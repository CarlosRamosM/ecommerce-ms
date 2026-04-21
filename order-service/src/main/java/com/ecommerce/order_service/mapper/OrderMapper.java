package com.ecommerce.order_service.mapper;

import com.ecommerce.order_service.dm.model.Order;
import com.ecommerce.order_service.dm.model.OrderLineItem;
import com.ecommerce.order_service.dto.request.OrderLineItemRequest;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderLineItemResponse;
import com.ecommerce.order_service.dto.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toOrderResponse(Order order);

    OrderLineItemResponse toOrderLineItemResponse(OrderLineItem orderLineItem);

    Order toOrder(OrderRequest orderRequest);

    OrderLineItem toOrderLineItem(OrderLineItemRequest orderLineItemRequest);
}
