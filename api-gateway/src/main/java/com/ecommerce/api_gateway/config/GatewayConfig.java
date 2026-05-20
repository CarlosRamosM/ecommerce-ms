package com.ecommerce.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
            .routes()
            .route("product-service", r -> r.path("/api/products/**").uri("lb://product-service"))
            .route("order-service", r -> r.path("/api/orders/**").uri("lb://order-service"))
            .route("inventory-service", r -> r.path("/api/inventory/**").uri("lb://inventory-service"))
            .build();
    }
}
