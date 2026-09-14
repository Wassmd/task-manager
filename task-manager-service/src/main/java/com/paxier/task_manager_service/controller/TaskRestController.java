package com.paxier.task_manager_service.controller;

import com.paxier.task_manager_service.api.TasksApi;
import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.model.TaskDetails;
import com.paxier.task_manager_service.service.TaskService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskRestController implements TasksApi {

  private final TaskService taskService;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public ResponseEntity<List<Task>> apiV1TasksGet() {
    return ResponseEntity.ok(taskService.getTasks());
  }

  @Override
  public ResponseEntity<Task> apiV1TasksPost(Task task) {
    Task savedTask = taskService.createTask(task);

    eventPublisher.publishEvent(getTaskDetails(savedTask));

    return ResponseEntity.created(URI.create("/api/v1/tasks/" + savedTask.getId())).body(savedTask);
  }

  public TaskDetails getTaskDetails(Task savedTask) {
    return new TaskDetails(
        savedTask.getTitle(),
        savedTask.getDescription(),
        savedTask.getStatus(),
        savedTask.getDueDate()
    );
  }
}
