package gov.gsa.form.service.web.rest;

import gov.gsa.form.service.service.SignRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
public class FormResource {

    private final SignRequestService signRequest;

    public FormResource(SignRequestService signRequest) {
        this.signRequest = signRequest;
    }

    @GetMapping(value = "/sign")
    public String signRequest(@RequestParam(name = "pdfUrl") String pdfUrl, @RequestParam(name = "pdfName") String pdfName) {
        log.info("Pdf Url :{} and Pdf Name :{}", pdfUrl, pdfName);
        return signRequest.executeSignRequest(pdfUrl, pdfName);
    }
}
