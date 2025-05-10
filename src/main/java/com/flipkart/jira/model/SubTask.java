package com.flipkart.jira.model;

import com.flipkart.jira.enums.TicketStatus;

import java.util.Objects;
import java.util.UUID;

public class SubTask {
    private final String id;
    private String title;
    private TicketStatus status;
    private final String parentTicketId;

    public SubTask(String title, String parentTicketId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.status = TicketStatus.OPEN;
        this.parentTicketId = parentTicketId;
    }

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

    public String getParentTicketId() {
        return parentTicketId;
    }

    @Override
    public String toString() {
        return "SubTask {" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", parentTicketId='" + parentTicketId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(id, subTask.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}