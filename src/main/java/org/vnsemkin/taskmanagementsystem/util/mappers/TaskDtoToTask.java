package org.vnsemkin.taskmanagementsystem.util.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.entity.Task;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class TaskDtoToTask {
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    public  Task toTask(TaskDto taskDto) {
        if (Objects.nonNull(taskDto)) {
            return Task.builder()
                    .id(taskDto.getId())
                    .taskName(taskDto.getTaskName())
                    .description(taskDto.getDescription())
                    .status(taskDto.getStatus())
                    .priority(taskDto.getPriority())
                    .author(userMapper.UserDtoToUser(taskDto.getAuthor()))
                    .assignee(userMapper.UserDtoToUser(taskDto.getAssignee()))
                    .comments(commentMapper.toCommentList(taskDto.getComments()))
                    .createdAt(taskDto.getCreatedAt())
                    .updatedAt(taskDto.getUpdatedAt())
                    .build();
        }else {
            return new Task();
        }
    }

    public  List<Task> toTaskDtoList(List<TaskDto> tasks) {
        return tasks.stream().map(this::toTask).toList();
    }
}
