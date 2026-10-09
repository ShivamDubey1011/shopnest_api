package com.shopnest.shopnest_api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Pattern(regexp = "^[a-fA-F0-9]{24}$")
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidObjectId {

    String message() default "must be a valid 24-character id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
