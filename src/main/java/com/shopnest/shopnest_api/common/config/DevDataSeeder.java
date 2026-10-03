package com.shopnest.shopnest_api.common.config;

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

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() > 0) return;
        Product p = Product.builder()
                .slug("iphone-15-pro")
                .name("iPhone 15 Pro")
                .description("Apple flagship phone")
                .price(new BigDecimal("129999.00"))
                .stock(50)
                .category("electronics")
                .status(ProductStatus.ACTIVE)
                .tags(List.of("apple", "phone"))
                .build();

        Product saved = productRepository.save(p);
        log.info("Seeded product id={} version{} createdAt={}",
                saved.getId(), saved.getVersion(), saved.getCreatedAt());

    }
}
