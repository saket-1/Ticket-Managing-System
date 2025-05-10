package com.flipkart.jira.exception;

public class TicketNotFoundException extends BaseTicketException {
    public TicketNotFoundException(String ticketId) {
        super("Ticket with ID '" + ticketId + "' not found.");
    }
}