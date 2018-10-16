package com.amanda.sales_processor.model;

public class AdjustmentTransaction implements ITransaction {

    private AdjustmentType adjustmentType;
    private ProductDetails productDetails;

    public AdjustmentTransaction(AdjustmentType adjustmentType, ProductDetails productDetails) {
        this.adjustmentType = adjustmentType;
        this.productDetails = productDetails;
    }

    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }
}
