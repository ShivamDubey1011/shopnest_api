package com.shopnest.shopnest_api.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * Type-safe binding of everything under "shopnest.catalog" in application.yml.
 * <p>
 * Why not @Value("${...}") everywhere?
 * <ul>
 *   <li>One typed object instead of string keys scattered across classes</li>
 *   <li>Lists/maps bind naturally (allowed-categories → Set)</li>
 *   <li>IDE autocomplete and a single place to see all app settings</li>
 * </ul>
 * Kebab-case in YAML (allowed-categories) maps to camelCase here (allowedCategories).
 *
 * @param allowedCategories categories a product may belong to
 */
@ConfigurationProperties(prefix = "shopnest.catalog")
public class CatalogProperties {

    private Set<String> allowedCategories;

    public CatalogProperties() {
    }

    public Set<String> getAllowedCategories() {
        return allowedCategories;
    }

    public void setAllowedCategories(Set<String> allowedCategories) {
        this.allowedCategories = allowedCategories;
    }
}
