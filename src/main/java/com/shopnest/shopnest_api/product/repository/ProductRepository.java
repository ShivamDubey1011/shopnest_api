package com.shopnest.shopnest_api.product.repository;

import com.shopnest.shopnest_api.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


/**
 * Data access for {@link Product} documents in the "products" collection.
 * <p>
 * Spring Data generates the implementation at startup. Method names are parsed
 * into Mongo queries ("derived queries"), e.g. findBySlug → { slug: ? }.
 */
public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

    /**
     * Finds a product by its URL-friendly slug.
     *
     * @param slug e.g. "macbook-air-m3"
     * @return the product, or empty if none matches
     */
    Optional<Product> findBySlug(String slug);


    /**
     * Checks slug existence without loading the document.
     * Cheaper than findBySlug when you only need a yes/no answer.
     *
     * @param slug slug to check
     * @return true if a product with this slug exists
     */
    boolean existsBySlug(String slug);
}
