package com.paxier.task_manager_service.task.adapter.out.persistence.mapper;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.api.model.TaskWatcher;
import com.paxier.task_manager_service.task.adapter.out.persistence.entity.TaskEntity;
import com.paxier.task_manager_service.task.adapter.out.persistence.entity.TaskWatcherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskPersistenceMapper {

  @Mapping(target = "user", ignore = true)
  Task toApiModel(TaskEntity entity);

  @Mapping(target = "id", ignore = true)
  TaskEntity toEntity(Task task);

  @Mapping(target = "emailId", source = "email")
  @Mapping(target = "taskId", source = "task.id")
  TaskWatcher toApiModel(TaskWatcherEntity entity);
}
