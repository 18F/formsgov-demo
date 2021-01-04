package gov.gsa.form.service.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SignRequestPayload implements Serializable {
    private static final long serialVersionUID = 7703827386714380482L;
    @JsonProperty("from_email")
    private String fromEmail;
    @JsonProperty("from_email_name")
    private String fromEmailName;
    @JsonProperty("redirect_url")
    private String redirectUrl;
    @JsonProperty("redirect_url_declined")
    private String redirectUrlDeclined;
    @JsonProperty("file_from_content")
    private byte[] fileFromContent;
    @JsonProperty("events_callback_url")
    private String eventsCallbackUrl;
    @JsonProperty("file_from_content_name")
    private String fileFromContentName;
    private List<Signers> signers;
}
