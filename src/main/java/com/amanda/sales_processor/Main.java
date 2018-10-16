package com.amanda.sales_processor;

import com.amanda.sales_processor.services.SalesGenerator;
import com.amanda.sales_processor.services.SalesProcessor;

public class Main {

    public static void main(String[] args) {
        SalesGenerator salesGenerator = new SalesGenerator();
        SalesProcessor salesProcessor = new SalesProcessor();
        salesProcessor.processSales(salesGenerator.generateSales());
    }
}
