package com.zylex.newsagregator.exception;

public class NewsAggregatorException extends RuntimeException {

    public NewsAggregatorException(String message) {
        super(message);
//        ConsoleLogger.writeErrorMessage(message);
    }

    public NewsAggregatorException(String message, Throwable cause) {
        super(message, cause);
//        ConsoleLogger.writeErrorMessage(message, cause);
    }
}
