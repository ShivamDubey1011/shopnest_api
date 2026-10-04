package com.shopnest.shopnest_api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a requested resource does not exist in the database.
 * <p>
 * Results in HTTP 404. Temporary mapping via {@link ResponseStatus};
 * will be handled centrally by GlobalExceptionHandler in Step 10.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     * @param resource name of the resource type, e.g. "Product"
     * @param field    field used for lookup, e.g. "id" or "slug"
     * @param value    the value that was not found
     */
    public ResourceNotFoundException(String resource, String field, Object value) {
        super("%s not found with %s: %s".formatted(resource, field, value));
    }

}
