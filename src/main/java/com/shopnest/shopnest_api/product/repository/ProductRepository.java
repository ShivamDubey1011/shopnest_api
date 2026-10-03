package com.shopnest.shopnest_api.product.repository;

import com.shopnest.shopnest_api.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
