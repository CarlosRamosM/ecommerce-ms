package com.ecommerce.order_service.dto.response;

import java.math.BigDecimal;

public record OrderLineItemResponse(
    Long id,
    String sku,
    BigDecimal price,
    Integer quantity
) {
}
