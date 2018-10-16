package com.amanda.sales_processor.report;

import java.math.BigDecimal;

public interface IReport {

    void printReport();

    static String formatCurrency(BigDecimal currency) {
        return currency.toString() + "P";
    }
}
