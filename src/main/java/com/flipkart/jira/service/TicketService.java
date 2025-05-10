package com.flipkart.jira.service;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;
import com.flipkart.jira.model.Ticket;

public interface TicketService {

    Ticket createTicket(TicketType type, String title, String description);

    Ticket updateTicketStatus(String ticketId, TicketStatus newStatus);

    Ticket getTicketById(String ticketId);

    Ticket addCommentToTicket(String ticketId, String comment);

    Ticket setDescriptionForTicket(String ticketId, String description);
}