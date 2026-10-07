package com.paxier.task_manager_service.adapter.out.persistence;

import com.paxier.task_manager_service.adapter.out.persistence.entity.TaskEntity;
import com.paxier.task_manager_service.adapter.out.persistence.mapper.PersistenceMapper;
import com.paxier.task_manager_service.adapter.out.persistence.repository.TaskJpaRepository;
import com.paxier.task_manager_service.adapter.out.persistence.repository.UserJpaRepository;
import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.application.port.out.TaskRepositoryPort;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskRepositoryPort {

  private final TaskJpaRepository taskJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final PersistenceMapper mapper;

  @Override
  public List<Task> findAllWithWatchers() {
    return taskJpaRepository.findAllWithWatchers()
        .stream()
        .map(mapper::toApiModel)
        .toList();
  }

  @Override
  public Task save(Task task) {
    TaskEntity taskEntity = mapper.toEntity(task);

    if (task.getUserId() != null) {
      taskEntity.setUser(userJpaRepository.getReferenceById(task.getUserId()));
    }

    return mapper.toApiModel(taskJpaRepository.save(taskEntity));
  }

  @Override
  public boolean existsById(UUID id) {
    return taskJpaRepository.existsById(id);
  }
}
