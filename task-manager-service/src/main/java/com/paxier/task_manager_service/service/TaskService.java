package com.paxier.task_manager_service.service;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.mapper.TaskMapper;
import com.paxier.task_manager_service.model.TaskEntity;
import com.paxier.task_manager_service.model.UserEntity;
import com.paxier.task_manager_service.repository.TaskRepository;
import com.paxier.task_manager_service.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final TaskMapper taskMapper;

  public List<Task> getTasks(){
    return taskRepository.findAllWithWatchers()
        .stream()
        .map(taskMapper::toApiModel)
        .toList();
  }

  public Task createTask(Task task) {
    TaskEntity taskEntity = taskMapper.toEntity(task);

    if (task.getUserId() != null) {
      UserEntity user = userRepository.findById(task.getUserId())
          .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + task.getUserId()));
      taskEntity.setUser(user);
    }

    TaskEntity savedEntity = taskRepository.save(taskEntity);
    return taskMapper.toApiModel(savedEntity);
  }
}
