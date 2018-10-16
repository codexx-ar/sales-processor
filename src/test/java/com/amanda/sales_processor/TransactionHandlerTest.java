package com.amanda.sales_processor;

import com.amanda.sales_processor.model.*;
import com.amanda.sales_processor.persistence.SalesProcessorData;
import com.amanda.sales_processor.services.SalesProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionHandlerTest {

    SalesProcessor processor = new SalesProcessor();

    @Test
    @DisplayName("should handle normal sales transactions.")
    void testHandleSalesEvent() {
        SalesProcessorData.getInstance().setItemsMap(new HashMap<>());
        BigDecimal productPrice1 = new BigDecimal(25);
        ITransaction transaction = new SalesTransaction(13,
                new ProductDetails("mango", productPrice1));
        List<SalesEvent> salesEvents = new ArrayList<SalesEvent>();
        salesEvents.add(new SalesEvent(TransactionType.SALE, transaction));
        processor.processSales(salesEvents);
        Assertions.assertNotNull(SalesProcessorData.getInstance().getItemsMap());
        Assertions.assertTrue(SalesProcessorData.getInstance().getItemsMap().containsKey("mango"));
        Assertions.assertEquals(1, SalesProcessorData.getInstance().getItemsMap().size());
        Assertions.assertEquals(0, SalesProcessorData.getInstance().getSalesEvents().size());
    }

    @Test
    @DisplayName("should handle add adjustment transactions.")
    void testHandleAddAdjustmentEvent() {
        SalesProcessorData.getInstance().setItemsMap(new HashMap<>());
        SalesProcessorData.getInstance().setAdjustmentHistory(new ArrayList<>());
        // add item of price 25
        BigDecimal productPrice1 = new BigDecimal(25);
        ITransaction transaction = new SalesTransaction(13,
                new ProductDetails("mango", productPrice1));
        List<SalesEvent> salesEvents = new ArrayList<SalesEvent>();
        salesEvents.add(new SalesEvent(TransactionType.SALE, transaction));
        // add 2 to the item 25+2 = 27
        productPrice1 = new BigDecimal(2);
        transaction = new AdjustmentTransaction(AdjustmentType.ADD,
                new ProductDetails("mango", productPrice1));
        salesEvents.add(new SalesEvent(TransactionType.ADJUSTMENT, transaction));
        processor.processSales(salesEvents);
        Assertions.assertNotNull(SalesProcessorData.getInstance().getItemsMap());
        Assertions.assertTrue(SalesProcessorData.getInstance().getItemsMap().containsKey("mango"));
        Assertions.assertEquals(1, SalesProcessorData.getInstance().getItemsMap().values().size());
        Assertions.assertEquals(new BigDecimal(27), SalesProcessorData.getInstance().getItemsMap().get("mango").get(0).getProductPrice());
        Assertions.assertEquals(13, SalesProcessorData.getInstance().getItemsMap().get("mango").get(0).getSalesQty());
    }

    @Test
    @DisplayName("should handle add adjustment transactions.")
    void testHandleSubtractAdjustmentEvent() {
        SalesProcessorData.getInstance().setItemsMap(new HashMap<>());
        SalesProcessorData.getInstance().setAdjustmentHistory(new ArrayList<>());
        // add item of price 25
        BigDecimal productPrice1 = new BigDecimal(25);
        ITransaction transaction = new SalesTransaction(13,
                new ProductDetails("mango", productPrice1));
        List<SalesEvent> salesEvents = new ArrayList<SalesEvent>();
        salesEvents.add(new SalesEvent(TransactionType.SALE, transaction));
        // subtract 28, 27-28 = 0 (rounded to 0)
        productPrice1 = new BigDecimal(28);
        transaction = new AdjustmentTransaction(AdjustmentType.SUBTRACT,
                new ProductDetails("mango", productPrice1));
        salesEvents.add(new SalesEvent(TransactionType.ADJUSTMENT, transaction));
        processor.processSales(salesEvents);
        Assertions.assertEquals(new BigDecimal(0), SalesProcessorData.getInstance().getItemsMap().get("mango").get(0).getProductPrice());
    }

    @Test
    @DisplayName("should handle add adjustment transactions.")
    void testHandleMultiplyAdjustmentEvent() {
        SalesProcessorData.getInstance().setItemsMap(new HashMap<>());
        SalesProcessorData.getInstance().setAdjustmentHistory(new ArrayList<>());
        // add item of price 25
        BigDecimal productPrice1 = new BigDecimal(2);
        ITransaction transaction = new SalesTransaction(13,
                new ProductDetails("mango", productPrice1));
        List<SalesEvent> salesEvents = new ArrayList<SalesEvent>();
        salesEvents.add(new SalesEvent(TransactionType.SALE, transaction));
        // multiply 4, 2*4 = 8
        productPrice1 = new BigDecimal(4);
        transaction = new AdjustmentTransaction(AdjustmentType.MULTIPLY,
                new ProductDetails("mango", productPrice1));
        salesEvents.add(new SalesEvent(TransactionType.ADJUSTMENT, transaction));
        processor.processSales(salesEvents);
        Assertions.assertEquals(new BigDecimal(8), SalesProcessorData.getInstance().getItemsMap().get("mango").get(0).getProductPrice());
    }

    @Test
    @DisplayName("should not apply adjustment when item is not found.")
    void testAdjustmentWithNoSales() {
        SalesProcessorData.getInstance().setItemsMap(new HashMap<>());
        SalesProcessorData.getInstance().setAdjustmentHistory(new ArrayList<>());
        // multiply 4, 2*4 = 8
        BigDecimal productPrice1 = new BigDecimal(4);
        AdjustmentTransaction transaction = new AdjustmentTransaction(AdjustmentType.MULTIPLY,
                new ProductDetails("mango", productPrice1));
        processor.handleAdjustmentEvent(transaction);
        Assertions.assertEquals(0, SalesProcessorData.getInstance().getItemsMap().size());
        Assertions.assertEquals(0, SalesProcessorData.getInstance().getAdjustmentHistory().size());
    }
}
