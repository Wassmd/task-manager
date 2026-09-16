package com.paxier.task_manager_service.mapper;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.api.model.TaskWatcher;
import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.model.TaskEntity;
import com.paxier.task_manager_service.model.TaskWatcherEntity;
import com.paxier.task_manager_service.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  Task toApiModel(TaskEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  TaskEntity toEntity(Task task);

  User toApiModel(UserEntity entity);

  @Mapping(target = "emailId", source = "email")
  @Mapping(target = "taskId", source = "task.id")
  TaskWatcher toApiModel(TaskWatcherEntity entity);
}
