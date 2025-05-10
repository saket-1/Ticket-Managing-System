package com.flipkart.jira.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sprint {
    private final String id;
    private List<String> storyIds;


    public static final String CURRENT_SPRINT_ID = "CURRENT_SPRINT";

    public Sprint() {
        this.id = CURRENT_SPRINT_ID;
        this.storyIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getStoryIds() {
        return new ArrayList<>(storyIds);
    }

    public void addStoryId(String storyId) {
        if (!storyIds.contains(storyId)) {
            storyIds.add(storyId);
        }
    }

    public void removeStoryId(String storyId) {
        storyIds.remove(storyId);
    }

    @Override
    public String toString() {
        return "Sprint {" +
                "id='" + id + '\'' +
                ", storyIdsCount=" + storyIds.size() +
                '}';
    }
}