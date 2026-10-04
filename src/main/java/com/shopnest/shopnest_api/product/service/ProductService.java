package com.shopnest.shopnest_api.product.service;

import com.shopnest.shopnest_api.common.exception.DuplicateResourceException;
import com.shopnest.shopnest_api.common.exception.ResourceNotFoundException;
import com.shopnest.shopnest_api.common.util.SlugUtil;
import com.shopnest.shopnest_api.product.dto.CreateProductRequest;
import com.shopnest.shopnest_api.product.dto.ProductResponse;
import com.shopnest.shopnest_api.product.dto.UpdateProductRequest;
import com.shopnest.shopnest_api.product.mapper.ProductMapper;
import com.shopnest.shopnest_api.product.model.Product;
import com.shopnest.shopnest_api.product.model.ProductStatus;
import com.shopnest.shopnest_api.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Slf4j
@Service
public class ProductService {
    private static final String RESOURCE = "Product";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse create(CreateProductRequest request) {
        String slug = SlugUtil.toSlug(request.name());

        if (productRepository.existsBySlug(slug)) {
            throw new DuplicateResourceException(RESOURCE, "slug", slug);
        }
        Product product = productMapper.toEntity(request);
        product.setSlug(slug);
        product.setStatus(ProductStatus.DRAFT);

        Product saved = productRepository.save(product);
        log.info("Product created id={} slug={}", saved.getId(), saved.getSlug());
        return productMapper.toResponse(saved);

    }

    public ProductResponse getById(String id) {
        return productMapper.toResponse(findEntityById(id));
    }

    public ProductResponse getBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "slug", slug));
        return productMapper.toResponse(product);

    }

    public List<ProductResponse> getAll() {
        return productMapper.toResponseList(productRepository.findAll());
    }

    public ProductResponse update(String id, UpdateProductRequest request) {
        Product product = findEntityById(id);
        productMapper.updateEntity(request, product);

        Product save = productRepository.save(product);
        log.info("Product update id={} version={}", save.getId(), save.getVersion());
        return productMapper.toResponse(save);
    }

    public void delete(String id) {
        Product product = findEntityById(id);
        productRepository.delete(product);
        log.info("Product deleted id-={}", id);
    }

    private Product findEntityById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id));
    }
}
