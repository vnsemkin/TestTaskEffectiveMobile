package org.vnsemkin.taskmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.vnsemkin.taskmanagementsystem.model.TaskPriority;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String taskName;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserDto author;
    private UserDto assignee;
    private List<CommentDto> comments;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
}
