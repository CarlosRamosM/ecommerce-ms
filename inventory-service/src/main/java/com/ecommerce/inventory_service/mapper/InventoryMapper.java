package com.ecommerce.inventory_service.mapper;

import com.ecommerce.inventory_service.dm.model.Inventory;
import com.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponseDto toInventoryResponseDto(Inventory inventory);

    @Mapping(target = "id", ignore = true)
    Inventory toEntity(InventoryRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateInventory(InventoryRequestDto dto, @MappingTarget Inventory inventory);
}
