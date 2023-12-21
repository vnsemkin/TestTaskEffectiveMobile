package org.vnsemkin.taskmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vnsemkin.taskmanagementsystem.dto.CommentDto;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.dto.TaskDtoUpdate;
import org.vnsemkin.taskmanagementsystem.dto.UserDto;
import org.vnsemkin.taskmanagementsystem.entity.Comment;
import org.vnsemkin.taskmanagementsystem.entity.Task;
import org.vnsemkin.taskmanagementsystem.entity.User;
import org.vnsemkin.taskmanagementsystem.exception.AppTaskNotFoundException;
import org.vnsemkin.taskmanagementsystem.exception.AppUserNotFoundException;
import org.vnsemkin.taskmanagementsystem.model.TaskPriority;
import org.vnsemkin.taskmanagementsystem.model.TaskRequest;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;
import org.vnsemkin.taskmanagementsystem.repository.TaskRepository;
import org.vnsemkin.taskmanagementsystem.repository.UserRepository;
import org.vnsemkin.taskmanagementsystem.util.mappers.CommentMapper;
import org.vnsemkin.taskmanagementsystem.util.mappers.TaskDtoToTask;
import org.vnsemkin.taskmanagementsystem.util.mappers.TaskToTaskDto;
import org.vnsemkin.taskmanagementsystem.util.mappers.UserMapper;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskDtoToTask taskDtoToTask;
    private final TaskToTaskDto taskToTaskDto;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;


    public ResponseEntity<Page<TaskDto>> getTask(TaskRequest taskRequest) {
        Pageable page =
                PageRequest.of(taskRequest.getPage(), taskRequest.getSize());
        if (taskRequest.getAuthor().equals("author") &&
                Objects.isNull(taskRequest.getAssignee())) {
            return ResponseEntity.ok().body(taskToTaskDto
                    .toTaskDtoPage(taskRepository.findAll(page)));
        }

        if (Objects.isNull(taskRequest.getAssignee())) {
            return ResponseEntity.ok()
                    .body(taskToTaskDto.toTaskDtoPage(taskRepository
                            .findAllByAuthor(getUser(taskRequest.getAuthor()), page)));
        } else {
            return ResponseEntity.ok()
                    .body(taskToTaskDto.toTaskDtoPage(taskRepository
                            .findAllByAssignee(getUser(taskRequest.getAssignee()), page)));
        }
    }


    public ResponseEntity<TaskDto> getTaskById(Long id) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isPresent()) {
            return ResponseEntity.ok().body(taskToTaskDto
                    .toTaskDto(taskById.get()));
        } else {
            throw new AppTaskNotFoundException("Task with id: " + id + " not found.");
        }
    }

    public ResponseEntity<String> createTask(TaskDto taskDto) {
        Task task = taskDtoToTask.toTask(taskDto);
        String authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser.equals(task.getAuthor().getUsername())) {
            TaskDto newTaskDto = taskToTaskDto
                    .toTaskDto(taskRepository.save(task));
            if (Objects.nonNull(newTaskDto)) {
                return ResponseEntity.ok().body("Task created");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User is not the author of " + task.getTaskName());
    }

    public ResponseEntity<String> updateTask(Long id, TaskDtoUpdate taskDtoUpdate) {
        int count;
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isEmpty()) {
            throw new AppTaskNotFoundException("Task with id: " + id + " not found.");
        }
        if (getAuthenticatedUser().equals(taskById
                .get().getAuthor().getUsername())) {
            count = updateTaskWithTaskDtoUpdate(taskById.get(), taskDtoUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User is not the author of " + taskById.get()
                            .getAuthor());
        }
        if (count == 1) {
            return ResponseEntity.ok().body("Task updated");
        } else {
            return ResponseEntity.status(HttpStatus
                            .INTERNAL_SERVER_ERROR)
                    .body("Task not updated");
        }
    }

    public ResponseEntity<String> deleteTask(Long id) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isEmpty()) {
            throw new AppTaskNotFoundException("Task with id: " + id + " not found.");
        }
        if (getAuthenticatedUser().equals(taskById.get().getAuthor().getUsername())) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok().body("Task with id: " + id + " was deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User is not the author of " + taskById.get()
                            .getTaskName());
        }
    }

    public ResponseEntity<String> updateTaskStatus(Long id, TaskStatus status) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isEmpty()) {
            throw new AppTaskNotFoundException("Task with id: " + id + " not found.");
        }
        String authenticatedUser = getAuthenticatedUser();
        String author = taskById.get()
                .getAuthor().getUsername();
        String assignee = taskById.get()
                .getAssignee().getUsername();

        if (authenticatedUser.equals(author) || authenticatedUser.equals(assignee)) {
            Task task = taskById.get();
            task.setStatus(status);
            return taskRepository.updateTaskStatus(id, status) > 0 ?
                    ResponseEntity.ok().body("Task with id " + id + " updated") :
                    ResponseEntity.ok().body("Task with id " + id + "is not updated");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User is not the author or assignee of " + taskById.get()
                            .getTaskName());
        }
    }

    public ResponseEntity<String> updateTaskAssignee(Long id, UserDto assignee) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isEmpty()) {
            throw new AppTaskNotFoundException("Task with id: " + id + " not found.");
        }
        if (!getAuthenticatedUser()
                .equals(taskById.get().getAuthor().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User is not the author of " + taskById.get().getTaskName());
        } else {
            int i = taskRepository.updateTaskAssigneeByTaskId(id,
                    getUser(assignee.getUsername()));
            return i > 0 ?
                    ResponseEntity.ok().body("Task with id " + id + " updated") :
                    ResponseEntity.ok().body("Task with id " + id + "is not updated");
        }
    }

    public ResponseEntity<String> setTaskComments(Long id, CommentDto commentDto) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isEmpty()) {
            throw new AppTaskNotFoundException("Task with id: " + id + " not found.");
        }
        Task task = taskById.get();
        // Check if author exist in db
        getUser(commentDto.getAuthor().getUsername());
        Comment comment = commentMapper.toComment(commentDto);
        task.addComment(comment);
        taskRepository.save(task);
        return ResponseEntity.ok().body("Comment  was added");
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new AppUserNotFoundException("Author "
                        + username
                        + " not found")
        );
    }

    private String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                return (String) principal;
            } else {
                return principal.toString();
            }
        }
        return "";
    }

    private int updateTaskWithTaskDtoUpdate(Task task, TaskDtoUpdate taskDtoUpdate) {
        String description = Objects.nonNull(taskDtoUpdate.getDescription())
                ? taskDtoUpdate.getDescription()
                : task.getDescription();
        String taskName = Objects.nonNull(taskDtoUpdate.getTaskName())
                ? taskDtoUpdate.getTaskName()
                : task.getTaskName();
        TaskStatus status = Objects.nonNull(taskDtoUpdate.getStatus())
                ? taskDtoUpdate.getStatus()
                : task.getStatus();
        TaskPriority priority = Objects.nonNull(taskDtoUpdate.getPriority())
                ? taskDtoUpdate.getPriority()
                : task.getPriority();
        User assignee = Objects.nonNull(taskDtoUpdate.getAssignee())
                ? userMapper.UserDtoToUser(taskDtoUpdate.getAssignee())
                : task.getAssignee();

        return taskRepository.updateTaskByTaskDtoUpdate(
                task.getId(), description, taskName, status, priority, assignee);
    }
}