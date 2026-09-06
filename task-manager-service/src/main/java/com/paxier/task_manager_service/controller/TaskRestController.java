package com.paxier.task_manager_service.controller;

import com.paxier.task_manager_service.api.TasksApi;
import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskRestController implements TasksApi {

  private final TaskService taskService;

  @Override
  public ResponseEntity<List<Task>> apiV1TasksGet() {
    return ResponseEntity.ok(taskService.getTasks());
  }

  @Override
  public ResponseEntity<Task> apiV1TasksPost(Task task) {
    return null;
  }
}
