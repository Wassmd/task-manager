package com.paxier.task_manager_service.application.service;

import com.paxier.task_manager_service.application.port.in.TaskWatcherUseCase;
import com.paxier.task_manager_service.application.port.out.TaskRepositoryPort;
import com.paxier.task_manager_service.application.port.out.TaskWatcherRepositoryPort;
import com.paxier.task_manager_service.domain.TaskWatcherCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskWatcherService implements TaskWatcherUseCase {

  private final TaskWatcherRepositoryPort taskWatcherRepository;
  private final TaskRepositoryPort taskRepository;

  @Override
  public void addTaskWatcher(TaskWatcherCommand command) {
    if (!taskRepository.existsById(command.getTaskId())) {
      throw new IllegalArgumentException("Task not found with id: " + command.getTaskId());
    }

    taskWatcherRepository.add(command.getTaskId(), command.getEmail());
  }
}
