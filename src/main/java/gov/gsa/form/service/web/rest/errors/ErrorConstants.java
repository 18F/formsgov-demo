package gov.gsa.form.service.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
	public static final String PROBLEM_BASE_URL = "input validation";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
	public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create("input-constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");

    public static final int ERROR_CODE_0 = 0;
    public static final int ERROR_CODE_4 = 0;

    public static final int NO_REQUEST_ID = 0;

    public static final int NEGATIVE_REQUEST_ID = -1;

    private ErrorConstants() {
    }
}
