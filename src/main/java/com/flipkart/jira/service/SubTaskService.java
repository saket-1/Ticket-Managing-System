package com.flipkart.jira.service;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.model.SubTask;

public interface SubTaskService {

    SubTask createSubTask(String parentTicketId, String title);

    SubTask updateSubTaskStatus(String subTaskId, TicketStatus newStatus);

    void deleteSubTask(String parentTicketId, String subTaskId);

    SubTask getSubTaskById(String subTaskId);
}