package gov.gsa.faas.repository;

import gov.gsa.faas.domain.PersistentAuditEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * Spring Data R2DBC repository for the {@link PersistentAuditEvent} entity.
 */
@Repository
public interface PersistenceAuditEventRepository extends R2dbcRepository<PersistentAuditEvent, Long>, PersistenceAuditEventRepositoryInternal {

    @Query("INSERT INTO jhi_persistent_audit_evt_data VALUES(:eventId, :name, :value)")
    Mono<Void> savePersistenceAuditEventData(Long eventId, String name, String value);
}

interface PersistenceAuditEventRepositoryInternal {

    Flux<PersistentAuditEvent> findByPrincipal(String principal);

    Flux<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    Flux<PersistentAuditEvent> findByAuditEventDateBefore(Instant before);

    Flux<PersistentAuditEvent> findAllBy(Pageable pageable);

    Mono<Long> countByAuditEventDateBetween(Instant fromDate, Instant toDate);
}

class PersistenceAuditEventRepositoryInternalImpl implements PersistenceAuditEventRepositoryInternal {

    private final DatabaseClient databaseClient;

    public PersistenceAuditEventRepositoryInternalImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<PersistentAuditEvent> findByPrincipal(String principal) {
        return findAllByCriteria(Criteria.where("principal").is(principal));
    }

    @Override
    public Flux<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable) {
        // LocalDateTime seems to be the only type that is supported across all drivers atm
        // See https://github.com/r2dbc/r2dbc-h2/pull/139 https://github.com/mirromutth/r2dbc-mysql/issues/105
        LocalDateTime fromDateLocal = LocalDateTime.ofInstant(fromDate, ZoneOffset.UTC);
        LocalDateTime toDateLocal = LocalDateTime.ofInstant(toDate, ZoneOffset.UTC);
        Criteria criteria = Criteria
            .where("event_date").greaterThan(fromDateLocal)
            .and("event_date").lessThan(toDateLocal);
        return findAllFromSpec(select().matching(criteria).page(pageable));
    }

    @Override
    public Flux<PersistentAuditEvent> findByAuditEventDateBefore(Instant before) {
        // LocalDateTime seems to be the only type that is supported across all drivers atm
        // See https://github.com/r2dbc/r2dbc-h2/pull/139 https://github.com/mirromutth/r2dbc-mysql/issues/105
        LocalDateTime beforeLocal = LocalDateTime.ofInstant(before, ZoneOffset.UTC);
        return findAllByCriteria(Criteria.where("event_date").lessThan(beforeLocal));
    }

    @Override
    public Flux<PersistentAuditEvent> findAllBy(Pageable pageable) {
        return findAllFromSpec(select().page(pageable));
    }

    @Override
    public Mono<Long> countByAuditEventDateBetween(Instant fromDate, Instant toDate) {
        // LocalDateTime seems to be the only type that is supported across all drivers atm
        // See https://github.com/r2dbc/r2dbc-h2/pull/139 https://github.com/mirromutth/r2dbc-mysql/issues/105
        LocalDateTime fromDateLocal = LocalDateTime.ofInstant(fromDate, ZoneOffset.UTC);
        LocalDateTime toDateLocal = LocalDateTime.ofInstant(toDate, ZoneOffset.UTC);
        return databaseClient.execute("SELECT COUNT(DISTINCT event_id) FROM jhi_persistent_audit_event " +
            "WHERE event_date > :fromDate AND event_date < :toDate")
            .bind("fromDate", fromDateLocal)
            .bind("toDate", toDateLocal)
            .as(Long.class)
            .fetch()
            .one();
    }

    private Flux<PersistentAuditEvent> findAllByCriteria(Criteria criteria) {
        return findAllFromSpec(select().matching(criteria));
    }

    private DatabaseClient.TypedSelectSpec<PersistentAuditEvent> select() {
        return databaseClient.select().from(PersistentAuditEvent.class);
    }

    private Flux<PersistentAuditEvent> findAllFromSpec(DatabaseClient.TypedSelectSpec<PersistentAuditEvent> spec) {
        return spec.as(PersistentAuditEvent.class).all()
            .flatMap(event -> findAllEventData(event.getId())
                .map(data -> {
                    event.setData(data);
                    return event;
                })
            );
    }

    private Mono<Map<String, String>> findAllEventData(Long id) {
        return databaseClient.select().from("jhi_persistent_audit_evt_data")
            .project("name", "value")
            .matching(Criteria.where("event_id").is(id))
            .map(row -> {
                String name = row.get("name", String.class);
                String value = row.get("value", String.class);
                return Pair.of(name == null ? "" : name, value == null ? "" : value);
            })
            .all()
            .collectMap(Pair::getFirst, Pair::getSecond);
    }
}
