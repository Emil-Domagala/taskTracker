package com.REST.taskTracker.mappers;

import com.REST.taskTracker.domain.dto.TaskDto;
import com.REST.taskTracker.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);

}
