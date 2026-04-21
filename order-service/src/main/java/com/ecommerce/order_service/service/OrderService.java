package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dm.model.Order;
import com.ecommerce.order_service.dm.repository.OrderRepository;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.exception.ResourceNotFoundException;
import com.ecommerce.order_service.integration.inventory.InventoryClient;
import com.ecommerce.order_service.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    private final InventoryClient inventoryClient;

    public OrderService(final OrderRepository repository, final OrderMapper mapper, final InventoryClient inventoryClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.inventoryClient = inventoryClient;
    }

    @Transactional
    public OrderResponse placeOrder(final OrderRequest request) {
        log.info("Placing order: {}", request);
        var orderLineItems = request.orderLineItems()
            .stream()
            .map(mapper::toOrderLineItem)
            .toList();
        orderLineItems
            .forEach(item -> {
                var sku = item.getSku();
                var quantity = item.getQuantity();
                try {
                    inventoryClient.reduceStock(sku, quantity);
                } catch (Exception e) {
                    log.error("Error reducing stock for sku: {} quantity: {}", sku, quantity, e);
                    throw new IllegalArgumentException("Insufficient inventory or Error for sku: " + sku + " quantity: " + quantity);
                }
            });
        var order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItems(orderLineItems);
        var orderSaved = repository.save(order);
        return mapper.toOrderResponse(orderSaved);
    }

    public List<OrderResponse> getAllOrders() {
        log.info("Getting all orders");
        var orders = repository.findAll();
        return orders.stream()
            .map(mapper::toOrderResponse)
            .toList();
    }

    public OrderResponse getOrderById(final Long id) {
        log.info("Getting order by id: {}", id);
        var order = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));
        return mapper.toOrderResponse(order);
    }

    @Transactional
    public void deleteOrder(final Long id) {
        log.info("Deleting order by id: {}", id);
        var order = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id.toString()));
        repository.delete(order);
    }
}
