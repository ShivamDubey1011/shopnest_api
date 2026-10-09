package com.shopnest.shopnest_api.product.service;

import com.shopnest.shopnest_api.common.dto.PageResponse;
import com.shopnest.shopnest_api.common.exception.DuplicateResourceException;
import com.shopnest.shopnest_api.common.exception.InvalidRequestException;
import com.shopnest.shopnest_api.common.exception.ResourceNotFoundException;
import com.shopnest.shopnest_api.common.util.SlugUtil;
import com.shopnest.shopnest_api.product.dto.CreateProductRequest;
import com.shopnest.shopnest_api.product.dto.ProductFilter;
import com.shopnest.shopnest_api.product.dto.ProductResponse;
import com.shopnest.shopnest_api.product.dto.UpdateProductRequest;
import com.shopnest.shopnest_api.product.mapper.ProductMapper;
import com.shopnest.shopnest_api.product.model.Product;
import com.shopnest.shopnest_api.product.model.ProductStatus;
import com.shopnest.shopnest_api.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ProductService {
    private static final String RESOURCE = "Product";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final Set<String> SORTABLE_FIELDS = Set.of("name", "price", "createdAt", "stock");

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse create(CreateProductRequest request) {
        String slug = SlugUtil.toSlug(request.name());
        log.debug("Creating product name={} generatedSlug={}", request.name(), slug);

        if (productRepository.existsBySlug(slug)) {
            log.warn("Duplicate product rejected slug={}", slug);
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

    public List<ProductResponse> getAll(Pageable pageable) {
        return productMapper.toResponseList(productRepository.findAll(pageable).getContent());
    }

    public ProductResponse update(String id, UpdateProductRequest request) {
        Product product = findEntityById(id);
        log.debug("Updating product id={} currentVersion={}", id, product.getVersion());
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

    public PageResponse<ProductResponse> search(ProductFilter productFilter, Pageable pageable) {
        validateSort(pageable);
        validatePriceRange(productFilter);

        Page<Product> page = this.productRepository.search(productFilter, pageable);
        log.debug("Product search returned {} of {} items", page.getNumberOfElements());
        return PageResponse.from(page, productMapper::toResponse);
    }

    private void validateSort(Pageable pageable) {
        pageable.getSort().forEach(order -> {
            if (!SORTABLE_FIELDS.contains(order.getProperty())) {
                throw new InvalidRequestException(
                        "Cannot sort by '%s'. Allowed: %s".formatted(order.getProperty(), SORTABLE_FIELDS));
            }
        });
    }

    private void validatePriceRange(ProductFilter filter) {
        if (filter.minPrice() != null && filter.maxPrice() != null && filter.minPrice().compareTo(filter.maxPrice()) > 0)
            throw new InvalidRequestException("minPrice cannot be greate than maxPrice");


    }
}
