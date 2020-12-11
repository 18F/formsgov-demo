package gov.gsa.form.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import javax.inject.Inject;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;



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

    @Value("${server.port}")
    private String port;

    @Value("${server.ssl.key-store}")
    private String keyStoreFilePath;

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
            .authorizeRequests()
            .antMatchers("/faas/saml*").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(saml())
            .serviceProvider()
            .entityId("urn:gov:gsa:SAML:2.0.profiles:sp:sso:irs:formservice-poc-irs")
            // .entityId("https://ec2-96-127-124-187.us-gov-west-1.compute.amazonaws.com/faas")
            .keyStore()
            .storeFilePath(this.keyStoreFilePath)
            .password(this.password)
            .keyname(this.keyAlias)
            //.keyname("feddevsso")
            .keyPassword(this.password)
            .and()
            .protocol("https")
            .hostname(String.format("%s:%s", "localhost", this.port))
            .basePath("/faas")
            .and()
            .identityProvider()
            .metadataFilePath(this.metadataUrl);
    }
}
