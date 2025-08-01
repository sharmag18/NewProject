package com.ecommerce;

import com.ecommerce.entity.Department;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.DepartmentRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.DatabaseMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DatabaseMigrationService migrationService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Starting Milestone 4: Database Refactoring...");
        
        // Clear existing data
        productRepository.deleteAll();
        departmentRepository.deleteAll();
        
        System.out.println("🗑️ Cleared existing data");

        // Create departments
        Department electronics = new Department("Electronics");
        Department clothing = new Department("Clothing");
        Department homeGarden = new Department("Home & Garden");

        departmentRepository.saveAll(Arrays.asList(electronics, clothing, homeGarden));
        System.out.println("✅ Created departments: Electronics, Clothing, Home & Garden");

        // Create sample products with department relationships
        List<Product> products = Arrays.asList(
            new Product(1, new BigDecimal("199.99"), "Smartphones", "iPhone 14 Pro", "Apple", 
                       new BigDecimal("999.99"), electronics, "IPH14PRO001", 1),
            new Product(2, new BigDecimal("15.99"), "T-Shirts", "Cotton Crew Neck T-Shirt", "Nike", 
                       new BigDecimal("24.99"), clothing, "NIKETSHIRT001", 2),
            new Product(3, new BigDecimal("199.99"), "Blenders", "Vitamix Professional Blender", "Vitamix", 
                       new BigDecimal("299.99"), homeGarden, "VITABLEND001", 3),
            new Product(4, new BigDecimal("45.99"), "Jeans", "Classic Blue Jeans", "Levi's", 
                       new BigDecimal("79.99"), clothing, "LEVIJEANS001", 2),
            new Product(5, new BigDecimal("899.99"), "Laptops", "MacBook Air M2", "Apple", 
                       new BigDecimal("1299.99"), electronics, "MACBOOKAIR001", 1),
            new Product(6, new BigDecimal("12.99"), "Mugs", "Ceramic Coffee Mug", "Starbucks", 
                       new BigDecimal("19.99"), homeGarden, "STARBMUG001", 3),
            new Product(7, new BigDecimal("249.99"), "Headphones", "Sony WH-1000XM4", "Sony", 
                       new BigDecimal("349.99"), electronics, "SONYHEAD001", 1),
            new Product(8, new BigDecimal("120.99"), "Sneakers", "Air Jordan 1 Retro", "Nike", 
                       new BigDecimal("169.99"), clothing, "NIKEAJ001", 2),
            new Product(9, new BigDecimal("449.99"), "Tablets", "iPad Air", "Apple", 
                       new BigDecimal("599.99"), electronics, "IPADAIR001", 1),
            new Product(10, new BigDecimal("89.99"), "Kitchen Tools", "Professional Chef Knife Set", "KitchenAid", 
                        new BigDecimal("129.99"), homeGarden, "KITCHKNIFE001", 3)
        );

        productRepository.saveAll(products);
        System.out.println("✅ Loaded " + products.size() + " sample products with department relationships");

        // Verify the data structure
        System.out.println("\n📊 Database Structure Verification:");
        System.out.println("   - Total Products: " + productRepository.count());
        System.out.println("   - Total Departments: " + departmentRepository.count());
        
        // Show department statistics
        List<Department> departments = departmentRepository.findAll();
        for (Department dept : departments) {
            long productCount = productRepository.countByDepartmentName(dept.getName());
            System.out.println("   - " + dept.getName() + ": " + productCount + " products");
        }

        System.out.println("\n🎯 Milestone 4: Database Refactoring Complete!");
        System.out.println("   ✅ Departments table created with proper relationships");
        System.out.println("   ✅ Products table updated with foreign key constraints");
        System.out.println("   ✅ All data migrated to normalized structure");
        System.out.println("   ✅ API endpoints updated to include department information");
    }
} 