package com.REST.taskTracker.mappers;

import com.REST.taskTracker.domain.dto.TaskListDto;
import com.REST.taskTracker.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);

}
