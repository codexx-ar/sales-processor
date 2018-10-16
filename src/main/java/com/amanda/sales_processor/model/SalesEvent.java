package com.amanda.sales_processor.model;

public class SalesEvent {

    TransactionType transactionType;
    ITransaction transactionDetails;

    public SalesEvent(TransactionType transactionType, ITransaction transactionDetails) {
        this.transactionType = transactionType;
        this.transactionDetails = transactionDetails;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public ITransaction getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(ITransaction transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}
