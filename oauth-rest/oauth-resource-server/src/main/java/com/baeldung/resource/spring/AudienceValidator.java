package com.baeldung.resource.spring;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    OAuth2Error error = new OAuth2Error("invalid_preferred_username_email_domain", "The required email domain is not this", null);

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        System.out.println("Val-----------------------------------------------######################");
        if (jwt.hasClaim("preferred_username") && jwt.getClaimAsString("preferred_username").contains("@test.com")) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}