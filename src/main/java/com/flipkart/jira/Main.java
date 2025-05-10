package com.flipkart.jira;

import com.flipkart.jira.enums.TicketStatus;
import com.flipkart.jira.enums.TicketType;
import com.flipkart.jira.exception.BaseTicketException;
import com.flipkart.jira.model.SubTask;
import com.flipkart.jira.model.Ticket;
import com.flipkart.jira.repository.SprintRepository;
import com.flipkart.jira.repository.SubTaskRepository;
import com.flipkart.jira.repository.TicketRepository;
import com.flipkart.jira.service.SprintService;
import com.flipkart.jira.service.SubTaskService;
import com.flipkart.jira.service.TicketService;
import com.flipkart.jira.service.impl.SprintServiceImpl;
import com.flipkart.jira.service.impl.SubTaskServiceImpl;
import com.flipkart.jira.service.impl.TicketServiceImpl;

public class Main {

    public static void main(String[] args) {
        TicketRepository ticketRepo = new TicketRepository();
        SubTaskRepository subTaskRepo = new SubTaskRepository();
        SprintRepository sprintRepo = new SprintRepository();

        TicketService ticketService = new TicketServiceImpl(ticketRepo, subTaskRepo);
        SubTaskService subTaskService = new SubTaskServiceImpl(subTaskRepo, ticketRepo);
        SprintService sprintService = new SprintServiceImpl(sprintRepo, ticketRepo);

            System.out.println("\n1. Creating Story...");
            Ticket loginStory = ticketService.createTicket(TicketType.STORY, "Implement Login", "Core login functionality");
            System.out.println("   Created: " + loginStory.getId() + " - " + loginStory.getTitle());

            System.out.println("\n2. Creating Sub-task...");
            SubTask uiSubTask = subTaskService.createSubTask(loginStory.getId(), "Design Login UI");
            System.out.println("   Created Sub-task: " + uiSubTask.getId() + " for Story " + loginStory.getId());

            System.out.println("\n3. Updating Story status...");
            ticketService.updateTicketStatus(loginStory.getId(), TicketStatus.IN_PROGRESS);
            System.out.println("   Story " + loginStory.getId() + " status: " + ticketService.getTicketById(loginStory.getId()).getStatus());

            System.out.println("\n4. Adding Story to Sprint...");
            sprintService.addStoryToSprint(loginStory.getId());
            System.out.println("   Story " + loginStory.getId() + " added to sprint.");
            System.out.print("   Sprint contents: ");
            sprintService.getStoriesInSprintDetailed().forEach(s -> System.out.print(s.getId() + " "));
            System.out.println();

            System.out.println("\n5. Attempting to close Story with open sub-task (EXPECTING ERROR)...");
            try {
                ticketService.updateTicketStatus(loginStory.getId(), TicketStatus.DEPLOYED);
                System.out.println("   ERROR: Story deployed unexpectedly!");
            } catch (BaseTicketException e) {
                System.out.println("   SUCCESS: Caught expected error - " + e.getMessage());
            }

            System.out.println("\n6. Creating an Epic (another ticket type)...");
            Ticket authEpic = ticketService.createTicket(TicketType.EPIC, "User Authentication", "Auth Epic");
            System.out.println("   Created: " + authEpic.getId() + " - " + authEpic.getTitle() + " [" + authEpic.getStatus() + "]");


        }
}