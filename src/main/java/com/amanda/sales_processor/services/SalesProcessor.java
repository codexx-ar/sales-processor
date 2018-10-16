package com.amanda.sales_processor.services;

import com.amanda.sales_processor.model.AdjustmentTransaction;
import com.amanda.sales_processor.model.SalesEvent;
import com.amanda.sales_processor.model.SalesTransaction;
import com.amanda.sales_processor.persistence.SalesProcessorData;
import com.amanda.sales_processor.report.AdjustmentReport;
import com.amanda.sales_processor.report.IMessageHandler;
import com.amanda.sales_processor.report.SalesReport;

import java.util.List;

public class SalesProcessor implements TransactionHandler {

    final String INVALID_TRANS_MESSAGE = "Invalid Transaction.";

    public void processSales(List<SalesEvent> salesEvents) {
        SalesProcessorData.getInstance().addSalesEvents(salesEvents);
        int counter = 1;
        IMessageHandler.printFormat("Start processing sales");
        while(SalesProcessorData.getInstance().getSalesEvents().peek() != null) {
            SalesEvent event = SalesProcessorData.getInstance().getSalesEvents().remove();
            switch(event.getTransactionType()) {
                case SALE:
                    if(!(event.getTransactionDetails() instanceof SalesTransaction))
                        IMessageHandler.printFormat(INVALID_TRANS_MESSAGE);
                    else
                        handleSalesEvent((SalesTransaction) event.getTransactionDetails());
                    break;
                case ADJUSTMENT:
                    if(!(event.getTransactionDetails() instanceof AdjustmentTransaction))
                        IMessageHandler.printFormat(INVALID_TRANS_MESSAGE);
                    else
                        handleAdjustmentEvent((AdjustmentTransaction) event.getTransactionDetails());
                    break;
                    default:

            }
            if(counter % 10 == 0) // print sales report for every 10 trans
                new SalesReport().printReport();
            if(counter % 50 == 0) {
                // print adjustment report for every 50 trans
                IMessageHandler.printFormat("Paused processing sales...");
                new AdjustmentReport().printReport();
                IMessageHandler.printFormat("Start processing sales");
            }
            counter++;
        }
        IMessageHandler.printFormat("All processing complete");
        new SalesReport().printReport();
        new AdjustmentReport().printReport();
    }
}
