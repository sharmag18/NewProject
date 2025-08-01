package com.ecommerce.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "retail_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal retailPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "sku", nullable = false, length = 100)
    private String sku;

    @Column(name = "distribution_center_id", nullable = false)
    private Integer distributionCenterId;

    // Default constructor
    public Product() {}

    // Constructor with all fields
    public Product(Integer id, BigDecimal cost, String category, String name, String brand,
                   BigDecimal retailPrice, Department department, String sku, Integer distributionCenterId) {
        this.id = id;
        this.cost = cost;
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.retailPrice = retailPrice;
        this.department = department;
        this.sku = sku;
        this.distributionCenterId = distributionCenterId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getDistributionCenterId() {
        return distributionCenterId;
    }

    public void setDistributionCenterId(Integer distributionCenterId) {
        this.distributionCenterId = distributionCenterId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", department='" + (department != null ? department.getName() : "null") + '\'' +
                ", retailPrice=" + retailPrice +
                '}';
    }
} 