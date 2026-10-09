package com.shopnest.shopnest_api.product.repository;

import com.shopnest.shopnest_api.product.dto.ProductFilter;
import com.shopnest.shopnest_api.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> search(ProductFilter filter, Pageable pageable);
}
