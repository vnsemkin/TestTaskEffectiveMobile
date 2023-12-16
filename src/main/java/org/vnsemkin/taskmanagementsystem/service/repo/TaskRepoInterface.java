package org.vnsemkin.taskmanagementsystem.service.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.entity.Task;
import org.vnsemkin.taskmanagementsystem.entity.User;

public interface TaskRepoInterface {
    TaskDto createTask(Task task);
    TaskDto getTaskDtoById(Long id);
    Task findTaskById(Long id);
    Page<TaskDto> findAllTask(Pageable pageable);
    TaskDto updateTask(Task task);
    void deleteTaskById(Long id);
    void deleteTask(Task task);
    int updateTaskAssigneeByTaskId(Long id, User assignee);
    Page<Task> findAllByAuthor(User author, Pageable pageable);
    Page<Task> findAllByAssignee(User assignee, Pageable pageable);
}
