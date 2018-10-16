package com.amanda.sales_processor;

import com.amanda.sales_processor.model.AdjustmentTransaction;
import com.amanda.sales_processor.model.AdjustmentType;
import com.amanda.sales_processor.model.ProductDetails;
import com.amanda.sales_processor.persistence.ItemDetails;
import com.amanda.sales_processor.report.AdjustmentReport;
import com.amanda.sales_processor.report.SalesReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

public class ReportsTest {

    @Test
    @DisplayName("should print correct Sales report.")
    void testSalesReport() {
        SalesReport report = new SalesReport();
        Map<String, List<ItemDetails>> items = new HashMap<>();
        BigDecimal itemPrice1 = new BigDecimal(20);
        BigDecimal itemPrice2 = new BigDecimal(10);
        items.put("custard apple", Arrays.asList(
                new ItemDetails(1, itemPrice1),
                new ItemDetails(20, itemPrice2),
                new ItemDetails(5, itemPrice1),
                new ItemDetails(17, itemPrice1)));
        items.put("muskmelon", Arrays.asList(
                new ItemDetails(13, itemPrice2),
                new ItemDetails(2, itemPrice2),
                new ItemDetails(15, itemPrice2),
                new ItemDetails(7, itemPrice2)));
        String expectedSalesReport = "\nSales Report\n" +
                "43 custard apple sold - Amount: 660P.\n" +
                "37 muskmelon sold - Amount: 370P.\n";
        Assertions.assertEquals(expectedSalesReport, report.createReport(items).toString());
    }

    @Test
    @DisplayName("should print correct Adjustment report.")
    void testAdjustmentReport() {
        AdjustmentReport report = new AdjustmentReport();
        BigDecimal newItemPrice1 = new BigDecimal(30);
        BigDecimal newItemPrice2 = new BigDecimal(40);
        List<AdjustmentTransaction> adjustmentHistory = new ArrayList<>();
        adjustmentHistory.add(new AdjustmentTransaction(AdjustmentType.ADD,
                new ProductDetails("custard apple", newItemPrice2)));
        adjustmentHistory.add(new AdjustmentTransaction(AdjustmentType.MULTIPLY,
                new ProductDetails("muskmelon", newItemPrice1)));
        adjustmentHistory.add(new AdjustmentTransaction(AdjustmentType.SUBTRACT,
                new ProductDetails("muskmelon", newItemPrice1)));
        adjustmentHistory.add(new AdjustmentTransaction(AdjustmentType.SUBTRACT,
                new ProductDetails("custard apple", newItemPrice2)));
        adjustmentHistory.add(new AdjustmentTransaction(AdjustmentType.MULTIPLY,
                new ProductDetails("custard apple", newItemPrice1)));
        adjustmentHistory.add(new AdjustmentTransaction(AdjustmentType.SUBTRACT,
                new ProductDetails("muskmelon", newItemPrice2)));
        String expectedAdjustmentReport = "\nAdjustment Report\n" +
                "Added 40P to custard apple\n" +
                "Multiplied 30P to muskmelon\n" +
                "Subtracted 30P from muskmelon\n" +
                "Subtracted 40P from custard apple\n" +
                "Multiplied 30P to custard apple\n" +
                "Subtracted 40P from muskmelon\n";
        Assertions.assertEquals(expectedAdjustmentReport, report.createReport(adjustmentHistory).toString());
    }
}
