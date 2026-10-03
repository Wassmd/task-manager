package com.paxier.task_manager_service.controller;

import com.paxier.task_manager_service.api.TaskWatchersApi;
import com.paxier.task_manager_service.api.model.TaskWatcher;
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
@RequiredArgsConstructor
public class TaskWatcherController implements TaskWatchersApi {
  private final TaskWatcherService taskWatcherService;

  @Override
  public ResponseEntity<TaskWatcher> apiV1TaskWatcherPost(TaskWatcher taskWatcher) {
    TaskWatcherDTO taskWatcherDTO = convertToDTO(taskWatcher);

    taskWatcherService.addTaskWatcher(taskWatcherDTO);
    return ResponseEntity.
        created(URI.create("/api/v1/task-watcher/" + taskWatcherDTO.getTaskId() + "/" + taskWatcherDTO.getEmail()))
            .body(taskWatcher);
  }

  private TaskWatcherDTO convertToDTO(TaskWatcher taskWatcher) {
    return TaskWatcherDTO.builder()
        .email(taskWatcher.getEmailId())
        .taskId(taskWatcher.getTaskId())
        .build();
  }
}

