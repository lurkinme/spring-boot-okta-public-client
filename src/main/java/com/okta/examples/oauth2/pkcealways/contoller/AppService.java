package com.okta.examples.oauth2.pkcealways.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Autowired
    OAuth2AuthorizedClientService clientService;

    public String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        return client.getAccessToken()
                .getTokenValue();
    }
}
