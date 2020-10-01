package gov.gsa.faas.service;

import io.github.jhipster.config.JHipsterProperties;
import gov.gsa.faas.config.audit.AuditEventConverter;
import gov.gsa.faas.domain.PersistentAuditEvent;
import gov.gsa.faas.repository.PersistenceAuditEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.actuate.security.AuthenticationAuditListener.AUTHENTICATION_FAILURE;
import static org.springframework.boot.actuate.security.AuthenticationAuditListener.AUTHENTICATION_SUCCESS;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator {@code AuditEventRepository}.
 */
@Service
public class AuditEventService {

    /**
     * Should be the same as in Liquibase migration.
     */
    private static final int EVENT_DATA_COLUMN_MAX_LENGTH = 255;

    private final Logger log = LoggerFactory.getLogger(AuditEventService.class);

    private final JHipsterProperties jHipsterProperties;

    private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final AuditEventConverter auditEventConverter;

    public AuditEventService(
        PersistenceAuditEventRepository persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter, JHipsterProperties jhipsterProperties) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
        this.jHipsterProperties = jhipsterProperties;
    }

    /**
     * Old audit events should be automatically deleted after 30 days.
     *
     * This is scheduled to get fired at 12:00 (am).
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void removeOldAuditEvents() {
        removeOldAuditEventsReactively().block();
    }

    @Transactional
    public Mono<Void> removeOldAuditEventsReactively() {
        return persistenceAuditEventRepository
            .findByAuditEventDateBefore(Instant.now().minus(jHipsterProperties.getAuditEvents().getRetentionPeriod(), ChronoUnit.DAYS))
            .flatMap(auditEvent -> {
                log.debug("Deleting audit data {}", auditEvent);
                return persistenceAuditEventRepository.delete(auditEvent);
            })
            .then();
    }

    @Transactional(readOnly = true)
    public Flux<AuditEvent> findAll(Pageable pageable) {
        return persistenceAuditEventRepository.findAllBy(pageable)
            .map(auditEventConverter::convertToAuditEvent);
    }

    @Transactional(readOnly = true)
    public Flux<AuditEvent> findByDates(Instant fromDate, Instant toDate, Pageable pageable) {
        return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
            .map(auditEventConverter::convertToAuditEvent);
    }

    @Transactional(readOnly = true)
    public Mono<AuditEvent> find(Long id) {
        return persistenceAuditEventRepository.findById(id)
            .map(auditEventConverter::convertToAuditEvent);
    }

    @Transactional(readOnly = true)
    public Mono<Long> count() {
        return persistenceAuditEventRepository.count();
    }

    @Transactional(readOnly = true)
    public Mono<Long> countByDates(Instant fromDate, Instant toDate) {
        return persistenceAuditEventRepository.countByAuditEventDateBetween(fromDate, toDate);
    }

    @Transactional
    public Mono<PersistentAuditEvent> saveAuthenticationSuccess(String login) {
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
        persistentAuditEvent.setPrincipal(login);
        persistentAuditEvent.setAuditEventType(AUTHENTICATION_SUCCESS);
        persistentAuditEvent.setAuditEventDate(Instant.now());
        return persistenceAuditEventRepository.save(persistentAuditEvent);
    }

    @Transactional
    public Mono<PersistentAuditEvent> saveAuthenticationError(String login, Throwable e) {
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
        persistentAuditEvent.setPrincipal(login);
        persistentAuditEvent.setAuditEventType(AUTHENTICATION_FAILURE);
        persistentAuditEvent.setAuditEventDate(Instant.now());
        Map<String, String> eventData = new HashMap<>();
        eventData.put("type", e.getClass().getName());
        eventData.put("message", e.getMessage());
        persistentAuditEvent.setData(truncate(eventData));
        return persistenceAuditEventRepository.save(persistentAuditEvent)
            .flatMapMany(event -> Flux.fromStream(
                event.getData().entrySet().stream()
                    .map(entry -> Tuples.of(event, entry.getKey(), entry.getValue()))
            ))
            .flatMap(t -> persistenceAuditEventRepository
                .savePersistenceAuditEventData(t.getT1().getId(), t.getT2(), t.getT3())
                .thenReturn(t.getT1())
            )
            .last();
    }

    /**
     * Truncate event data that might exceed column length.
     */
    private Map<String, String> truncate(Map<String, String> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    int length = value.length();
                    if (length > EVENT_DATA_COLUMN_MAX_LENGTH) {
                        value = value.substring(0, EVENT_DATA_COLUMN_MAX_LENGTH);
                        log.warn("Event data for {} too long ({}) has been truncated to {}. Consider increasing column width.",
                                entry.getKey(), length, EVENT_DATA_COLUMN_MAX_LENGTH);
                    }
                }
                results.put(entry.getKey(), value);
            }
        }
        return results;
    }
}
