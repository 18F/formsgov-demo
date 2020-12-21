package gov.gsa.form.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.gsa.form.service.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

@Named
public class SignRequestService {

    private final Logger log = LoggerFactory.getLogger(SignRequestService.class);

    private static ObjectMapper mapper = new ObjectMapper();
    ;
    private String targetEnvAuthToken = null;

    @Value("${security.saml2.server}")
    private String host;

    @Value("${security.saml2.port}")
    private String port;




    public  String postSignRequestQuickCreate(String pdfUrl) {
//        String formioPdfDownloadUrl = "https://dev-portal.fs.gsa.gov/project/" + projectId + "/form/" + formId
//                + "/submission/" + submissionId + "/download?token=" + downloadTokenKey;
        String targetEnvApiPath = "https://formservice.sr-sandb.appsquared.io/api/v1";
        HttpClient signRequestClient = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
        String targetEnvAuthToken = "ddcf8999cb911fbf0e63953e5a40785fe5948d5a";
        JsonNode quickCreateResponseNode = null;

        try {
            URL url = new URL(pdfUrl);
            String signRequestJsonString = formatSignRequestJson(url);
            URI signRequestQuickCreateUri = new URI(targetEnvApiPath + "/signrequest-quick-create/");
            HttpRequest signRequestQuickCreateRequest = HttpRequest.newBuilder()
                    .header("Authorization", "TOKEN " + targetEnvAuthToken)
                    .header("Content-Type", "application/json").uri(signRequestQuickCreateUri)
                    .POST(BodyPublishers.ofString(signRequestJsonString)).build();

            HttpResponse<String>  signRequestQuickCreateResponse = signRequestClient.send(signRequestQuickCreateRequest,
                        HttpResponse.BodyHandlers.ofString());

            quickCreateResponseNode = mapper.readTree(signRequestQuickCreateResponse.body().toString());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info(quickCreateResponseNode.toPrettyString());
        return quickCreateResponseNode.toPrettyString();
    }

    // TODO: The creation of the SignRequest Json should be done with an object with
    // a builder,
    // since there are a lot of things that need to be provided to create the json.
    // They are hardcoded for now.
    public  String formatSignRequestJson(URL url) throws IOException {
        String redirectUrl = "https://" + this.host + ":" + this.port + "/faas";
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
       User user=  (User) request.getSession().getAttribute("user");
        String urlToRedirectOnceSigned = redirectUrl + "/ui#/sign-success";
        String urlToRedirectIfNotSigned = redirectUrl + "/ui#/sign-unsuccessful";
        String email = user.getEmail();
        String signerFirstName = user.getFirstName();
        String signerLastName = user.getLastName();
        String defaultQuickCreateJson = "{\"from_email\": \"from@example.com\",\"redirect_url\": \"http://documentsigned.com\",\"redirect_url_declined\": \"http://documentnotsigned.com\",\"signers\": [],\"file_from_url\": \"\",\"events_callback_url\": \"\",\"auto_delete_days\": 5,\"auto_expire_days\": 5}";
        String signers = "{\"email\": \"from@example.com\",\"first_name\": \"Wube\",\"last_name\": \"Kifle\",\"embed_url_user_id\" : \"wube@gsa.gov\"}";

        ObjectNode signersJson = (ObjectNode) mapper.readTree(signers);

        signersJson.put("email", email);
        signersJson.put("first_name", signerFirstName);
        signersJson.put("last_name", signerLastName);
        signersJson.put("embed_url_user_id", email);



            // read json into JsonNode using Jackson ObjectMapper
        ObjectNode signRequestJsonConfiguration = (ObjectNode) mapper.readTree(defaultQuickCreateJson);

        ArrayNode arrayNode = signRequestJsonConfiguration.putArray("signers");
        arrayNode.add(signersJson);

        byte[] encoded = encodePdfToByte(url);

        //modify certain Json fields
        signRequestJsonConfiguration.put("redirect_url", urlToRedirectOnceSigned);
        signRequestJsonConfiguration.put("redirect_url_declined", urlToRedirectIfNotSigned);

        signRequestJsonConfiguration.put("from_email", email);
        signRequestJsonConfiguration.put("from_email_name", signerFirstName);


        signRequestJsonConfiguration.put("file_from_content", encoded);
        signRequestJsonConfiguration.put("file_from_content_name", "F8821.pdf"); //title at the top
        signRequestJsonConfiguration.put("events_callback_url", redirectUrl);
        log.info(signRequestJsonConfiguration.toPrettyString());
        return signRequestJsonConfiguration.toPrettyString();
    }

    private   byte[] encodePdfToByte(URL url) throws IOException {

        try (InputStream is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.

            int n;
            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }

            return baos.toByteArray();
        }

    }

}

// This should be called with something like the following:
//
//String signRequestSandboxApiToken = "375bc981d1bda07e090fcaa410851812de56208a"; //stephaniesandbox
//String signRequestApiPath = "https://stephaniesandbox.sr-sandb.appsquared.io/api/v1";
//
//SignRequestUtil srUtil = new SignRequestUtil(signRequestSandboxApiToken, signRequestApiPath);
//
//String projectId = "5f6a32fb7974387303dc6859";
//String formId = "5fd14dd2ba8cc517f0ec74e4";
//String submissionId = "5fd3f55afc99441736102e07";
//String downloadTokenKey = "290OhaP9pHIbceBfFo18n4uCPCgIjw";
//
//JsonNode response = srUtil.postSignRequestQuickCreate(projectId, formId, submissionId, downloadTokenKey);
////TODO: - do something with the response to grab the embed_url for each signer
//System.out.println(response.toPrettyString());
