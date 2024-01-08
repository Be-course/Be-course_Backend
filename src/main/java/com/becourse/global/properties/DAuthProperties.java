package com.becourse.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DAuthProperties {
    @Value("${oauth2.dauth.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.dauth.service-id}")
    private String serviceId;

    @Value("${oauth2.dauth.service-secret}")
    private String serviceSecret;

    @Value("${oauth2.dauth.token-uri}")
    private String tokenUri;

    @Value("${oauth2.dauth.resource-uri}")
    private String resourceUri;
}
