package com.paxier.task_manager_service.adapter.in.web;

import com.paxier.task_manager_service.api.TasksApi;
import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.application.port.in.TaskUseCase;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskRestController implements TasksApi {

  private final TaskUseCase taskUseCase;

  @Override
  public ResponseEntity<List<Task>> apiV1TasksGet() {
    return ResponseEntity.ok(taskUseCase.getTasks());
  }

  @Override
  public ResponseEntity<Task> apiV1TasksPost(Task task) {
    Task savedTask = taskUseCase.createTask(task);

    return ResponseEntity.created(URI.create("/api/v1/tasks/" + savedTask.getId())).body(savedTask);
  }
}
