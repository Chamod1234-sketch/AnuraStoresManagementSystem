package com.anurastores.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Product {

    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;
    private String barcode;
    private BigDecimal sellingPrice;
    private int reorderLevel;
    private Date expiryDate;
    private String status;

    public Product() {
    }

    public Product(int productId,
                   String productName,
                   int categoryId,
                   String categoryName,
                   String barcode,
                   BigDecimal sellingPrice,
                   int reorderLevel,
                   Date expiryDate,
                   String status) {

        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.barcode = barcode;
        this.sellingPrice = sellingPrice;
        this.reorderLevel = reorderLevel;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}