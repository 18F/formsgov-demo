package gov.gsa.form.service.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.gsa.form.service.dto.Data;
import gov.gsa.form.service.dto.Login;
import gov.gsa.form.service.util.HttpClient;
import gov.gsa.form.service.util.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import javax.inject.Inject;


@RestController
@RequestMapping("/api")
public class FormIoResource {
    private final Logger log = LoggerFactory.getLogger(FormIoResource.class);

    private String reqBody = "{\"data\" : {\"email\":\"service@gsa.gov\"," +
        "\"password\":\"vBEJbMK6DAydFjBitmLbB4ndBhHZpm\"" + "}"+ "}";
    @Inject
    private HttpClient HttpClient;


    @GetMapping(value = "/login")
    public void login() throws JsonProcessingException {
        log.info("FormIoResource ::  login #");
        Login login = new Login(new Data());
      //  Mono<ClientResponse> clientResponse = HttpClient.postLoginRequest(ObjectMapperUtil.writeToJsonString(login.getData()));
        System.out.println(reqBody);
        Mono<ClientResponse> clientResponse = HttpClient.postLoginRequest(reqBody);
        clientResponse.subscribe((response) -> {

            // here you can access headers and status code
            ClientResponse.Headers headers = response.headers();
            HttpStatus stausCode = response.statusCode();

            Mono<String> bodyToMono = response.bodyToMono(String.class);
            // the second subscribe to access the body
            bodyToMono.subscribe((body) -> {

                // here you can access the body
                System.out.println("body:" + body);

                // and you can also access headers and status code if you need
                System.out.println("headers ***** :" + headers.asHttpHeaders());
                System.out.println("stausCode **** :" + stausCode);

            }, (ex) -> {
                // handle error
            });
        }, (ex) -> {
            // handle network error
        });

    }
}
