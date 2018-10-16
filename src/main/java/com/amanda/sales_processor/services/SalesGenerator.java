package com.amanda.sales_processor.services;

import com.amanda.sales_processor.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SalesGenerator {

    String[] productNames = {"apple", "banana", "mango", "guava", "pineapple"};
    Random randomNumberGen = new Random();

    public List<SalesEvent> generateSales() {
        List<SalesEvent> salesEvents = new ArrayList<>();

        // create 75 transactions
        for(int i = 0; i < 75; i++) {
            TransactionType transactionType = TransactionType.values()[
                    randomNumberGen.nextInt(TransactionType.values().length)];
            ITransaction transaction = null;
            BigDecimal productPrice = new BigDecimal((randomNumberGen.nextInt(20) + 1) * 5);
            ProductDetails productDetails = new ProductDetails(
                    productNames[randomNumberGen.nextInt(productNames.length)], productPrice);
            switch (transactionType) {
                case SALE:
                    // sale transaction
                    transaction = new SalesTransaction(randomNumberGen.nextInt(20) + 1,
                        productDetails);
                    break;
                case ADJUSTMENT:
                    // adjustment transaction
                    transaction = new AdjustmentTransaction(AdjustmentType.values()[randomNumberGen.nextInt(AdjustmentType.values().length)],
                            productDetails);
                    break;
            }
            salesEvents.add(new SalesEvent(transactionType, transaction));
        }
        return salesEvents;
    }
}
