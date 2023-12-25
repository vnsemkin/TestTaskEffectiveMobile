package org.vnsemkin.taskmanagementsystem.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING("в ожидании"),
    IN_PROGRESS("в процессе"),
    COMPLETED("завершено");

    private final String description;
    TaskStatus(String description) {
        this.description = description;
    }
}
