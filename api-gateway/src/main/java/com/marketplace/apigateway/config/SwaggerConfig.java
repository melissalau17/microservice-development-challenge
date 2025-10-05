package com.marketplace.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Marketplace API Gateway")
                .description("Central API Gateway for Marketplace Microservices")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Marketplace Team")
                    .url("http://marketplace.com")
                    .email("support@marketplace.com")
                )
            );
    }
}
