package gov.gsa.form.service.web.rest;

import gov.gsa.form.service.service.SignRequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class FormResourceTest {

    private MockMvc mockMvc;

    @Mock
    private SignRequestService signRequest;

    @InjectMocks
    private FormResource endpoint;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(endpoint).build();
    }

    @Test
    public void shouldSignRequest() throws Exception {
        String pdfUrl = "testUrl";
        String pdfName = "test.pdf";
        Mockito.when(signRequest.executeSignRequest(pdfUrl, pdfName)).thenReturn("result");
        mockMvc.perform(get("/api/sign", pdfUrl, pdfName)
            .param("pdfUrl", pdfUrl)
            .param("pdfName", pdfName)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        Mockito.verify(signRequest, Mockito.times(1)).executeSignRequest(pdfUrl, pdfName);
    }

}
