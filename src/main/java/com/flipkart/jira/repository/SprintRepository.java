package com.flipkart.jira.repository;

import com.flipkart.jira.model.Sprint;

import java.util.ArrayList;
import java.util.List;

public class SprintRepository {

    private final Sprint currentSprint;

    public SprintRepository() {
        this.currentSprint = new Sprint();
    }

    public Sprint getSprint() {
        return currentSprint;
    }

    public void addStoryToSprint(String storyId) {

        currentSprint.addStoryId(storyId);
    }

    public void removeStoryFromSprint(String storyId) {
        currentSprint.removeStoryId(storyId);
    }

    public List<String> getStoriesInSprint() {
        return new ArrayList<>(currentSprint.getStoryIds());
    }
}