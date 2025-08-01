package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Basic queries
    List<Product> findByDepartmentName(String departmentName);
    List<Product> findByBrand(String brand);
    List<Product> findByCategory(String category);
    long countByDepartmentName(String departmentName);

    // Advanced search with multiple criteria
    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
           "(:category IS NULL OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
           "(:departmentName IS NULL OR LOWER(p.department.name) LIKE LOWER(CONCAT('%', :departmentName, '%'))) AND " +
           "(:minPrice IS NULL OR p.retailPrice >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.retailPrice <= :maxPrice)")
    Page<Product> findProductsByCriteria(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("category") String category,
            @Param("departmentName") String departmentName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    // Full-text search
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.department.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Product> searchProducts(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Price range queries
    List<Product> findByRetailPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByRetailPriceGreaterThanEqual(BigDecimal minPrice);
    List<Product> findByRetailPriceLessThanEqual(BigDecimal maxPrice);

    // Statistics queries
    @Query("SELECT COUNT(p) FROM Product p")
    long getTotalProductCount();

    @Query("SELECT COUNT(DISTINCT p.brand) FROM Product p")
    long getUniqueBrandCount();

    @Query("SELECT COUNT(DISTINCT p.category) FROM Product p")
    long getUniqueCategoryCount();

    @Query("SELECT AVG(p.retailPrice) FROM Product p")
    BigDecimal getAveragePrice();

    @Query("SELECT MIN(p.retailPrice) FROM Product p")
    BigDecimal getMinPrice();

    @Query("SELECT MAX(p.retailPrice) FROM Product p")
    BigDecimal getMaxPrice();

    // Department statistics
    @Query("SELECT p.department.name, COUNT(p) FROM Product p GROUP BY p.department.name")
    List<Object[]> getProductCountByDepartment();

    // Brand statistics
    @Query("SELECT p.brand, COUNT(p) FROM Product p GROUP BY p.brand ORDER BY COUNT(p) DESC")
    List<Object[]> getProductCountByBrand();

    // Category statistics
    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category ORDER BY COUNT(p) DESC")
    List<Object[]> getProductCountByCategory();

    // Top categories
    @Query(value = "SELECT p.category FROM Product p GROUP BY p.category ORDER BY COUNT(p) DESC LIMIT :limit", nativeQuery = true)
    List<String> findTopCategories(@Param("limit") int limit);
}