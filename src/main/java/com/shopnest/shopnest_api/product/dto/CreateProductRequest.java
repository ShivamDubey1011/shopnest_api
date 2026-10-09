package com.shopnest.shopnest_api.product.dto;


import com.shopnest.shopnest_api.common.validation.AllowedCategory;
import jakarta.validation.constraints.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

/*
*Why records for DTOs:
Immutable: a request can't be changed accidentally halfway through processing.
No Lombok needed: constructor, accessors, equals, hashCode and toString come built in.
Natural fit for Jackson: JSON maps straight to the record.
* */
public record CreateProductRequest(
        @NotBlank(message = "name is required")
        @Size(min = 3, max = 120, message = "name must be 3-120 characters")
        String name,
        @Size(max = 2000, message = "description must be at most 2000 characters")
        String description,
        @NotNull(message = "price is required")
        @DecimalMin(value = "1.00", message = "price must be at least 1.00")
        @Digits(integer = 10, fraction = 2, message = "price can have at most 2 decimal places")
        BigDecimal price,
        @NotNull(message = "stock is requried")
        @PositiveOrZero(message = "stock cannot be negative")
        @Max(value = 100_000, message = "stock cannot exceed 100000")
        Integer stock,
        @NotBlank(message = "category is required")
        @AllowedCategory
        String category,
        @Size(max = 10, message = "at most 10 tags allowed")
        List<@NotBlank(message = "tag cannont be blank") @Size(max = 30, message = "tag must be at most 30 characters") String>
                tags
) {
}
