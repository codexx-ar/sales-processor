package com.amanda.sales_processor.services;

import com.amanda.sales_processor.model.AdjustmentTransaction;
import com.amanda.sales_processor.model.SalesTransaction;
import com.amanda.sales_processor.persistence.ItemDetails;
import com.amanda.sales_processor.persistence.SalesProcessorData;
import com.amanda.sales_processor.report.IMessageHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public interface TransactionHandler {

    default void handleSalesEvent(SalesTransaction sale) {
        ItemDetails item = new ItemDetails(sale.getSalesUnit(), sale.getProductDetails().getProductPrice());
        if(SalesProcessorData.getInstance().getItemsMap().containsKey(sale.getProductDetails().getProductName())) {
            SalesProcessorData.getInstance().getItemsMap().get(sale.getProductDetails().getProductName()).add(item);
        }
        else {
            SalesProcessorData.getInstance().getItemsMap().put(
                    sale.getProductDetails().getProductName(), new ArrayList<>(Arrays.asList(item)));
        }
    }

    default void handleAdjustmentEvent(AdjustmentTransaction adjustment) {
        if(!SalesProcessorData.getInstance().getItemsMap().containsKey(adjustment.getProductDetails().getProductName())) {
            IMessageHandler.printFormat("Ignoring " + adjustment.getAdjustmentType().toString()
                    + " with: " + adjustment.getProductDetails().getProductPrice()
                    + " as item: " + adjustment.getProductDetails().getProductName() +" sales not found.");
            return;
        }
        switch (adjustment.getAdjustmentType()) {
            case ADD:
                SalesProcessorData.getInstance().getItemsMap().get(adjustment.getProductDetails().getProductName())
                        .forEach(x -> x.setProductPrice(
                                x.getProductPrice().add(adjustment.getProductDetails().getProductPrice())));
                break;
            case SUBTRACT:
                SalesProcessorData.getInstance().getItemsMap().get(adjustment.getProductDetails().getProductName())
                        .forEach(x -> {
                            if(x.getProductPrice().subtract(
                                    adjustment.getProductDetails().getProductPrice())
                                    .compareTo(BigDecimal.ZERO) > 0)
                                x.setProductPrice(
                                        x.getProductPrice().subtract(adjustment.getProductDetails().getProductPrice()));
                            else x.setProductPrice(BigDecimal.ZERO);
                        });
                break;
            case MULTIPLY:
                SalesProcessorData.getInstance().getItemsMap().get(adjustment.getProductDetails().getProductName())
                        .forEach(x -> x.setProductPrice(
                                x.getProductPrice().multiply(adjustment.getProductDetails().getProductPrice())));
        }
        SalesProcessorData.getInstance().getAdjustmentHistory().add(adjustment);
    }
}
