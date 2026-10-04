package com.shopnest.shopnest_api.product.controller;

import com.shopnest.shopnest_api.product.dto.CreateProductRequest;
import com.shopnest.shopnest_api.product.dto.ProductResponse;
import com.shopnest.shopnest_api.product.dto.UpdateProductRequest;
import com.shopnest.shopnest_api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest productRequest) {
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
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> productResponseList = productService.getAll();
        return ResponseEntity.ok(productResponseList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest updateProductRequest) {
        ProductResponse productResponse = productService.update(id, updateProductRequest);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
