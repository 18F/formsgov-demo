package gov.gsa.form.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import javax.inject.Named;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class SignRequestUtil {

    private final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static ObjectMapper mapper = new ObjectMapper();;
    private String targetEnvAuthToken = null;


//    public SignRequestUtil(String targetEnvAuthToken, String targetEnvApiPath) {
//        this.signRequestClient = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
//        this.mapper = new ObjectMapper();
//        this.targetEnvAuthToken = targetEnvAuthToken;
//        this.targetEnvApiPath = targetEnvApiPath;
//    }

    public static String postSignRequestQuickCreate(String pdfUrl) {
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

        return quickCreateResponseNode.toPrettyString();
    }

    // TODO: The creation of the SignRequest Json should be done with an object with
    // a builder,
    // since there are a lot of things that need to be provided to create the json.
    // They are hardcoded for now.
    public static String formatSignRequestJson(URL url) throws IOException {

        String defaultQuickCreateJson = "{\"from_email\": \"from@example.com\",\"redirect_url\": \"http://documentsigned.com\",\"redirect_url_declined\": \"http://documentnotsigned.com\",\"signers\": [{\"email\": \"wube@gsa.gov\",\"first_name\": \"Wube\",\"last_name\": \"Kifle\",\"embed_url_user_id\" : \"wube@gsa.gov\"}],\"file_from_url\": \"\",\"events_callback_url\": \"\",\"auto_delete_days\": 5,\"auto_expire_days\": 5}";
        String urlToRedirectOnceSigned = "https://localhost:8181/faas/ui#/sign-success";
        String urlToRedirectIfNotSigned = "https://localhost:8181/faas/ui#/sign-unsuccessful";
        String signRequestCallbackUrl = "https://localhost:8181/faas";

        // read json into JsonNode using Jackson ObjectMapper
        ObjectNode signRequestJsonConfiguration = (ObjectNode) mapper.readTree(defaultQuickCreateJson);

        byte[] encoded = encodePdfToByte(url);

        //modify certain Json fields
        signRequestJsonConfiguration.put("redirect_url", urlToRedirectOnceSigned);
        signRequestJsonConfiguration.put("redirect_url_declined", urlToRedirectIfNotSigned);
        signRequestJsonConfiguration.put("file_from_content", encoded);
        signRequestJsonConfiguration.put("file_from_content_name", "F8821.pdf"); //title at the top
        signRequestJsonConfiguration.put("events_callback_url", signRequestCallbackUrl);

        return signRequestJsonConfiguration.toPrettyString();
    }

    private  static byte[] encodePdfToByte(URL url) throws IOException {

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
