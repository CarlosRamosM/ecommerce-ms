package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;
import com.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
    path = "/api/orders",
    produces = "application/json",
    consumes = "application/json"
)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid final OrderRequest orderRequest, @AuthenticationPrincipal final Jwt jwt) {
        return orderService.placeOrder(orderRequest, jwt.getSubject());
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(@AuthenticationPrincipal final Jwt jwt) {
        boolean isAdmin = false;
        String userId = jwt.getSubject();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            isAdmin = roles.stream().anyMatch(role -> role.equalsIgnoreCase("ADMIN"));
        }
        return orderService.getOrders(userId, isAdmin);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable final Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable final Long id) {
        orderService.deleteOrder(id);
    }
}
