package com.ecommerce.service;

import com.ecommerce.dto.PaginatedResponse;
import com.ecommerce.dto.ProductSearchRequest;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final DepartmentService departmentService;

    @Value("${app.search.max-results:1000}")
    private int maxResults;

    @Value("${app.search.max-page-size:100}")
    private int maxPageSize;

    public ProductService(ProductRepository productRepository, DepartmentService departmentService) {
        this.productRepository = productRepository;
        this.departmentService = departmentService;
    }

    @Cacheable(value = "products", key = "#page + '_' + #size + '_' + #sortBy + '_' + #sortDirection")
    public PaginatedResponse<Product> getAllProducts(int page, int size, String sortBy, String sortDirection) {
        // Validate and adjust page size
        size = Math.min(size, maxPageSize);
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        
        Page<Product> productPage = productRepository.findAll(pageable);
        
        return new PaginatedResponse<>(
                productPage.getContent(),
                page,
                size,
                productPage.getTotalElements()
        );
    }

    @Cacheable(value = "product", key = "#id")
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    @Cacheable(value = "productsByDepartment", key = "#departmentName + '_' + #page + '_' + #size")
    public PaginatedResponse<Product> getProductsByDepartment(String departmentName, int page, int size) {
        size = Math.min(size, maxPageSize);
        Pageable pageable = PageRequest.of(page - 1, size);
        
        // Use the advanced search method with department filter
        Page<Product> productPage = productRepository.findProductsByCriteria(
                null, null, null, departmentName, null, null, pageable);
        
        return new PaginatedResponse<>(
                productPage.getContent(),
                page,
                size,
                productPage.getTotalElements()
        );
    }

    @Cacheable(value = "productsByBrand", key = "#brand + '_' + #page + '_' + #size")
    public PaginatedResponse<Product> getProductsByBrand(String brand, int page, int size) {
        size = Math.min(size, maxPageSize);
        Pageable pageable = PageRequest.of(page - 1, size);
        
        Page<Product> productPage = productRepository.findProductsByCriteria(
                null, brand, null, null, null, null, pageable);
        
        return new PaginatedResponse<>(
                productPage.getContent(),
                page,
                size,
                productPage.getTotalElements()
        );
    }

    @Cacheable(value = "productsByCategory", key = "#category + '_' + #page + '_' + #size")
    public PaginatedResponse<Product> getProductsByCategory(String category, int page, int size) {
        size = Math.min(size, maxPageSize);
        Pageable pageable = PageRequest.of(page - 1, size);
        
        Page<Product> productPage = productRepository.findProductsByCriteria(
                null, null, category, null, null, null, pageable);
        
        return new PaginatedResponse<>(
                productPage.getContent(),
                page,
                size,
                productPage.getTotalElements()
        );
    }

    @Cacheable(value = "searchProducts", key = "#searchTerm + '_' + #page + '_' + #size")
    public PaginatedResponse<Product> searchProducts(String searchTerm, int page, int size) {
        size = Math.min(size, maxPageSize);
        Pageable pageable = PageRequest.of(page - 1, size);
        
        Page<Product> productPage = productRepository.searchProducts(searchTerm, pageable);
        
        return new PaginatedResponse<>(
                productPage.getContent(),
                page,
                size,
                productPage.getTotalElements()
        );
    }

    public PaginatedResponse<Product> advancedSearch(ProductSearchRequest request) {
        // Validate and adjust parameters
        int page = Math.max(1, request.getPage());
        int size = Math.min(Math.max(1, request.getSize()), maxPageSize);
        
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        
        Page<Product> productPage = productRepository.findProductsByCriteria(
                request.getName(),
                request.getBrand(),
                request.getCategory(),
                request.getDepartmentName(),
                request.getMinPrice(),
                request.getMaxPrice(),
                pageable
        );
        
        return new PaginatedResponse<>(
                productPage.getContent(),
                page,
                size,
                productPage.getTotalElements()
        );
    }

    @Cacheable(value = "productStats")
    public Map<String, Object> getProductStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalProducts", productRepository.getTotalProductCount());
        stats.put("uniqueBrands", productRepository.getUniqueBrandCount());
        stats.put("uniqueCategories", productRepository.getUniqueCategoryCount());
        stats.put("averagePrice", productRepository.getAveragePrice());
        stats.put("minPrice", productRepository.getMinPrice());
        stats.put("maxPrice", productRepository.getMaxPrice());
        
        return stats;
    }

    @Cacheable(value = "departmentStats")
    public Map<String, Object> getDepartmentStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Object[]> departmentCounts = productRepository.getProductCountByDepartment();
        Map<String, Long> departmentMap = new HashMap<>();
        
        for (Object[] result : departmentCounts) {
            departmentMap.put((String) result[0], (Long) result[1]);
        }
        
        stats.put("departments", departmentMap);
        stats.put("totalDepartments", departmentMap.size());
        
        return stats;
    }

    @Cacheable(value = "brandStats")
    public Map<String, Object> getBrandStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Object[]> brandCounts = productRepository.getProductCountByBrand();
        Map<String, Long> brandMap = new HashMap<>();
        
        for (Object[] result : brandCounts) {
            brandMap.put((String) result[0], (Long) result[1]);
        }
        
        stats.put("brands", brandMap);
        stats.put("totalBrands", brandMap.size());
        
        return stats;
    }

    @Cacheable(value = "categoryStats")
    public Map<String, Object> getCategoryStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Object[]> categoryCounts = productRepository.getProductCountByCategory();
        Map<String, Long> categoryMap = new HashMap<>();
        
        for (Object[] result : categoryCounts) {
            categoryMap.put((String) result[0], (Long) result[1]);
        }
        
        stats.put("categories", categoryMap);
        stats.put("totalCategories", categoryMap.size());
        
        return stats;
    }

    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return productRepository.findByRetailPriceBetween(minPrice, maxPrice);
        } else if (minPrice != null) {
            return productRepository.findByRetailPriceGreaterThanEqual(minPrice);
        } else if (maxPrice != null) {
            return productRepository.findByRetailPriceLessThanEqual(maxPrice);
        }
        return productRepository.findAll();
    }

    @CacheEvict(value = {"products", "product", "productsByDepartment", "productsByBrand", 
                         "productsByCategory", "searchProducts", "productStats", "departmentStats", 
                         "brandStats", "categoryStats"}, allEntries = true)
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = {"products", "product", "productsByDepartment", "productsByBrand", 
                         "productsByCategory", "searchProducts", "productStats", "departmentStats", 
                         "brandStats", "categoryStats"}, allEntries = true)
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public void clearAllCaches() {
        // This method can be called to manually clear all caches
        // In a real application, you might want to use CacheManager
    }

    public Long getTotalProductCount() {
        return productRepository.count();
    }

    public List<String> getTopCategories(int limit) {
        return productRepository.findTopCategories(limit);
    }
}