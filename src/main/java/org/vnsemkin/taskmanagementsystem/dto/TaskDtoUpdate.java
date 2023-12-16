package org.vnsemkin.taskmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.vnsemkin.taskmanagementsystem.model.TaskPriority;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TaskDtoUpdate {
    @NotNull
    private String taskName;
    @NotNull
    private String description;
    @NotNull
    private TaskStatus status;
    @NotNull
    private TaskPriority priority;
    private UserDto assignee;
}
