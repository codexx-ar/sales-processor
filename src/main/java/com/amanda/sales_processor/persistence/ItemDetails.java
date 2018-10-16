package com.amanda.sales_processor.persistence;

import java.math.BigDecimal;

public class ItemDetails {

    private int salesQty;
    private BigDecimal productPrice;

    public ItemDetails(int salesQty, BigDecimal productPrice) {
        this.salesQty = salesQty;
        this.productPrice = productPrice;
    }

    public int getSalesQty() {
        return salesQty;
    }

    public void setSalesQty(int salesQty) {
        this.salesQty = salesQty;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
