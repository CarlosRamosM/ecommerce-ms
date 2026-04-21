package com.ecommerce.order_service.integration.inventory;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PatchExchange;

public interface InventoryClient {

    @PatchExchange("/api/inventory/reduce-stock/{sku}")
    void reduceStock(@PathVariable final String sku, @RequestParam final int quantity);
}
