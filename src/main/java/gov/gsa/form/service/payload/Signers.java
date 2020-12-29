package gov.gsa.form.service.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Signers implements Serializable {
    private static final long serialVersionUID = 5808167462123295388L;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("embed_url_user_id")
    private String embedUrlUserId;

    public Signers(String email, String firstName, String lastName, String embedUrlUserId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.embedUrlUserId = embedUrlUserId;
    }

    public Signers() {
    }
}
