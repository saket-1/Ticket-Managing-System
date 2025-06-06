package com.flipkart.jira.model;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Epic extends Ticket {

    private static final Map<TicketStatus, Set<TicketStatus>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        ALLOWED_TRANSITIONS.put(TicketStatus.OPEN, Set.of(TicketStatus.IN_PROGRESS));
        ALLOWED_TRANSITIONS.put(TicketStatus.IN_PROGRESS, Set.of(TicketStatus.OPEN, TicketStatus.COMPLETED));
        ALLOWED_TRANSITIONS.put(TicketStatus.COMPLETED, Collections.emptySet()); // Terminal state
    }

    public Epic(String title, String description) {
        super(title, TicketType.EPIC, description);
    }

    @Override
    public boolean isValidTransition(TicketStatus currentStatus, TicketStatus newStatus) { // CORRECTED SIGNATURE
        return ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Collections.emptySet()).contains(newStatus);
    }

    @Override
    public boolean isValidTransition(TicketStatus newStatus) {
        return false;
    }

    @Override
    public TicketStatus getResolvedStatus() {
        return TicketStatus.COMPLETED;
    }

    @Override
    public boolean isTerminalStatus(TicketStatus status) {
        return status == TicketStatus.COMPLETED;
    }
}