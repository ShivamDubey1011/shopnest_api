package com.shopnest.shopnest_api.product.dto;

import com.shopnest.shopnest_api.product.model.ProductStatus;

import java.math.BigDecimal;

public record ProductFilter(
        String q,
        String category,
        ProductStatus status,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String tag
) {
}
