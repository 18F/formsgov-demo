package gov.gsa.form.service.payload.builder;

import gov.gsa.form.service.payload.SignRequestPayload;
import gov.gsa.form.service.payload.Signers;

import java.util.List;

/**
 * @author Wube Kifle
 *
 * <p>
 * Payload builder for Sign request.
 * </p>
 */
public class SignRequestPayloadBuilder {

    private final SignRequestPayload payload;

    private SignRequestPayloadBuilder() {
        payload = new SignRequestPayload();
    }

    public static SignRequestPayloadBuilder builder() {
        return new SignRequestPayloadBuilder();
    }

    public SignRequestPayload build() {
        return payload;
    }

    public SignRequestPayloadBuilder fromEmail(String fromEmail) {
        payload.setFromEmail(fromEmail);
        return this;
    }

    public SignRequestPayloadBuilder fromEmailName(String fromEmailName) {
        payload.setFromEmailName(fromEmailName);
        return this;
    }

    public SignRequestPayloadBuilder redirectUrl(String redirectUrl) {
        payload.setRedirectUrl(redirectUrl);
        return this;
    }

    public SignRequestPayloadBuilder redirectUrlDeclined(String redirectUrlDeclined) {
        payload.setRedirectUrlDeclined(redirectUrlDeclined);
        return this;
    }

    public SignRequestPayloadBuilder fileFromContent(byte[] fileFromContent) {
        payload.setFileFromContent(fileFromContent);
        return this;
    }

    public SignRequestPayloadBuilder eventsCallbackUrl(String eventsCallbackUrl) {
        payload.setEventsCallbackUrl(eventsCallbackUrl);
        return this;
    }

    public SignRequestPayloadBuilder fileFromContentName(String fileFromContentName) {
        payload.setFileFromContentName(fileFromContentName);
        return this;
    }

    public SignRequestPayloadBuilder signers(List<Signers> signers) {
        payload.setSigners(signers);
        return this;
    }
}
