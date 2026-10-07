package com.paxier.task_manager_service.adapter.out.persistence.mapper;

import com.paxier.task_manager_service.adapter.out.persistence.entity.TaskEntity;
import com.paxier.task_manager_service.adapter.out.persistence.entity.TaskWatcherEntity;
import com.paxier.task_manager_service.adapter.out.persistence.entity.UserEntity;
import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.api.model.TaskWatcher;
import com.paxier.task_manager_service.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

  Task toApiModel(TaskEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  TaskEntity toEntity(Task task);

  User toApiModel(UserEntity entity);

  @Mapping(target = "emailId", source = "email")
  @Mapping(target = "taskId", source = "task.id")
  TaskWatcher toApiModel(TaskWatcherEntity entity);
}
