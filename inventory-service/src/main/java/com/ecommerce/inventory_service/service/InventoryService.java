package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dm.repository.InventoryRepository;
import com.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.ecommerce.inventory_service.mapper.InventoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class InventoryService {

    private final InventoryRepository repository;

    private final InventoryMapper mapper;

    public InventoryService(final InventoryRepository repository, final InventoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public InventoryResponseDto createInventory(final InventoryRequestDto request) {
        log.info("Creating inventory: {}", request);
        var inventory = mapper.toEntity(request);
        var inventorySaved = repository.save(inventory);
        return mapper.toInventoryResponseDto(inventorySaved);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponseDto> getAllInventory() {
        log.info("Getting all inventory");
        var inventories = repository.findAll();
        return inventories.stream()
            .map(mapper::toInventoryResponseDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InventoryResponseDto getInventoryBySku(final String sku) {
        log.info("Getting inventory by sku: {}", sku);
        return repository.findBySku(sku)
            .map(mapper::toInventoryResponseDto)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory", "sku", sku));
    }

    public InventoryResponseDto updateInventory(final String sku, final InventoryRequestDto request) {
        log.info("Updating inventory by sku: {} request: {}", sku, request);
        var inventory = repository.findBySku(sku)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory", "sku", sku));
        mapper.updateInventory(request, inventory);
        repository.save(inventory);
        return mapper.toInventoryResponseDto(inventory);
    }

    public void deleteInventory(final String sku) {
        log.info("Deleting inventory by sku: {}", sku);
        var inventory = repository.findBySku(sku)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory", "sku", sku));
        repository.delete(inventory);
    }

    public boolean isInStock(final String sku, final int quantity) {
        return repository.findBySku(sku)
            .map(inventory -> inventory.getQuantity() >= quantity)
            .orElse(false);
    }
}
