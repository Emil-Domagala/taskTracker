package com.REST.taskTracker.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.REST.taskTracker.domain.entities.TaskPriority;
import com.REST.taskTracker.domain.entities.TaskStatus;


public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status,
        TaskPriority priority) {
}
