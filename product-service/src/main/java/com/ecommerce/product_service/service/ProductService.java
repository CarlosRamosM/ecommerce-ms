package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dm.repository.ProductRepository;
import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import com.ecommerce.product_service.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    public ProductService(final ProductRepository repository, final ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponseDto createProduct(final ProductRequestDto request) {
        log.info("Creating product: {}", request);
        var product = mapper.toEntity(request);
        return mapper.toResponseDto(repository.save(product));
    }

    public List<ProductResponseDto> getAllProducts() {
        log.info("Getting all products");
        return repository.findAll()
            .stream()
            .map(mapper::toResponseDto)
            .toList();
    }

    public ProductResponseDto getProductById(final String id) {
        log.info("Getting product by id: {}", id);
        return repository.findById(id)
            .map(mapper::toResponseDto)
            .orElseGet(() -> new ProductResponseDto(null, null, null, null));
    }

    public ProductResponseDto updateProduct(final String id, final ProductRequestDto request) {
        log.info("Updating product with id: {}", id);
        return repository.findById(id)
            .map(product -> {
                mapper.updateProduct(request, product);
                repository.save(product);
                return mapper.toResponseDto(product);
            })
            .orElseGet(() -> new ProductResponseDto(null, null, null, null));
    }

    public void deleteProduct(final String id) {
        log.info("Deleting product with id: {}", id);
        repository.deleteById(id);
    }
}
