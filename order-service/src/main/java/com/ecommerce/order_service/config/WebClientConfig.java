package com.ecommerce.order_service.config;

import com.ecommerce.order_service.integration.inventory.InventoryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient
            .builder();
    }

    @Bean
    public InventoryClient inventoryClient(WebClient.Builder builder) {
        var webClient = builder
            .baseUrl("http://inventory-service")
            .build();
        var adapter = WebClientAdapter.create(webClient);
        var factory = HttpServiceProxyFactory
            .builderFor(adapter)
            .build();
        return factory.createClient(InventoryClient.class);
    }
}
