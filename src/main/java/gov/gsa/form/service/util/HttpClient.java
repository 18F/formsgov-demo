package gov.gsa.form.service.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.inject.Named;

@Named
public class HttpClient {
    private final Logger log = LoggerFactory.getLogger(HttpClient.class);
    private static String FORMIO_LOGIN_URL  = "https://dev-portal.fs.gsa.gov/dev/admin/login";
    private final WebClient webClient;

    public HttpClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<ClientResponse> postLoginRequest(String reqBody) {
        return webClient.post().uri(FORMIO_LOGIN_URL).body(Mono.just(reqBody), String.class).exchange();
    }
}
