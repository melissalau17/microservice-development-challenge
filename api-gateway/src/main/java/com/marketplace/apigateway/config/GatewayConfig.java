package com.marketplace.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://user-service"))
                
                .route("product-service", r -> r.path("/product-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://product-service"))
                
                .route("cart-service", r -> r.path("/cart-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://cart-service"))
                
                .route("email-service", r -> r.path("/email-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://email-service"))
                
                .route("payment-service", r -> r.path("/payment-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://payment-service"))
                .build();
    }
}