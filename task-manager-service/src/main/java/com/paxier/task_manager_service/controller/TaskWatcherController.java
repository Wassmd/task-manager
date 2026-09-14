package com.paxier.task_manager_service.controller;

import com.paxier.task_manager_service.model.TaskWatcherDTO;
import com.paxier.task_manager_service.service.TaskService;
import com.paxier.task_manager_service.service.TaskWatcherService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task-watcher")
@RequiredArgsConstructor
public class TaskWatcherController {
  private final TaskWatcherService taskWatcherService;


  @PostMapping
  public ResponseEntity<String> addTaskWatcher(@Valid @RequestBody TaskWatcherRequest request) {
    TaskWatcherDTO taskWatcherDTO = convertToDTO(request);

    taskWatcherService.addTaskWatcher(convertToDTO(request));
    return ResponseEntity.created(URI.create("/api/v1/task-watcher/" + taskWatcherDTO.getTaskId() + "/" + taskWatcherDTO.getEmail())).body("Task watcher added successfully");
  }

  private TaskWatcherDTO convertToDTO(TaskWatcherRequest request) {
    return TaskWatcherDTO.builder()
        .email(request.getEmailId())
        .taskId(request.getTaskId())
        .build();
  }
}

