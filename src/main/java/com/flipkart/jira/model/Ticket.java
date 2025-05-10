package com.flipkart.jira.model;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class Ticket {
    private final String id;
    private String title;
    private TicketStatus status;
    private final TicketType type;
    private List<String> subTaskIds;
    private String description;
    private List<String> comments;

    public Ticket(String title, TicketType type, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.type = type;
        this.status = TicketStatus.OPEN;
        this.subTaskIds = new ArrayList<>();
        this.description = description;
        this.comments = new ArrayList<>();
    }


    public abstract boolean isValidTransition(TicketStatus newStatus);
    public abstract TicketStatus getResolvedStatus();
    public abstract boolean isTerminalStatus(TicketStatus status);



    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketType getType() {
        return type;
    }

    public List<String> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTaskId(String subTaskId) {
        this.subTaskIds.add(subTaskId);
    }

    public void removeSubTaskId(String subTaskId) {
        this.subTaskIds.remove(subTaskId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "Ticket {" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", subTaskIds=" + subTaskIds.size() +
                ", comments=" + comments.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public abstract boolean isValidTransition(TicketStatus currentStatus, TicketStatus newStatus);
}