package com.paxier.task_manager_service.service;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.api.model.TaskStatus;
import com.paxier.task_manager_service.model.TaskEntity;
import com.paxier.task_manager_service.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  public List<Task> getTasks(){
    Task task1 = new Task(UUID.randomUUID(), "My first task", TaskStatus.OPEN);
    task1.description("My first task description");
    Task task2 = new Task(UUID.randomUUID(), "My second task", TaskStatus.OPEN);
    task1.description("My second task description");

    return List.of(task1, task2);
  }

  public Task createTask(Task task) {
    TaskEntity taskEntity = TaskEntity.builder()
        .title(task.getTitle())
        .status(task.getStatus())
        .description(task.getDescription())
        .dueDate(task.getDueDate())
        .build();

    TaskEntity savedEntity = taskRepository.save(taskEntity);
    Task savedTask = new Task(savedEntity.getId(), savedEntity.getTitle(), savedEntity.getStatus());
    savedTask.setDescription(savedEntity.getDescription());
    savedTask.setDueDate(savedEntity.getDueDate());

    return savedTask;
  }
}
