package com.shopnest.shopnest_api.product.mapper;

import com.shopnest.shopnest_api.product.dto.CreateProductRequest;
import com.shopnest.shopnest_api.product.dto.ProductResponse;
import com.shopnest.shopnest_api.product.dto.UpdateProductRequest;
import com.shopnest.shopnest_api.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(CreateProductRequest request) {
        if (request == null) {
            return null;
        }
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(request.category());
        product.setTags(request.tags() != null ? new ArrayList<>(request.tags()) : null);
        return product;
    }

    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResponse(
                product.getId(),
                product.getSlug(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getStatus(),
                product.getTags(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null) {
            return List.of();
        }
        return products.stream().map(this::toResponse).toList();

    }

    // Update: PATCH style. Only non-null fields change; id, version and createdAt stay untouched.
    public void updateEntity(UpdateProductRequest request, Product product) {
        if (request == null || product == null) {
            return;
        }

        if (request.name() != null) product.setName(request.name());
        if (request.description() != null) product.setDescription(request.description());
        if (request.price() != null) product.setPrice(request.price());
        if (request.stock() != null) product.setStock(request.stock());
        if (request.category() != null) product.setCategory(request.category());
        if (request.tags() != null) product.setTags(new ArrayList<>(request.tags()));
    }
}

