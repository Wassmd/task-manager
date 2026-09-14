package com.paxier.task_manager_service.service;

import com.paxier.task_manager_service.model.TaskEntity;
import com.paxier.task_manager_service.model.TaskWatcherDTO;
import com.paxier.task_manager_service.model.TaskWatcherEntity;
import com.paxier.task_manager_service.repository.TaskRepository;
import com.paxier.task_manager_service.repository.TaskWatcherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskWatcherService {
  private final TaskWatcherRepository taskWatcherRepository;
  private final TaskRepository taskRepository;
  public void addTaskWatcher(TaskWatcherDTO taskWatcherDTO) {

    TaskEntity taskEntity = taskRepository.findById(taskWatcherDTO.getTaskId())
        .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskWatcherDTO.getTaskId()));

    // Convert DTO to Entity
    TaskWatcherEntity taskWatcherEntity = TaskWatcherEntity.builder()
        .email(taskWatcherDTO.getEmail())
        .task(taskEntity)
        .build();

    // Save to database
    taskWatcherRepository.save(taskWatcherEntity);
  }
}
