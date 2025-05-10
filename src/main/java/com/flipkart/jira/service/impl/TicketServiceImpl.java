package com.flipkart.jira.service.impl;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;
import com.flipkart.jira.exception.InvalidStatusTransitionException;
import com.flipkart.jira.exception.StoryCannotBeClosedException;
import com.flipkart.jira.exception.TicketNotFoundException;
import com.flipkart.jira.model.*;
import com.flipkart.jira.repository.SubTaskRepository;
import com.flipkart.jira.repository.TicketRepository;
import com.flipkart.jira.service.TicketService;

public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final SubTaskRepository subTaskRepository; // Needed for Story closure check

    public TicketServiceImpl(TicketRepository ticketRepository, SubTaskRepository subTaskRepository) {
        this.ticketRepository = ticketRepository;
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    public Ticket createTicket(TicketType type, String title, String description) {
        Ticket ticket;
        switch (type) {
            case STORY:
                ticket = new Story(title, description);
                break;
            case EPIC:
                ticket = new Epic(title, description);
                break;
            case ON_CALL:
                ticket = new OnCall(title, description);
                break;
            default:
                throw new IllegalArgumentException("Invalid ticket type: " + type);
        }
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicketStatus(String ticketId, TicketStatus newStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        if (ticket instanceof Story && ticket.isTerminalStatus(newStatus)) {
            TicketStatus storyResolvedStatus = ticket.getResolvedStatus(); // e.g., DEPLOYED
            for (String subTaskId : ticket.getSubTaskIds()) {
                SubTask subTask = subTaskRepository.findById(subTaskId)
                        .orElseThrow(() -> new IllegalStateException("Inconsistent data: SubTask " + subTaskId + " not found for Story " + ticketId));
                if (subTask.getStatus() != storyResolvedStatus) {
                    throw new StoryCannotBeClosedException(ticketId + ". Subtask " + subTask.getId() + " is not in " + storyResolvedStatus + " state.");
                }
            }
        }

        if (!ticket.isValidTransition(newStatus)) {
            throw new InvalidStatusTransitionException(ticketId, ticket.getStatus(), newStatus);
        }

        ticket.setStatus(newStatus);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(String ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
    }

    @Override
    public Ticket addCommentToTicket(String ticketId, String comment) {
        Ticket ticket = getTicketById(ticketId);
        ticket.addComment(comment);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket setDescriptionForTicket(String ticketId, String description) {
        Ticket ticket = getTicketById(ticketId);
        ticket.setDescription(description);
        return ticketRepository.save(ticket);
    }
}