package com.flipkart.jira.service.impl;

import com.flipkart.jira.enums.TicketType;
import com.flipkart.jira.exception.InvalidTicketOperationException;
import com.flipkart.jira.exception.TicketNotFoundException;
import com.flipkart.jira.model.Sprint;
import com.flipkart.jira.model.Story;
import com.flipkart.jira.model.Ticket;
import com.flipkart.jira.repository.SprintRepository;
import com.flipkart.jira.repository.TicketRepository;
import com.flipkart.jira.service.SprintService;

import java.util.List;
import java.util.stream.Collectors;

public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final TicketRepository ticketRepository;

    public SprintServiceImpl(SprintRepository sprintRepository, TicketRepository ticketRepository) {
        this.sprintRepository = sprintRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void addStoryToSprint(String storyId) {
        Ticket ticket = ticketRepository.findById(storyId)
                .orElseThrow(() -> new TicketNotFoundException(storyId));

        if (!(ticket instanceof Story)) {
            throw new InvalidTicketOperationException("Ticket " + storyId + " is not a Story and cannot be added to a sprint.");
        }

        if (sprintRepository.getSprint().getStoryIds().contains(storyId)) {
            throw new InvalidTicketOperationException("Story " + storyId + " is already in the current sprint.");
        }

        sprintRepository.addStoryToSprint(storyId);
    }

    @Override
    public void removeStoryFromSprint(String storyId) {
        // Check if the story is actually in the sprint before attempting removal
        if (!sprintRepository.getSprint().getStoryIds().contains(storyId)) {
            // Or silently succeed, depends on desired behavior. Throwing an error is usually better.
            throw new InvalidTicketOperationException("Story " + storyId + " is not in the current sprint, cannot remove.");
        }
        sprintRepository.removeStoryFromSprint(storyId);
    }

    @Override
    public List<String> getSprintStoryIds() {
        return sprintRepository.getStoriesInSprint();
    }

    @Override
    public Sprint getSprintDetails() {
        return sprintRepository.getSprint();
    }

    @Override
    public List<Ticket> getStoriesInSprintDetailed() {
        return sprintRepository.getStoriesInSprint().stream()
                .map(storyId -> ticketRepository.findById(storyId)
                        .orElseThrow(() -> new IllegalStateException("Inconsistent data: Story " + storyId + " in sprint but not in repository.")))
                .collect(Collectors.toList());
    }
}