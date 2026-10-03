package com.shopnest.shopnest_api.product.dto;


import java.math.BigDecimal;
import java.util.List;

/*
*Why records for DTOs:
Immutable: a request can't be changed accidentally halfway through processing.
No Lombok needed: constructor, accessors, equals, hashCode and toString come built in.
Natural fit for Jackson: JSON maps straight to the record.
* */
public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        List<String> tags
) {
}
