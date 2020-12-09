package gov.gsa.form.service.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {
	public static final String ADMIN = "ROLE_ADMIN";

	public static final String USER = "ROLE_USER";

	public static final String ANONYMOUS = "ROLE_ANONYMOUS";

	private AuthoritiesConstants() {
	}
}
