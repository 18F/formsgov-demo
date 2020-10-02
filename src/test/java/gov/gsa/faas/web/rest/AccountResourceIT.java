package gov.gsa.faas.web.rest;

import gov.gsa.faas.FaasApp;
import gov.gsa.faas.config.TestSecurityConfiguration;
import gov.gsa.faas.security.AuthoritiesConstants;
import gov.gsa.faas.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static gov.gsa.faas.web.rest.AccountResourceIT.TEST_USER_LOGIN;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static gov.gsa.faas.web.rest.TestUtil.ID_TOKEN;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

/**
 * Integration tests for the {@link AccountResource} REST controller.
 */
@AutoConfigureWebTestClient
@WithMockUser(value = TEST_USER_LOGIN)
@SpringBootTest(classes = {FaasApp.class, TestSecurityConfiguration.class})
public class AccountResourceIT {

    static final String TEST_USER_LOGIN = "test";

    private OidcIdToken idToken;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("groups", Collections.singletonList(AuthoritiesConstants.ADMIN));
        claims.put("sub", "jane");
        claims.put("email", "jane.doe@jhipster.com");
        this.idToken = new OidcIdToken(ID_TOKEN, Instant.now(),
            Instant.now().plusSeconds(60), claims);
    }

    @Test
    public void testGetExistingAccount() {
        webTestClient
            .mutateWith(mockAuthentication(TestUtil.authenticationToken(idToken)))
            .mutateWith(csrf())
            .get().uri("/api/account")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody()
            .jsonPath("$.login").isEqualTo("jane")
            .jsonPath("$.email").isEqualTo("jane.doe@jhipster.com")
            .jsonPath("$.authorities").isEqualTo(AuthoritiesConstants.ADMIN);
    }

    @Test
    public void testGetUnknownAccount() {
        webTestClient.get().uri("/api/account")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is5xxServerError();
    }
}
