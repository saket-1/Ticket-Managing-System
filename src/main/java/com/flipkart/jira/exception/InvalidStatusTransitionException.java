package com.flipkart.jira.exception;

import com.flipkart.jira.enums.TicketStatus;

public class InvalidStatusTransitionException extends BaseTicketException {
    public InvalidStatusTransitionException(String ticketId, TicketStatus fromStatus, TicketStatus toStatus) {
        super("Invalid status transition for ticket '" + ticketId + "' from " + fromStatus + " to " + toStatus);
    }

    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}