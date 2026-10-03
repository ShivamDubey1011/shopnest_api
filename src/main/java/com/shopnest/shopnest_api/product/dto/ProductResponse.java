package com.shopnest.shopnest_api.product.dto;

import com.shopnest.shopnest_api.product.model.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ProductResponse(
        String id,
        String slug,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        ProductStatus status,
        List<String> tags,
        Instant createdAt,
        Instant updatedAt
) {
}