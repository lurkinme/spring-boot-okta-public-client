package com.okta.examples.oauth2.pkcealways.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import com.okta.examples.oauth2.pkcealways.custom.CustomAuthorizationRequestResolver;
import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Value("${okta.oauth2.pkce-always}")
    private boolean pkceOn;

    @Value("${okta.oauth2.redirect-uri}")
    private String preEstablishedRedirectUrl;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http
                .authorizeRequests()
                .antMatchers("/", "/img/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri(preEstablishedRedirectUrl);

        if (pkceOn) {
            http
                    .oauth2Login()
                    .authorizationEndpoint()
                    .authorizationRequestResolver(new CustomAuthorizationRequestResolver(
                            clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
                    ));
        }
    }
}
