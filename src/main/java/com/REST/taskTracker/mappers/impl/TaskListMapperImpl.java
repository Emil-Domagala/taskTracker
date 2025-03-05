package com.REST.taskTracker.mappers.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.REST.taskTracker.domain.dto.TaskListDto;
import com.REST.taskTracker.domain.entities.Task;
import com.REST.taskTracker.domain.entities.TaskList;
import com.REST.taskTracker.domain.entities.TaskStatus;
import com.REST.taskTracker.mappers.TaskListMapper;
import com.REST.taskTracker.mappers.TaskMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                null,
                null,
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList())
                        .orElse(null));
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks()).map(List::size).orElse(0),
                calculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream().map(taskMapper::toDto).toList())
                        .orElse(null));
    }

    private Double calculateTaskListProgress(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }
        long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSED == task.getStatus()).count();
        return (double) closedTaskCount / tasks.size();
    }

}
