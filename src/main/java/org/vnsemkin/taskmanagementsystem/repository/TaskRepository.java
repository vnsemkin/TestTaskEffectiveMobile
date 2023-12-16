package org.vnsemkin.taskmanagementsystem.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vnsemkin.taskmanagementsystem.entity.Task;
import org.vnsemkin.taskmanagementsystem.entity.User;
import org.vnsemkin.taskmanagementsystem.model.TaskPriority;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.assignee = :assignee WHERE t.id = :id")
    int updateTaskAssigneeByTaskId(@Param("id") Long id,
                                   @Param("assignee") User assignee);

    @Transactional
    @Query("SELECT t FROM Task t WHERE t.author = :author")
    Page<Task> findAllByAuthor(@Param("author") User author, Pageable pageable);

    @Transactional
    @Query("SELECT t FROM Task t WHERE t.assignee = :assignee")
    Page<Task> findAllByAssignee(@Param("assignee") User assignee, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Task t" +
            " SET " +
            "t.assignee = :assignee," +
            " t.description= :description," +
            " t.taskName= :taskName," +
            " t.status= :status," +
            " t.priority= :priority" +
            "  WHERE t.id = :id")
    int updateTaskByTaskDtoUpdate(
            @Param("id") Long id,
            @Param("description") String description,
            @Param("taskName") String taskName,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("assignee") User assignee);
}
