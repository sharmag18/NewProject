package com.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException {
    
    public ProductNotFoundException(String message) {
        super(message);
    }
    
    public ProductNotFoundException(Integer id) {
        super("Product with ID " + id + " not found");
    }
} 