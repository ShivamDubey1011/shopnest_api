package com.shopnest.shopnest_api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when creating a resource that violates a uniqueness rule
 * (e.g. a product with the same slug already exists).
 * <p>
 * Results in HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    /**
     * @param resource name of the resource type, e.g. "Product"
     * @param field    the unique field that clashed, e.g. "slug"
     * @param value    the duplicate value
     */
    public DuplicateResourceException(String resource, String field, Object value) {
        super("%s already exists with %s: %s".formatted(resource, field, value));
    }
}
