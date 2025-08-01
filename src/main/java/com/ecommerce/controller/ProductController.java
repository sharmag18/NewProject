package com.ecommerce.controller;

import com.ecommerce.dto.PaginatedResponse;
import com.ecommerce.dto.ProductSearchRequest;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @Value("${app.search.default-page-size:20}")
    private int defaultPageSize;

    @Value("${app.search.max-page-size:100}")
    private int maxPageSize;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products with advanced pagination and sorting
     */
    @GetMapping
    public ResponseEntity<PaginatedResponse<Product>> getAllProducts(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size,
            @RequestParam(defaultValue = "name") @Pattern(regexp = "^(name|brand|category|retailPrice|cost)$") String sortBy,
            @RequestParam(defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sortDirection) {
        
        size = Math.min(size, maxPageSize);
        PaginatedResponse<Product> response = productService.getAllProducts(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Advanced search with multiple criteria
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse<Product>> advancedSearch(@Valid @RequestBody ProductSearchRequest request) {
        PaginatedResponse<Product> response = productService.advancedSearch(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Search products by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<Product>> searchProducts(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size) {
        
        size = Math.min(size, maxPageSize);
        PaginatedResponse<Product> response = productService.searchProducts(q, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get products by department
     */
    @GetMapping("/department/{departmentName}")
    public ResponseEntity<PaginatedResponse<Product>> getProductsByDepartment(
            @PathVariable String departmentName,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size) {
        
        size = Math.min(size, maxPageSize);
        PaginatedResponse<Product> response = productService.getProductsByDepartment(departmentName, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get products by brand
     */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<PaginatedResponse<Product>> getProductsByBrand(
            @PathVariable String brand,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size) {
        
        size = Math.min(size, maxPageSize);
        PaginatedResponse<Product> response = productService.getProductsByBrand(brand, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<PaginatedResponse<Product>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size) {
        
        size = Math.min(size, maxPageSize);
        PaginatedResponse<Product> response = productService.getProductsByCategory(category, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get products by price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    /**
     * Get product statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getProductStatistics() {
        Map<String, Object> stats = productService.getProductStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get department statistics
     */
    @GetMapping("/stats/departments")
    public ResponseEntity<Map<String, Object>> getDepartmentStatistics() {
        Map<String, Object> stats = productService.getDepartmentStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get brand statistics
     */
    @GetMapping("/stats/brands")
    public ResponseEntity<Map<String, Object>> getBrandStatistics() {
        Map<String, Object> stats = productService.getBrandStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get category statistics
     */
    @GetMapping("/stats/categories")
    public ResponseEntity<Map<String, Object>> getCategoryStatistics() {
        Map<String, Object> stats = productService.getCategoryStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Create a new product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /**
     * Update an existing product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @Valid @RequestBody Product product) {
        return productService.getProductById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    Product updatedProduct = productService.saveProduct(product);
                    return ResponseEntity.ok(updatedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(product -> {
                    productService.deleteProduct(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Clear all caches (admin endpoint)
     */
    @PostMapping("/cache/clear")
    public ResponseEntity<Map<String, String>> clearCache() {
        productService.clearAllCaches();
        return ResponseEntity.ok(Map.of("message", "All caches cleared successfully"));
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "Product API"));
    }
} 