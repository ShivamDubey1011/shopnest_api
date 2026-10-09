package com.shopnest.shopnest_api.product.controller;

import com.shopnest.shopnest_api.common.dto.PageResponse;
import com.shopnest.shopnest_api.common.validation.ValidObjectId;
import com.shopnest.shopnest_api.product.dto.CreateProductRequest;
import com.shopnest.shopnest_api.product.dto.ProductFilter;
import com.shopnest.shopnest_api.product.dto.ProductResponse;
import com.shopnest.shopnest_api.product.dto.UpdateProductRequest;
import com.shopnest.shopnest_api.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest productRequest) {
        ProductResponse created = productService.create(productRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);


    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.getBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> search(@Valid @ModelAttribute ProductFilter productFilter, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<ProductResponse> productResponses = productService.search(productFilter, pageable);
        return ResponseEntity.ok(productResponses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable @ValidObjectId String id, @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        ProductResponse productResponse = productService.update(id, updateProductRequest);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @ValidObjectId String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
