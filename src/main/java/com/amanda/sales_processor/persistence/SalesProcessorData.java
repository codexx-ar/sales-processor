package com.amanda.sales_processor.persistence;

import com.amanda.sales_processor.model.AdjustmentTransaction;
import com.amanda.sales_processor.model.SalesEvent;

import java.util.*;

/*
 * This class can be enhanced later for other persistent systems.
 */
public class SalesProcessorData {

    private static SalesProcessorData instance;

    public static SalesProcessorData getInstance() {
        if(instance == null) {
            instance = new SalesProcessorData();
        }
        return instance;
    }
    private SalesProcessorData(){}

    private Queue<SalesEvent> salesEvents = new LinkedList<>();

    private Map<String, List<ItemDetails>> itemsMap = new HashMap<>();

    private List<AdjustmentTransaction> adjustmentHistory = new ArrayList<>();

    public Queue<SalesEvent> getSalesEvents() {
        return salesEvents;
    }

    public void addSalesEvents(List<SalesEvent> salesEvents) {
        this.salesEvents.addAll(salesEvents);
    }

    public Map<String, List<ItemDetails>> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(Map<String, List<ItemDetails>> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public List<AdjustmentTransaction> getAdjustmentHistory() {
        return adjustmentHistory;
    }

    public void setAdjustmentHistory(List<AdjustmentTransaction> adjustmentHistory) {
        this.adjustmentHistory = adjustmentHistory;
    }

}
