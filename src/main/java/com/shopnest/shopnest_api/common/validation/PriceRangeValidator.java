package com.shopnest.shopnest_api.common.validation;

import com.shopnest.shopnest_api.product.dto.ProductFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceRangeValidator implements ConstraintValidator<ValidPriceRange, ProductFilter> {


    @Override
    public boolean isValid(ProductFilter productFilter, ConstraintValidatorContext constraintValidatorContext) {
        if (productFilter == null || productFilter.minPrice() == null || productFilter.maxPrice() == null) {
            return true;
        }

        return productFilter.minPrice().compareTo(productFilter.maxPrice()) <= 0;
    }
}
