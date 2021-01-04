package gov.gsa.form.service.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.inject.Named;
import java.util.Collections;

/**
 * Custom AuthenticationProvider, all logged in user are set to authenticated.
 * It is assumed to be safe since portal will handle and pass authenticated user
 * to FAAS APP
 *
 */
@Named
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) {
		return new UsernamePasswordAuthenticationToken(null, Collections.emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
