package com.amanda.sales_processor.report;

import com.amanda.sales_processor.persistence.ItemDetails;
import com.amanda.sales_processor.persistence.SalesProcessorData;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SalesReport implements IReport {

    @Override
    public void printReport() {
        Map items = SalesProcessorData.getInstance().getItemsMap();
        if(items.size() > 0) {
            IMessageHandler.printFormat(createReport(items));
        }
        else IMessageHandler.printFormat("No sales performed.\n");
    }

    public StringBuffer createReport(Map<String, List<ItemDetails>> items) {
        StringBuffer report = new StringBuffer();
        report.append("\nSales Report\n");
        Iterator<Map.Entry<String, List<ItemDetails>>> itemDetailsIt = items.entrySet().iterator();
        while (itemDetailsIt.hasNext()) {
            Map.Entry<String, List<ItemDetails>> item = itemDetailsIt.next();
            int salesQty = 0;
            BigDecimal amount = BigDecimal.ZERO;
            for (ItemDetails itemDetails : item.getValue()) {
                salesQty += itemDetails.getSalesQty();
                amount = amount.add(itemDetails.getProductPrice().multiply(new BigDecimal(itemDetails.getSalesQty())));
            }
            report.append(salesQty + " " + item.getKey() + " sold - Amount: " + IReport.formatCurrency(amount) + ".\n");
        }
        return report;
    }
}
