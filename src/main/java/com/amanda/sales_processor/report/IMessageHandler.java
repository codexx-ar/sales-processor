package com.amanda.sales_processor.report;

public interface IMessageHandler {

    static void printFormat(StringBuffer message) {
        printFormat(message.toString());
    }

    static void printFormat(String message) {
        System.out.println(message);
    }
}
