package com.shopnest.shopnest_api.common.config;

import com.shopnest.shopnest_api.common.util.SlugUtil;
import com.shopnest.shopnest_api.product.model.Product;
import com.shopnest.shopnest_api.product.model.ProductStatus;
import com.shopnest.shopnest_api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    /**
     * Seeds a varied product catalog in dev only, so pagination, sorting and
     * filtering have realistic data. Skips if any products already exist.
     */
    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            log.info("Products already exist, skipping seed");
            return;
        }

        List<Product> products = List.of(
                build("iPhone 15 Pro", "129999", 50, "electronics", ProductStatus.ACTIVE, "apple", "phone"),
                build("MacBook Air M3", "114900", 25, "electronics", ProductStatus.ACTIVE, "apple", "laptop"),
                build("Sony WH-1000XM5", "29990", 40, "electronics", ProductStatus.ACTIVE, "sony", "headphones"),
                build("Samsung Galaxy S24", "79999", 0, "electronics", ProductStatus.OUT_OF_STOCK, "samsung", "phone"),
                build("Dell XPS 13", "99990", 10, "electronics", ProductStatus.DRAFT, "dell", "laptop"),
                build("Boat Airdopes 141", "1299", 200, "electronics", ProductStatus.ACTIVE, "boat", "earbuds"),
                build("Clean Code", "699", 100, "books", ProductStatus.ACTIVE, "programming"),
                build("Effective Java", "899", 80, "books", ProductStatus.ACTIVE, "programming", "java"),
                build("Designing Data-Intensive Applications", "1499", 60, "books", ProductStatus.ACTIVE, "programming"),
                build("Atomic Habits", "499", 150, "books", ProductStatus.ACTIVE, "self-help"),
                build("Levi's 511 Jeans", "2999", 70, "fashion", ProductStatus.ACTIVE, "levis", "jeans"),
                build("Nike Air Max", "8995", 30, "fashion", ProductStatus.ACTIVE, "nike", "shoes"),
                build("Adidas Ultraboost", "16999", 0, "fashion", ProductStatus.OUT_OF_STOCK, "adidas", "shoes"),
                build("Puma Hoodie", "2499", 45, "fashion", ProductStatus.DRAFT, "puma", "hoodie")
        );

        productRepository.saveAll(products);
        log.info("Seeded {} products", products.size());
    }

    /**
     * Small helper to keep the seed list readable.
     */
    private Product build(String name, String price, int stock, String category,
                          ProductStatus status, String... tags) {
        Product p = new Product();
        p.setName(name);
        p.setSlug(SlugUtil.toSlug(name));
        p.setDescription(name + " description");
        p.setPrice(new BigDecimal(price));
        p.setStock(stock);
        p.setCategory(category);
        p.setStatus(status);
        p.setTags(List.of(tags));
        return p;
    }
}
