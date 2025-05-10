package com.flipkart.jira.exception;

public class SubTaskNotFoundException extends BaseTicketException {
    public SubTaskNotFoundException(String subTaskId) {
        super("SubTask with ID '" + subTaskId + "' not found.");
    }
}