package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.ProductRequestDto;
import com.ecommerce.product_service.dto.ProductResponseDto;
import com.ecommerce.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
    path = "/api/products",
    produces = "application/json",
    consumes = "application/json"
)
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody final ProductRequestDto request) {
        var response = productService.createProduct(request);
        return ResponseEntity.created(null).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable final String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable final String id, @RequestBody final ProductRequestDto request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
