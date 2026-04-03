package com.ecommerce.inventory_service.dto.response;

public record InventoryResponseDto(
    Long id,
    String sku,
    Integer quantity,
    boolean inStock
) {
}
