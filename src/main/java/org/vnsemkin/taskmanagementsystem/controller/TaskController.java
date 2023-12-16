package org.vnsemkin.taskmanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vnsemkin.taskmanagementsystem.configuration.constants.TMSConstants;
import org.vnsemkin.taskmanagementsystem.dto.CommentDto;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.dto.TaskDtoUpdate;
import org.vnsemkin.taskmanagementsystem.dto.UserDto;
import org.vnsemkin.taskmanagementsystem.model.TaskRequest;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;
import org.vnsemkin.taskmanagementsystem.service.TaskService;

import static org.vnsemkin.taskmanagementsystem.configuration.constants.TMSConstants.TASK_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(TASK_URL)
public class TaskController {
    private final TaskService taskService;

    @GetMapping(produces = TMSConstants.APP_JSON)
    public ResponseEntity<Page<TaskDto>> getTasks(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(value = 0, message = "Size must be greater than or equal to 0")
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "author", required = false, defaultValue = "author") String author,
            @RequestParam(value = "assignee", required = false) String assignee) {
            TaskRequest taskRequest = TaskRequest.builder()
                    .page(page)
                    .size(size)
                    .author(author)
                    .assignee(assignee)
                    .build();
            return taskService.getTask(taskRequest);
    }

    @PostMapping(produces = TMSConstants.APP_JSON)
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping(value = "/{id}", produces = TMSConstants.APP_JSON)
    public ResponseEntity<TaskDto> getTaskById(
            @PathVariable Long id) throws JsonProcessingException {
        return taskService.getTaskById(id);
    }

    @PutMapping(value = "/{id}", produces = TMSConstants.APP_JSON)
    public ResponseEntity<String> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDtoUpdate taskDtoUpdate) {
        return taskService.updateTask(id, taskDtoUpdate);
    }

    @DeleteMapping(value = "/{id}",
            produces = TMSConstants.APP_JSON)
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @PutMapping(value = "/{id}/status", produces = TMSConstants.APP_JSON)
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long id, @RequestParam TaskStatus status) {
        return taskService.updateTaskStatus(id, status);
    }

    @PutMapping(value = "/{id}/assignee", produces = TMSConstants.APP_JSON)
    public ResponseEntity<String> updateTaskAssignee(
            @PathVariable Long id, @RequestParam UserDto assignee) {
        return taskService.updateTaskAssignee(id, assignee);
    }

    @PostMapping(value = "/{id}/comment", produces = TMSConstants.APP_JSON)
    public ResponseEntity<String> setComment(
            @PathVariable Long id, @RequestBody CommentDto commentDto) {
        return taskService.setTaskComments(id, commentDto);
    }
}
