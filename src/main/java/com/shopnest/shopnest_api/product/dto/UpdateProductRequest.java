package com.shopnest.shopnest_api.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        List<String> tags
) {
}
