package gov.gsa.form.service.service.impl;

import gov.gsa.form.service.dto.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Wube Kifle
 */
@RunWith(MockitoJUnitRunner.class)
public class SignRequestServiceImplTest {

    private final HttpServletRequest request = mock(MockHttpServletRequest.class);

    private final HttpSession session = mock(MockHttpSession.class);

    private final WebClient webClient = mock(WebClient.class);

    @SuppressWarnings("rawtypes")
    private final WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);

    private final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private SignRequestServiceImpl signRequestService;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        User user = new User();
        user.setFirstName("Mamo");
        user.setLastName("Killo");
        user.setEmail("mamo@email.com");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(webClient.post()).thenReturn(mock(RequestBodyUriSpec.class));

        when(webClient.post().uri(Mockito.anyString())).thenReturn(mock(RequestBodySpec.class));

        when(webClient.post().uri(Mockito.anyString()).headers(Mockito.any())).thenReturn(mock(RequestBodySpec.class));

        when(webClient.post().uri(Mockito.anyString()).headers(Mockito.any()).body(Mockito.any(Mono.class), Mockito.eq(String.class))).thenReturn(requestHeadersSpec);

        ReflectionTestUtils.setField(signRequestService, "webClient", webClient);
        ReflectionTestUtils.setField(signRequestService, "token", "token");
        ReflectionTestUtils.setField(signRequestService, "signRequestUrl", "test-url");
    }

    @Test
    public void executeSignRequestTest() throws MalformedURLException {
        URL url = Paths.get("src/test/resources", ("/sample.pdf")).toUri().toURL();
        String pdfName = "sample.pdf";
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("response"));
        String result = signRequestService.executeSignRequest(url.toString(), pdfName);
        Assert.assertNotNull(result);
    }
}

