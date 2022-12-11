package com.baeldung.resource.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final String issuerUri = "http://localhost:8083/auth/realms/baeldung";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
            .hasAuthority("SCOPE_read")
            .antMatchers(HttpMethod.POST, "/api/foos")
            .hasAuthority("SCOPE_write")
            .anyRequest()
            .authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator();
        jwtDecoder.setJwtValidator(audienceValidator);
        return jwtDecoder;
    }
//    @Bean
//    ReactiveJwtDecoder jwtDecoder() {
//        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder)
//                ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
//
//        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator();
//        jwtDecoder.setJwtValidator(audienceValidator);
//
//        return jwtDecoder;
//    }
}