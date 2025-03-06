package com.REST.taskTracker.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.REST.taskTracker.domain.entities.Task;

public interface TaskService {
    List<Task> listTasks(UUID tasklistId);

    Task createTask(UUID taskListId, Task task);

    Optional<Task> getTask(UUID taskListId, UUID taskId);

    Task updateTask(UUID taskListId, UUID taskId, Task task);

    void deleteTask(UUID taskListId, UUID taskId);
}
