package org.vnsemkin.taskmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.vnsemkin.taskmanagementsystem.model.TaskPriority;
import org.vnsemkin.taskmanagementsystem.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "task_name")
    private String taskName;
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @NotNull
    @ManyToOne
    private User author;
    @ManyToOne
    private User assignee;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }
}
