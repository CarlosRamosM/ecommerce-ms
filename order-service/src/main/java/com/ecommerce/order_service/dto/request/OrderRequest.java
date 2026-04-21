package com.ecommerce.order_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    List<OrderLineItemRequest> orderLineItems
) {
}
