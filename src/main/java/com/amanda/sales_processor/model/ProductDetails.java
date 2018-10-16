package com.amanda.sales_processor.model;

import java.math.BigDecimal;

public class ProductDetails {

    private String productName;
    private BigDecimal productPrice;

    public ProductDetails(String productName, BigDecimal productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
