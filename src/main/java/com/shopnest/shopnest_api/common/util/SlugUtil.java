package com.shopnest.shopnest_api.common.util;

import org.hibernate.validator.constraints.Normalized;

import java.text.Normalizer;
import java.util.Locale;

public class SlugUtil {

    private SlugUtil() {
    }

    public static String toSlug(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");               // remove accents: é -> e
        return normalized.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")             // anything else -> hyphen
                .replaceAll("(^-|-$)", "");                // trim hyphens at the ends
    }
}
