package org.vnsemkin.taskmanagementsystem.service.repo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.vnsemkin.taskmanagementsystem.dto.TaskDto;
import org.vnsemkin.taskmanagementsystem.entity.Task;
import org.vnsemkin.taskmanagementsystem.entity.User;
import org.vnsemkin.taskmanagementsystem.repository.TaskRepository;
import org.vnsemkin.taskmanagementsystem.util.mappers.TaskToTaskDto;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TaskRepoInterfaceImpl implements TaskRepoInterface {
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskDto createTask(Task task) {
        return TaskToTaskDto.toTaskDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskDto getTaskDtoById(Long id) {
        Optional<Task> byId = taskRepository.findById(id);
        if(byId.isPresent()){
            return TaskToTaskDto.toTaskDto(byId.get());
        }
        return new TaskDto();
    }

    @Override

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseGet(Task::new);
    }

    @Override
    @Transactional
    public Page<TaskDto> findAllTask(Pageable pageable) {
        return TaskToTaskDto.toTaskDtoPage(taskRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public TaskDto updateTask(Task task) {
        return TaskToTaskDto.toTaskDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public int updateTaskAssigneeByTaskId(Long id, User assignee) {
        return taskRepository.updateTaskAssigneeByTaskId(id, assignee);
    }

    @Override
    @Transactional
    public Page<Task> findAllByAuthor(User author, Pageable pageable) {
        return taskRepository.findAllByAuthor(author, pageable);
    }

    @Override
    @Transactional
    public Page<Task> findAllByAssignee(User assignee, Pageable pageable) {
       return taskRepository.findAllByAssignee(assignee, pageable);
    }
}
