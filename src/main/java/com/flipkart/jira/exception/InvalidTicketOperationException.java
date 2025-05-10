package com.flipkart.jira.exception;

public class InvalidTicketOperationException extends BaseTicketException {
    public InvalidTicketOperationException(String message) {
        super(message);
    }
}