package com.flipkart.jira.exception;

public class StoryCannotBeClosedException extends BaseTicketException {
    public StoryCannotBeClosedException(String storyId) {
        super("Story '" + storyId + "' cannot be closed/deployed as one or more sub-tasks are not yet completed/deployed.");
    }
}