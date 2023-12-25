package org.vnsemkin.taskmanagementsystem.model;

import lombok.Getter;

@Getter
public enum TaskPriority {
    HIGH("высокий"),
    MEDIUM("средний"),
    LOW("низкий");

    private final String description;
    TaskPriority(String description) {
        this.description = description;
    }
}
