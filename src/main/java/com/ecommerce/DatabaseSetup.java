package com.ecommerce;

import java.sql.*;

public class DatabaseSetup {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "ecommerce";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sh1vam.sharma@23";

    public static void main(String[] args) {
        createDatabase();
        createProductsTable();
        System.out.println("✅ Database and table setup completed successfully!");
    }

    private static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + 
                                     " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
            
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createDatabaseSQL);
                System.out.println("✅ Database 'ecommerce' created successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error creating database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createProductsTable() {
        String jdbcURL = JDBC_URL + DB_NAME;
        
        try (Connection connection = DriverManager.getConnection(jdbcURL, USERNAME, PASSWORD)) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS products (
                    id INT PRIMARY KEY,
                    cost DECIMAL(10,2) NOT NULL,
                    category VARCHAR(255) NOT NULL,
                    name VARCHAR(500) NOT NULL,
                    brand VARCHAR(255) NOT NULL,
                    retail_price DECIMAL(10,2) NOT NULL,
                    department VARCHAR(255) NOT NULL,
                    sku VARCHAR(100) NOT NULL,
                    distribution_center_id INT NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    INDEX idx_department (department),
                    INDEX idx_category (category),
                    INDEX idx_brand (brand),
                    INDEX idx_sku (sku)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                """;
            
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
                System.out.println("✅ Products table created successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error creating products table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getConnectionString() {
        return JDBC_URL + DB_NAME;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
} 