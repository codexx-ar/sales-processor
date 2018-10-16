package com.amanda.sales_processor.model;

public class SalesTransaction implements ITransaction {

    private int salesUnit;
    private ProductDetails productDetails;

    public SalesTransaction(int salesUnit, ProductDetails productDetails) {
        this.salesUnit = salesUnit;
        this.productDetails = productDetails;
    }

    public int getSalesUnit() {
        return salesUnit;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }
}
