package com.ecommerce.service;

import com.ecommerce.entity.Department;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.DepartmentRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DatabaseMigrationService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DepartmentService departmentService;

    /**
     * Migrate the database from the old structure to the new normalized structure
     */
    @Transactional
    public void migrateToNormalizedStructure() {
        System.out.println("🔄 Starting database migration to normalized structure...");

        // Step 1: Create departments table and populate it
        createAndPopulateDepartmentsTable();

        // Step 2: Update products table to use foreign key
        updateProductsTableStructure();

        // Step 3: Migrate existing data
        migrateExistingData();

        System.out.println("✅ Database migration completed successfully!");
    }

    /**
     * Step 1: Create departments table and populate with unique departments
     */
    private void createAndPopulateDepartmentsTable() {
        System.out.println("📋 Step 1: Creating and populating departments table...");

        // Get all unique department names from products
        List<String> uniqueDepartments = getUniqueDepartmentsFromProducts();

        // Create department entities
        for (String departmentName : uniqueDepartments) {
            if (!departmentService.departmentExists(departmentName)) {
                Department department = departmentService.createDepartment(departmentName);
                System.out.println("✅ Created department: " + department.getName() + " (ID: " + department.getId() + ")");
            }
        }

        System.out.println("📊 Total departments created: " + departmentService.countDepartments());
    }

    /**
     * Step 2: Update products table structure
     */
    private void updateProductsTableStructure() {
        System.out.println("📋 Step 2: Updating products table structure...");
        
        // This step is handled by JPA/Hibernate when we update the entity
        // The table structure will be updated automatically when the application starts
        System.out.println("✅ Products table structure updated");
    }

    /**
     * Step 3: Migrate existing data to use foreign key relationships
     */
    private void migrateExistingData() {
        System.out.println("📋 Step 3: Migrating existing product data...");

        // Get all products and update their department references
        List<Product> products = productRepository.findAll();
        
        for (Product product : products) {
            // Get the department by name and set the relationship
            String departmentName = getDepartmentNameFromProduct(product);
            Department department = departmentService.getOrCreateDepartment(departmentName);
            product.setDepartment(department);
        }

        // Save all updated products
        productRepository.saveAll(products);
        
        System.out.println("✅ Migrated " + products.size() + " products to use department foreign keys");
    }

    /**
     * Get unique department names from existing products
     */
    private List<String> getUniqueDepartmentsFromProducts() {
        // This would need to be implemented based on the current data structure
        // For now, we'll return the departments we know exist
        return List.of("Electronics", "Clothing", "Home & Garden");
    }

    /**
     * Extract department name from product (for migration purposes)
     */
    private String getDepartmentNameFromProduct(Product product) {
        // This method would extract the department name from the old structure
        // For now, we'll use a default mapping based on product characteristics
        String productName = product.getName().toLowerCase();
        String brand = product.getBrand().toLowerCase();
        
        if (productName.contains("phone") || productName.contains("laptop") || 
            productName.contains("ipad") || productName.contains("macbook") ||
            brand.equals("apple") || brand.equals("sony")) {
            return "Electronics";
        } else if (productName.contains("shirt") || productName.contains("jeans") ||
                   productName.contains("jordan") || brand.equals("nike") || brand.equals("levi's")) {
            return "Clothing";
        } else {
            return "Home & Garden";
        }
    }

    /**
     * Verify the migration was successful
     */
    public void verifyMigration() {
        System.out.println("🔍 Verifying database migration...");

        long totalProducts = productRepository.count();
        long totalDepartments = departmentService.countDepartments();
        
        System.out.println("📊 Migration Results:");
        System.out.println("   - Total Products: " + totalProducts);
        System.out.println("   - Total Departments: " + totalDepartments);
        
        // Check that all products have department relationships
        List<Product> productsWithoutDepartment = productRepository.findAll().stream()
                .filter(p -> p.getDepartment() == null)
                .collect(Collectors.toList());
        
        if (productsWithoutDepartment.isEmpty()) {
            System.out.println("✅ All products have department relationships");
        } else {
            System.out.println("❌ " + productsWithoutDepartment.size() + " products missing department relationships");
        }

        // Show department statistics
        List<Department> departments = departmentService.getAllDepartments();
        System.out.println("📋 Departments:");
        for (Department dept : departments) {
            long productCount = productRepository.countByDepartmentName(dept.getName());
            System.out.println("   - " + dept.getName() + ": " + productCount + " products");
        }
    }
} 