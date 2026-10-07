package com.paxier.task_manager_service.adapter.in.web;

import com.paxier.task_manager_service.api.TaskWatchersApi;
import com.paxier.task_manager_service.api.model.TaskWatcher;
import com.paxier.task_manager_service.application.port.in.TaskWatcherUseCase;
import com.paxier.task_manager_service.domain.TaskWatcherCommand;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskWatcherController implements TaskWatchersApi {

  private final TaskWatcherUseCase taskWatcherUseCase;

  @Override
  public ResponseEntity<TaskWatcher> apiV1TaskWatcherPost(TaskWatcher taskWatcher) {
    TaskWatcherCommand command = toCommand(taskWatcher);

    taskWatcherUseCase.addTaskWatcher(command);
    return ResponseEntity
        .created(URI.create("/api/v1/task-watcher/" + command.getTaskId() + "/" + command.getEmail()))
        .body(taskWatcher);
  }

  private TaskWatcherCommand toCommand(TaskWatcher taskWatcher) {
    return TaskWatcherCommand.builder()
        .email(taskWatcher.getEmailId())
        .taskId(taskWatcher.getTaskId())
        .build();
  }
}
