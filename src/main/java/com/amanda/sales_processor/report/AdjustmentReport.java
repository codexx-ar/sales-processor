package com.amanda.sales_processor.report;

import com.amanda.sales_processor.model.AdjustmentTransaction;
import com.amanda.sales_processor.persistence.SalesProcessorData;

import java.util.List;

public class AdjustmentReport implements IReport {

    @Override
    public void printReport() {
        List<AdjustmentTransaction> adjustmentHistory = SalesProcessorData.getInstance().getAdjustmentHistory();
        if(adjustmentHistory.size() > 0) {
            IMessageHandler.printFormat(createReport(adjustmentHistory));
        }
        else IMessageHandler.printFormat("No adjustments reported...\n");
    }

    public StringBuffer createReport(List<AdjustmentTransaction> adjustmentHistory) {
        StringBuffer report = new StringBuffer();
        report.append("\nAdjustment Report\n");

        for (AdjustmentTransaction adjustment : adjustmentHistory) {
            switch (adjustment.getAdjustmentType()) {
                case ADD:
                    report.append("Added " + IReport.formatCurrency(adjustment.getProductDetails().getProductPrice()) +
                            " to " + adjustment.getProductDetails().getProductName());
                    break;
                case SUBTRACT:
                    report.append("Subtracted " + IReport.formatCurrency(adjustment.getProductDetails().getProductPrice()) +
                            " from " + adjustment.getProductDetails().getProductName());
                    break;
                case MULTIPLY:
                    report.append("Multiplied " + IReport.formatCurrency(adjustment.getProductDetails().getProductPrice()) +
                            " to " + adjustment.getProductDetails().getProductName());
                    break;
            }
            report.append("\n");
        }
        return report;
    }
}
