package com.flipkart.jira.service.impl;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;
import com.flipkart.jira.exception.InvalidStatusTransitionException;
import com.flipkart.jira.exception.SubTaskNotFoundException;
import com.flipkart.jira.exception.TicketNotFoundException;
import com.flipkart.jira.model.*;
import com.flipkart.jira.repository.SubTaskRepository;
import com.flipkart.jira.repository.TicketRepository;
import com.flipkart.jira.service.SubTaskService;

public class SubTaskServiceImpl implements SubTaskService {

    private final SubTaskRepository subTaskRepository;
    private final TicketRepository ticketRepository;

    public SubTaskServiceImpl(SubTaskRepository subTaskRepository, TicketRepository ticketRepository) {
        this.subTaskRepository = subTaskRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public SubTask createSubTask(String parentTicketId, String title) {
        Ticket parentTicket = ticketRepository.findById(parentTicketId)
                .orElseThrow(() -> new TicketNotFoundException(parentTicketId));

        SubTask subTask = new SubTask(title, parentTicketId);
        subTaskRepository.save(subTask);

        parentTicket.addSubTaskId(subTask.getId());
        ticketRepository.save(parentTicket);

        return subTask;
    }

    @Override
    public SubTask updateSubTaskStatus(String subTaskId, TicketStatus newStatus) {
        SubTask subTask = subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new SubTaskNotFoundException(subTaskId));

        Ticket parentTicket = ticketRepository.findById(subTask.getParentTicketId())
                .orElseThrow(() -> new TicketNotFoundException(subTask.getParentTicketId() + " (parent of subtask " + subTaskId + ")"));

        // Sub-tasks follow the parent's status flow.
        if (!parentTicket.isValidTransition(subTask.getStatus(), newStatus)) { // Uses parent's logic with subtask's current status
            throw new InvalidStatusTransitionException("Invalid status transition for sub-task '" + subTaskId +
                    "' from " + subTask.getStatus() + " to " + newStatus +
                    " (based on parent " + parentTicket.getType() + " flow)");
        }

        subTask.setStatus(newStatus);
        return subTaskRepository.save(subTask);
    }

    private Ticket createMockTicketForValidation(TicketType parentType, TicketStatus currentSubTaskStatus) {
        Ticket mockTicket;
        switch (parentType) {
            case STORY: mockTicket = new Story("mock", "mock"); break;
            case EPIC: mockTicket = new Epic("mock", "mock"); break;
            case ON_CALL: mockTicket = new OnCall("mock", "mock"); break;
            default: throw new IllegalArgumentException("Unknown parent type");
        }
        mockTicket.setStatus(currentSubTaskStatus); // Set its status to the subtask's current status
        return mockTicket;
    }


    @Override
    public void deleteSubTask(String parentTicketId, String subTaskId) {
        Ticket parentTicket = ticketRepository.findById(parentTicketId)
                .orElseThrow(() -> new TicketNotFoundException(parentTicketId));

        if (subTaskRepository.findById(subTaskId).isEmpty()){
            throw new SubTaskNotFoundException(subTaskId);
        }

        parentTicket.removeSubTaskId(subTaskId);
        ticketRepository.save(parentTicket);

        subTaskRepository.deleteById(subTaskId);
    }

    @Override
    public SubTask getSubTaskById(String subTaskId) {
        return subTaskRepository.findById(subTaskId)
                .orElseThrow(() -> new SubTaskNotFoundException(subTaskId));
    }
}