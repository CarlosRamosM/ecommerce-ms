package com.ecommerce.order_service.dto.response;

import java.util.List;

public record OrderResponse(
    Long id,
    String orderNumber,
    List<OrderLineItemResponse> orderLineItems
) {
}
