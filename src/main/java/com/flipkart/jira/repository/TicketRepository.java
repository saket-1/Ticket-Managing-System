package com.flipkart.jira.repository;

import com.flipkart.jira.model.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepository {

    private final Map<String, Ticket> tickets = new ConcurrentHashMap<>();

    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public Optional<Ticket> findById(String ticketId) {
        return Optional.ofNullable(tickets.get(ticketId));
    }


    public Map<String, Ticket> findAll() {
        return new HashMap<>(tickets); // Return a copy
    }

    public void deleteById(String ticketId) {
        tickets.remove(ticketId);
    }
}