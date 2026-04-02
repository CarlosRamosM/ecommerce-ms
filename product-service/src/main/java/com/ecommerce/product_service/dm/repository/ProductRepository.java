package com.ecommerce.product_service.dm.repository;

import com.ecommerce.product_service.dm.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
