package com.ecommerce.inventory_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryRequestDto(
    @NotBlank(message = "SKU is required.")
    String sku,

    @NotNull(message = "Quantity is required.")
    @Positive(message = "Quantity must be non-negative.")
    Integer quantity
) {
}
