package com.paxier.task_manager_service.service;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.mapper.TaskMapper;
import com.paxier.task_manager_service.model.TaskEntity;
import com.paxier.task_manager_service.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  public List<Task> getTasks(){
    return taskRepository.findAllWithWatchers()
        .stream()
        .map(taskMapper::toApiModel)
        .toList();
  }

  public Task createTask(Task task) {
    TaskEntity taskEntity = taskMapper.toEntity(task);

    TaskEntity savedEntity = taskRepository.save(taskEntity);
    return taskMapper.toApiModel(savedEntity);
  }
}
