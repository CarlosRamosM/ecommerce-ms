package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dm.model.Product;
import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto toResponseDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateProduct(ProductRequestDto dto, @MappingTarget Product product);
}
