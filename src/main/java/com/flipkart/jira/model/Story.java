package com.flipkart.jira.model;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Story extends Ticket {

    private static final Map<TicketStatus, Set<TicketStatus>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        ALLOWED_TRANSITIONS.put(TicketStatus.OPEN, Set.of(TicketStatus.IN_PROGRESS));
        ALLOWED_TRANSITIONS.put(TicketStatus.IN_PROGRESS, Set.of(TicketStatus.OPEN, TicketStatus.TESTING));
        ALLOWED_TRANSITIONS.put(TicketStatus.TESTING, Set.of(TicketStatus.IN_PROGRESS, TicketStatus.IN_REVIEW));
        ALLOWED_TRANSITIONS.put(TicketStatus.IN_REVIEW, Set.of(TicketStatus.TESTING, TicketStatus.DEPLOYED));
        ALLOWED_TRANSITIONS.put(TicketStatus.DEPLOYED, Collections.emptySet()); // Terminal state
    }

    public Story(String title, String description) {
        super(title, TicketType.STORY, description);
    }

    @Override
    public boolean isValidTransition(TicketStatus newStatus) {
        return ALLOWED_TRANSITIONS.getOrDefault(this.getStatus(), Collections.emptySet()).contains(newStatus);
    }

    @Override
    public TicketStatus getResolvedStatus() {
        return TicketStatus.DEPLOYED;
    }

    @Override
    public boolean isTerminalStatus(TicketStatus status) {
        return status == TicketStatus.DEPLOYED;
    }

    @Override
    public boolean isValidTransition(TicketStatus currentStatus, TicketStatus newStatus) {
        return ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Collections.emptySet()).contains(newStatus);
    }
}