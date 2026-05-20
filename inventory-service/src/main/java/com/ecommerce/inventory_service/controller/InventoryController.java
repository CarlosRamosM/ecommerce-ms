package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
    path = "/api/inventory",
    produces = "application/json"
)
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping(path = "/create", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public InventoryResponseDto createInventory(@RequestBody @Valid final InventoryRequestDto request) {
        return inventoryService.createInventory(request);
    }

    @GetMapping
    public List<InventoryResponseDto> getAllInventory(HttpServletRequest request) {
        log.info("Received request in port: {}", request.getServerPort());
        return inventoryService.getAllInventory();
    }

    @GetMapping(path = "/{sku}")
    public InventoryResponseDto getInventoryBySku(@PathVariable final String sku) {
        return inventoryService.getInventoryBySku(sku);
    }

    @PutMapping(path = "/{sku}", consumes = "application/json")
    public InventoryResponseDto updateInventory(@PathVariable final String sku, @RequestBody @Valid final InventoryRequestDto request) {
        return inventoryService.updateInventory(sku, request);
    }

    @DeleteMapping(path = "/{sku}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable final String sku) {
        inventoryService.deleteInventory(sku);
    }

    @GetMapping(path = "/in-stock/{sku}")
    public boolean isInStock(@PathVariable final String sku, @RequestParam final int quantity) {
        return inventoryService.isInStock(sku, quantity);
    }

    @PatchMapping(path = "/reduce-stock/{sku}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void reduceStock(@PathVariable final String sku, @RequestParam final int quantity) {
        inventoryService.reduceStock(sku, quantity);
    }
}
