package com.ecommerce;

import java.io.File;
import java.sql.*;

public class Milestone1App {
    
    public static void main(String[] args) {
        System.out.println("🎯 MILESTONE 1: Database Design and Loading Data");
        System.out.println("==================================================");
        
        try {
            // Step 1: Setup Database and Table
            System.out.println("\n📋 Step 1: Setting up database and table structure...");
            DatabaseSetup.main(args);
            
            // Step 2: Load CSV Data
            System.out.println("\n📋 Step 2: Loading CSV data into database...");
            String csvPath = getCSVFilePath(args);
            ProductCSVLoader.main(new String[]{csvPath});
            
            // Step 3: Verify and Show Results
            System.out.println("\n📋 Step 3: Verifying data and showing results...");
            showDatabaseSchema();
            showSampleData();
            showStatistics();
            
            System.out.println("\n✅ MILESTONE 1 COMPLETED SUCCESSFULLY!");
            System.out.println("🎉 Database is ready for the next milestone!");
            
        } catch (Exception e) {
            System.err.println("❌ Error in Milestone 1: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String getCSVFilePath(String[] args) {
        if (args.length > 0) {
            return args[0];
        }
        
        // Try common locations for the CSV file
        String[] possiblePaths = {
            "products.csv",
            "data/products.csv",
            "C:\\Users\\conne\\Downloads\\ecommerce-dataset-main\\ecommerce-dataset-main\\archive.zip\\archive\\products.csv",
            "C:\\Users\\conne\\Downloads\\products.csv"
        };
        
        for (String path : possiblePaths) {
            if (new File(path).exists()) {
                System.out.println("📁 Found CSV file at: " + path);
                return path;
            }
        }
        
        System.out.println("⚠️  CSV file not found in common locations.");
        System.out.println("📝 Please provide the path to products.csv as a command line argument.");
        System.out.println("📝 Example: java -cp target/classes com.ecommerce.Milestone1App path/to/products.csv");
        return "products.csv"; // Default fallback
    }
    
    private static void showDatabaseSchema() {
        System.out.println("\n🗄️  Database Schema:");
        System.out.println("===================");
        
        String schemaSQL = """
            SELECT 
                COLUMN_NAME,
                DATA_TYPE,
                IS_NULLABLE,
                COLUMN_DEFAULT
            FROM INFORMATION_SCHEMA.COLUMNS 
            WHERE TABLE_SCHEMA = 'ecommerce' 
            AND TABLE_NAME = 'products'
            ORDER BY ORDINAL_POSITION
            """;
        
        try (Connection connection = DriverManager.getConnection(
                DatabaseSetup.getConnectionString(), 
                DatabaseSetup.getUsername(), 
                DatabaseSetup.getPassword());
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(schemaSQL)) {
            
            System.out.printf("%-20s %-15s %-10s %-15s%n", "Column", "Type", "Nullable", "Default");
            System.out.println("------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-20s %-15s %-10s %-15s%n",
                    rs.getString("COLUMN_NAME"),
                    rs.getString("DATA_TYPE"),
                    rs.getString("IS_NULLABLE"),
                    rs.getString("COLUMN_DEFAULT") != null ? rs.getString("COLUMN_DEFAULT") : "NULL"
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error showing schema: " + e.getMessage());
        }
    }
    
    private static void showSampleData() {
        System.out.println("\n📋 Sample Data (First 10 records):");
        System.out.println("==================================");
        
        String sampleSQL = """
            SELECT 
                id, 
                name, 
                brand, 
                department, 
                category,
                retail_price,
                cost
            FROM products 
            ORDER BY id 
            LIMIT 10
            """;
        
        try (Connection connection = DriverManager.getConnection(
                DatabaseSetup.getConnectionString(), 
                DatabaseSetup.getUsername(), 
                DatabaseSetup.getPassword());
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sampleSQL)) {
            
            System.out.printf("%-5s %-40s %-15s %-15s %-20s %-10s %-10s%n", 
                "ID", "Name", "Brand", "Department", "Category", "Price", "Cost");
            System.out.println("=".repeat(120));
            
            while (rs.next()) {
                String name = rs.getString("name");
                if (name.length() > 37) {
                    name = name.substring(0, 34) + "...";
                }
                
                System.out.printf("%-5s %-40s %-15s %-15s %-20s $%-9.2f $%-9.2f%n",
                    rs.getInt("id"),
                    name,
                    rs.getString("brand"),
                    rs.getString("department"),
                    rs.getString("category"),
                    rs.getDouble("retail_price"),
                    rs.getDouble("cost")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error showing sample data: " + e.getMessage());
        }
    }
    
    private static void showStatistics() {
        System.out.println("\n📊 Database Statistics:");
        System.out.println("======================");
        
        try (Connection connection = DriverManager.getConnection(
                DatabaseSetup.getConnectionString(), 
                DatabaseSetup.getUsername(), 
                DatabaseSetup.getPassword());
             Statement statement = connection.createStatement()) {
            
            // Total count
            ResultSet countRs = statement.executeQuery("SELECT COUNT(*) as total FROM products");
            if (countRs.next()) {
                System.out.println("📈 Total Products: " + countRs.getInt("total"));
            }
            
            // Department breakdown
            System.out.println("\n🏢 Products by Department:");
            ResultSet deptRs = statement.executeQuery(
                "SELECT department, COUNT(*) as count FROM products GROUP BY department ORDER BY count DESC"
            );
            while (deptRs.next()) {
                System.out.printf("   %-20s: %d products%n", 
                    deptRs.getString("department"), 
                    deptRs.getInt("count"));
            }
            
            // Brand breakdown
            System.out.println("\n🏷️  Top 10 Brands:");
            ResultSet brandRs = statement.executeQuery(
                "SELECT brand, COUNT(*) as count FROM products GROUP BY brand ORDER BY count DESC LIMIT 10"
            );
            while (brandRs.next()) {
                System.out.printf("   %-20s: %d products%n", 
                    brandRs.getString("brand"), 
                    brandRs.getInt("count"));
            }
            
            // Price statistics
            System.out.println("\n💰 Price Statistics:");
            ResultSet priceRs = statement.executeQuery(
                "SELECT MIN(retail_price) as min_price, MAX(retail_price) as max_price, AVG(retail_price) as avg_price FROM products"
            );
            if (priceRs.next()) {
                System.out.printf("   Min Price: $%.2f%n", priceRs.getDouble("min_price"));
                System.out.printf("   Max Price: $%.2f%n", priceRs.getDouble("max_price"));
                System.out.printf("   Avg Price: $%.2f%n", priceRs.getDouble("avg_price"));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error showing statistics: " + e.getMessage());
        }
    }
} 