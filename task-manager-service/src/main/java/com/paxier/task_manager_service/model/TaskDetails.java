package com.paxier.task_manager_service.model;


import com.paxier.task_manager_service.api.model.TaskStatus;
import java.time.LocalDate;

public record TaskDetails(String Title, String description, TaskStatus status, LocalDate dueDate) {
}
