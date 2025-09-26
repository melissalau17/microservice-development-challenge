package com.marketplace.cartservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Marketplace [Service Name] API")
                        .description("API Documentation for [Service Name] Microservice")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Marketplace Team")
                                .email("support@marketplace.com")
                                .url("http://marketplace.com")));
    }
}