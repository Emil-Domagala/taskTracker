package com.REST.taskTracker.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.REST.taskTracker.domain.dto.TaskDto;
import com.REST.taskTracker.domain.entities.Task;
import com.REST.taskTracker.mappers.TaskMapper;
import com.REST.taskTracker.services.TaskService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("task-list/{task_list_id}/tasks")
public class TaskControlles {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId)
                .stream().map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task createedTask = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(createedTask);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_lid") UUID taskId) {
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_lid") UUID taskId,
            @RequestBody TaskDto taskDto) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_lid") UUID taskId) {
        taskService.deleteTask(taskListId, taskId);

    }

}
