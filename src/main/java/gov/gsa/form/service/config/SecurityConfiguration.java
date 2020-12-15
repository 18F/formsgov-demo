package gov.gsa.form.service.config;

import gov.gsa.form.service.security.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.inject.Inject;

import static gov.gsa.form.service.security.CustomSAMLConfigurer.saml;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.saml2.metadata-url}")
    private String metadataUrl;

    @Value("${server.ssl.key-alias}")
    private String keyAlias;

    @Value("${server.ssl.key-store-password}")
    private String password;

    @Value("${security.saml2.port}")
    private String port;

    @Value("${server.ssl.key-store}")
    private String keyStoreFilePath;

    @Value("${security.saml2.server}")
    private String host;

    @Value("${security.saml2.entityId}")
    private String entityId;

    @Inject
    CustomAuthenticationProvider customAuthProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().sameOrigin()
            .and()
            .authorizeRequests()
            .antMatchers("/faas/saml*").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(saml())
            .serviceProvider()
            .entityId(this.entityId)
            .keyStore()
            .storeFilePath(this.keyStoreFilePath)
            .password(this.password)
            .keyname(this.keyAlias)
            .keyPassword(this.password)
            .and()
            .protocol("https")
            .hostname(String.format("%s:%s", this.host, this.port))
            .basePath("/faas")
            .and()
            .userDetailsService(new UserDetails())
            .identityProvider()
            .metadataFilePath(this.metadataUrl);
    }
}
