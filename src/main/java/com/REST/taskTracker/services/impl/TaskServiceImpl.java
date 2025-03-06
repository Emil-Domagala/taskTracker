package com.REST.taskTracker.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.REST.taskTracker.domain.entities.Task;
import com.REST.taskTracker.domain.entities.TaskList;
import com.REST.taskTracker.domain.entities.TaskPriority;
import com.REST.taskTracker.domain.entities.TaskStatus;
import com.REST.taskTracker.repositories.TaskListRepository;
import com.REST.taskTracker.repositories.TaskRepository;
import com.REST.taskTracker.services.TaskService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Override
    public List<Task> listTasks(UUID tasklistId) {
        return taskRepository.findByTaskListId(tasklistId);
    }

    @Override
    public Task createTask(UUID taskLisId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskList taskList = taskListRepository.findById(taskLisId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID provided"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now);

        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task must have an id!");
        }
        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task IDs do not match!");
        }
        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have valid priority");
        }
        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task must have valid status");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }

}
