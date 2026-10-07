package com.paxier.task_manager_service.adapter.out.persistence;

import com.paxier.task_manager_service.adapter.out.persistence.entity.TaskWatcherEntity;
import com.paxier.task_manager_service.adapter.out.persistence.repository.TaskJpaRepository;
import com.paxier.task_manager_service.adapter.out.persistence.repository.TaskWatcherJpaRepository;
import com.paxier.task_manager_service.application.port.out.TaskWatcherRepositoryPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskWatcherPersistenceAdapter implements TaskWatcherRepositoryPort {

  private final TaskWatcherJpaRepository taskWatcherJpaRepository;
  private final TaskJpaRepository taskJpaRepository;

  @Override
  public void add(UUID taskId, String email) {
    TaskWatcherEntity taskWatcherEntity = TaskWatcherEntity.builder()
        .email(email)
        .task(taskJpaRepository.getReferenceById(taskId))
        .build();

    taskWatcherJpaRepository.save(taskWatcherEntity);
  }
}
