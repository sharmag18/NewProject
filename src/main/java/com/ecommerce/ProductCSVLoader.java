package com.ecommerce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCSVLoader {
    private static final int BATCH_SIZE = 100;
    private static final String CSV_FILE_PATH = "products.csv"; // Update this path to your CSV file location

    public static void main(String[] args) {
        String csvFilePath = args.length > 0 ? args[0] : CSV_FILE_PATH;
        
        System.out.println("🚀 Starting CSV data loading process...");
        System.out.println("📁 CSV File: " + csvFilePath);
        
        try {
            loadProductsFromCSV(csvFilePath);
            verifyDataLoad();
        } catch (Exception e) {
            System.err.println("❌ Error during data loading: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadProductsFromCSV(String csvFilePath) {
        String jdbcURL = DatabaseSetup.getConnectionString();
        String username = DatabaseSetup.getUsername();
        String password = DatabaseSetup.getPassword();

        int totalRecords = 0;
        int successfulRecords = 0;
        int failedRecords = 0;
        List<String> errors = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            connection.setAutoCommit(false);

            // Clear existing data
            try (Statement clearStatement = connection.createStatement()) {
                clearStatement.executeUpdate("DELETE FROM products");
                System.out.println("🧹 Cleared existing data from products table");
            }

            String sql = """
                INSERT INTO products (id, cost, category, name, brand, retail_price, department, sku, distribution_center_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
            
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath))) {
                
                String lineText;
                int batchCount = 0;

                // Skip header
                String header = lineReader.readLine();
                if (header != null) {
                    System.out.println("📋 CSV Header: " + header);
                }

                while ((lineText = lineReader.readLine()) != null) {
                    totalRecords++;
                    
                    try {
                        String[] data = parseCSVLine(lineText);
                        
                        if (data.length < 9) {
                            errors.add("Line " + totalRecords + ": Insufficient data columns");
                            failedRecords++;
                            continue;
                        }

                        // Parse and validate data
                        int id = Integer.parseInt(data[0].trim());
                        double cost = Double.parseDouble(data[1].trim());
                        String category = cleanString(data[2]);
                        String name = cleanString(data[3]);
                        String brand = cleanString(data[4]);
                        double retailPrice = Double.parseDouble(data[5].trim());
                        String department = cleanString(data[6]);
                        String sku = cleanString(data[7]);
                        int distributionCenterId = Integer.parseInt(data[8].trim());

                        // Set parameters
                        statement.setInt(1, id);
                        statement.setDouble(2, cost);
                        statement.setString(3, category);
                        statement.setString(4, name);
                        statement.setString(5, brand);
                        statement.setDouble(6, retailPrice);
                        statement.setString(7, department);
                        statement.setString(8, sku);
                        statement.setInt(9, distributionCenterId);

                        statement.addBatch();
                        successfulRecords++;

                        if (++batchCount % BATCH_SIZE == 0) {
                            statement.executeBatch();
                            System.out.println("📊 Processed " + totalRecords + " records...");
                        }

                    } catch (NumberFormatException e) {
                        errors.add("Line " + totalRecords + ": Invalid number format - " + e.getMessage());
                        failedRecords++;
                    } catch (Exception e) {
                        errors.add("Line " + totalRecords + ": " + e.getMessage());
                        failedRecords++;
                    }
                }

                // Execute remaining batch
                if (batchCount % BATCH_SIZE != 0) {
                    statement.executeBatch();
                }

                connection.commit();
                System.out.println("✅ Data loading completed!");
                System.out.println("📈 Summary:");
                System.out.println("   Total records processed: " + totalRecords);
                System.out.println("   Successful inserts: " + successfulRecords);
                System.out.println("   Failed records: " + failedRecords);

                if (!errors.isEmpty()) {
                    System.out.println("⚠️  Errors encountered:");
                    errors.forEach(error -> System.out.println("   " + error));
                }

            }
        } catch (IOException | SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        
        return result.toArray(new String[0]);
    }

    private static String cleanString(String input) {
        if (input == null) return "";
        return input.trim().replaceAll("^\"|\"$", ""); // Remove surrounding quotes
    }

    private static void verifyDataLoad() {
        String jdbcURL = DatabaseSetup.getConnectionString();
        String username = DatabaseSetup.getUsername();
        String password = DatabaseSetup.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            
            // Count total records
            try (Statement countStatement = connection.createStatement()) {
                ResultSet countResult = countStatement.executeQuery("SELECT COUNT(*) FROM products");
                if (countResult.next()) {
                    System.out.println("🔍 Total products in database: " + countResult.getInt(1));
                }
            }

            // Show sample records
            System.out.println("\n📋 Sample Products:");
            try (Statement sampleStatement = connection.createStatement()) {
                ResultSet sampleResult = sampleStatement.executeQuery(
                    "SELECT id, name, brand, department, retail_price FROM products LIMIT 5"
                );
                
                while (sampleResult.next()) {
                    System.out.printf("   ID: %d | %s | %s | %s | $%.2f%n",
                        sampleResult.getInt("id"),
                        sampleResult.getString("name"),
                        sampleResult.getString("brand"),
                        sampleResult.getString("department"),
                        sampleResult.getDouble("retail_price")
                    );
                }
            }

            // Show department statistics
            System.out.println("\n📊 Department Statistics:");
            try (Statement deptStatement = connection.createStatement()) {
                ResultSet deptResult = deptStatement.executeQuery(
                    "SELECT department, COUNT(*) as count FROM products GROUP BY department ORDER BY count DESC"
                );
                
                while (deptResult.next()) {
                    System.out.printf("   %s: %d products%n",
                        deptResult.getString("department"),
                        deptResult.getInt("count")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error during verification: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 