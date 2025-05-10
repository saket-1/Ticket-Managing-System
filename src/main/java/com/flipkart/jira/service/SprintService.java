package com.flipkart.jira.service;

import com.flipkart.jira.model.Sprint;
import com.flipkart.jira.model.Ticket; // For returning detailed story info

import java.util.List;

public interface SprintService {


    void addStoryToSprint(String storyId);

    void removeStoryFromSprint(String storyId);

    List<String> getSprintStoryIds();

    Sprint getSprintDetails();

    List<Ticket> getStoriesInSprintDetailed();
}