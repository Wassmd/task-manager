package com.paxier.task_manager_service.service;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.api.model.TaskStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  public List<Task> getTasks(){
    Task task1 = new Task(UUID.randomUUID(), "My first task", TaskStatus.OPEN);
    task1.description("My first task description");
    Task task2 = new Task(UUID.randomUUID(), "My second task", TaskStatus.OPEN);
    task1.description("My second task description");

    return List.of(task1, task2);
  }
}
