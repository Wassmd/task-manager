package com.paxier.task_manager_service.task.adapter.out.persistence;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.task.adapter.out.persistence.mapper.TaskPersistenceMapper;
import com.paxier.task_manager_service.task.adapter.out.persistence.repository.TaskJpaRepository;
import com.paxier.task_manager_service.task.application.port.out.TaskRepositoryPort;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskRepositoryPort {

  private final TaskJpaRepository taskJpaRepository;
  private final TaskPersistenceMapper mapper;

  @Override
  public List<Task> findAllWithWatchers() {
    return taskJpaRepository.findAllWithWatchers()
        .stream()
        .map(mapper::toApiModel)
        .toList();
  }

  @Override
  public Task save(Task task) {
    return mapper.toApiModel(taskJpaRepository.save(mapper.toEntity(task)));
  }

  @Override
  public boolean existsById(UUID id) {
    return taskJpaRepository.existsById(id);
  }
}
