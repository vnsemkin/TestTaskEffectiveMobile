package org.vnsemkin.taskmanagementsystem.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class
TaskRequest{
    private Integer page;
    private Integer size;
    private String author;
    private String assignee;
}

