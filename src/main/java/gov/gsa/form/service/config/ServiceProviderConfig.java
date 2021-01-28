package gov.gsa.form.service.config;

import com.github.ulisesbocchio.spring.boot.security.saml.bean.SAMLConfigurerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
@Slf4j
public class ServiceProviderConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.saml2.metadata-url}")
    private String metadataUrl;

//    @Value("${server.ssl.key-alias}")
//    private String keyAlias;
//
//    @Value("${server.ssl.key-store-password}")
//    private String password;

    @Value("${port}")
    private String port;

    @Value("${host}")
    private String host;

//    @Value("${server.ssl.key-store}")
//    private String keyStoreFilePath;

    @Value("${security.saml2.entityId}")
    private String entityId;

    public ServiceProviderConfig() {
        super(false);
    }

    @Bean
    SAMLConfigurerBean samlConfig() {
        return new SAMLConfigurerBean();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers("/faas/saml*").permitAll()
            .and()
            .httpBasic()
            .disable()
            .csrf()
            .disable()
            .anonymous()
            .and()
            .apply(samlConfig())
            .serviceProvider()
            .metadataGenerator()
            .entityId(this.entityId)
            //  .bindingsSSO("artifact", "post", "paos")
            .and()
            .ecpProfile()
            .and()
            .sso()
            .defaultSuccessURL("/faas")
//            .idpSelectionPageURL("/idpselection")
            .and()
            .metadataManager()
            .metadataLocations(this.metadataUrl)
            .refreshCheckInterval(0)
            .and()
            .extendedMetadata()
            .ecpEnabled(true)
            .idpDiscoveryEnabled(false)//set to false for no IDP Selection page.
            .and()
            .keyManager()
//            .storeLocation("classpath:config/tls/keystore.p12")
            .privateKeyDERLocation("classpath:config/tls/localhost.key.der")
            .publicKeyPEMLocation("classpath:config/tls/localhost.cert")
            .and()
            .http()
            .authorizeRequests()
            .requestMatchers(samlConfig().endpointsMatcher()).permitAll()
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated();
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }
}
