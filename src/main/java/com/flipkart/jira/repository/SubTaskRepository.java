package com.flipkart.jira.repository;

import com.flipkart.jira.model.SubTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SubTaskRepository {
    private final Map<String, SubTask> subTasks = new ConcurrentHashMap<>();

    public SubTask save(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        return subTask;
    }

    public Optional<SubTask> findById(String subTaskId) {
        return Optional.ofNullable(subTasks.get(subTaskId));
    }

    public void deleteById(String subTaskId) {
        subTasks.remove(subTaskId);
    }


    public Map<String, SubTask> findAll() {
        return new HashMap<>(subTasks);
    }
}