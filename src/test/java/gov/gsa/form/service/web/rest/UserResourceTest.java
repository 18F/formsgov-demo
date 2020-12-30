package gov.gsa.form.service.web.rest;

import gov.gsa.form.service.web.rest.vm.UserResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wube Kifle
 */
@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private UserResource endpoint;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(endpoint).build();
    }

    @Test
    public void shouldGetCurrentUser() throws Exception {
        mockMvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
