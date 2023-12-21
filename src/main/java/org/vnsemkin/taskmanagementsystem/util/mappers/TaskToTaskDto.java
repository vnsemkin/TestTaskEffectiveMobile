package org.vnsemkin.taskmanagementsystem.util.mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.entity.Task;

import java.util.List;
import java.util.Objects;

@Component
public class TaskToTaskDto {
    public TaskDto toTaskDto(Task task) {
        if(Objects.nonNull(task)) {
            return TaskDto.builder()
                    .id(task.getId())
                    .taskName(task.getTaskName())
                    .description(task.getDescription())
                    .status(task.getStatus())
                    .priority(task.getPriority())
                    .author(UserMapper.UserToUserDto(task.getAuthor()))
                    .assignee(UserMapper.UserToUserDto(task.getAssignee()))
                    .comments(CommentMapper.toCommentDtoList(task.getComments()))
                    .createdAt(task.getCreatedAt())
                    .updatedAt(task.getUpdatedAt())
                    .build();
        }else {
            return new TaskDto();
        }
    }

    public  Page<TaskDto> toTaskDtoPage(Page<Task> tasks) {
        List<TaskDto> taskDtoList = tasks.stream().map(this::toTaskDto).toList();
        return new PageImpl<>(taskDtoList, tasks.getPageable(), tasks.getTotalElements());
    }

    public  List<TaskDto> toTaskDtoList(List<Task> tasks) {
        return tasks.stream().map(this::toTaskDto).toList();
    }
}
