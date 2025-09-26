package com.marketplace.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Public endpoints
                        .pathMatchers(
                                "/user-service/api/users/register",
                                "/user-service/api/users/login",
                                "/product-service/api/products",
                                "/product-service/api/products/**",
                                "/actuator/health",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        
                        // Admin only endpoints
                        .pathMatchers(
                                "/user-service/api/users/admin/**",
                                "/product-service/api/products/admin/**"
                        ).hasAuthority("ADMIN")
                        
                        // Authenticated users only
                        .pathMatchers(
                                "/cart-service/**",
                                "/payment-service/**",
                                "/email-service/**"
                        ).authenticated()
                        
                        // All other requests require authentication
                        .anyExchange().authenticated()
                )
                .build();
    }
}