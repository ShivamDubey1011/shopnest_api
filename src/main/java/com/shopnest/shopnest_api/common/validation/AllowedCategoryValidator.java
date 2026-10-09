package com.shopnest.shopnest_api.common.validation;

import com.shopnest.shopnest_api.common.config.CatalogProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllowedCategoryValidator implements ConstraintValidator<AllowedCategory, String> {

    private final CatalogProperties catalogProperties;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        boolean allowed = catalogProperties.getAllowedCategories().stream()
                .anyMatch(c -> c.equalsIgnoreCase(value.trim()));

        if (!allowed) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "must be one of " + catalogProperties.getAllowedCategories())
                    .addConstraintViolation();

        }
        return allowed;

//        return false;
    }
}
