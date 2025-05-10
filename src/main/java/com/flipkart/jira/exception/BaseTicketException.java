package com.flipkart.jira.exception;

public class BaseTicketException extends RuntimeException {
    public BaseTicketException(String message) {
        super(message);
    }
}