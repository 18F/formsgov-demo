package gov.gsa.faas.web.rest;

import gov.gsa.faas.FaasApp;
import gov.gsa.faas.config.TestSecurityConfiguration;
import gov.gsa.faas.domain.PersistentAuditEvent;
import gov.gsa.faas.repository.PersistenceAuditEventRepository;
import gov.gsa.faas.security.AuthoritiesConstants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;

/**
 * Integration tests for the {@link AuditResource} REST controller.
 */
@AutoConfigureWebTestClient
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@SpringBootTest(classes = {FaasApp.class, TestSecurityConfiguration.class})
public class AuditResourceIT {

    private static final String SAMPLE_PRINCIPAL = "SAMPLE_PRINCIPAL";
    private static final String SAMPLE_TYPE = "SAMPLE_TYPE";
    private static final Instant SAMPLE_TIMESTAMP = Instant.parse("2015-08-04T10:11:30Z");
    private static final long SECONDS_PER_DAY = 60 * 60 * 24;

    @Autowired
    private PersistenceAuditEventRepository auditEventRepository;

    private PersistentAuditEvent auditEvent;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void initTest() {
        auditEventRepository.deleteAll().block();
        auditEvent = new PersistentAuditEvent();
        auditEvent.setAuditEventType(SAMPLE_TYPE);
        auditEvent.setPrincipal(SAMPLE_PRINCIPAL);
        auditEvent.setAuditEventDate(SAMPLE_TIMESTAMP);
    }

    @Test
    public void getAllAudits() {
        // Initialize the database
        auditEventRepository.save(auditEvent).block();

        // Get all the audits
        webTestClient.get().uri("/management/audits")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.[*].principal").value(hasItem(SAMPLE_PRINCIPAL));
    }

    @Test
    public void getAudit() {
        // Initialize the database
        auditEventRepository.save(auditEvent).block();

        // Get the audit
        webTestClient.get().uri("/management/audits/{id}", auditEvent.getId())
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.principal").isEqualTo(SAMPLE_PRINCIPAL);
    }

    @Test
    public void getAuditsByDate() {
        // Initialize the database
        auditEventRepository.save(auditEvent).block();

        // Generate dates for selecting audits by date, making sure the period will contain the audit
        String fromDate = SAMPLE_TIMESTAMP.minusSeconds(SECONDS_PER_DAY).toString().substring(0, 10);
        String toDate = SAMPLE_TIMESTAMP.plusSeconds(SECONDS_PER_DAY).toString().substring(0, 10);

        // Get the audit
        webTestClient.get().uri("/management/audits?fromDate="+fromDate+"&toDate="+toDate)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.[*].principal").value(hasItem(SAMPLE_PRINCIPAL));
    }

    @Test
    public void getNonExistingAuditsByDate() {
        // Initialize the database
        auditEventRepository.save(auditEvent).block();

        // Generate dates for selecting audits by date, making sure the period will not contain the sample audit
        String fromDate  = SAMPLE_TIMESTAMP.minusSeconds(2*SECONDS_PER_DAY).toString().substring(0, 10);
        String toDate = SAMPLE_TIMESTAMP.minusSeconds(SECONDS_PER_DAY).toString().substring(0, 10);

        // Query audits but expect no results
        webTestClient.get().uri("/management/audits?fromDate="+fromDate+"&toDate="+toDate)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectHeader().valueEquals("X-Total-Count", "0");
    }

    @Test
    public void getNonExistingAudit() {
        // Get the audit
        webTestClient.get().uri("/management/audits/{id}", Long.MAX_VALUE)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void testPersistentAuditEventEquals() throws Exception {
        TestUtil.equalsVerifier(PersistentAuditEvent.class);
        PersistentAuditEvent auditEvent1 = new PersistentAuditEvent();
        auditEvent1.setId(1L);
        PersistentAuditEvent auditEvent2 = new PersistentAuditEvent();
        auditEvent2.setId(auditEvent1.getId());
        assertThat(auditEvent1).isEqualTo(auditEvent2);
        auditEvent2.setId(2L);
        assertThat(auditEvent1).isNotEqualTo(auditEvent2);
        auditEvent1.setId(null);
        assertThat(auditEvent1).isNotEqualTo(auditEvent2);
    }
}
