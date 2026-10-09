package com.shopnest.shopnest_api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriceRange {
    String message() default "minPrice cannot be greater than maxPrice";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
