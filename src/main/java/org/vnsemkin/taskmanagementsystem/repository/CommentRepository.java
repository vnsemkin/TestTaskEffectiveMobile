package org.vnsemkin.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vnsemkin.taskmanagementsystem.entity.Comment;
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
