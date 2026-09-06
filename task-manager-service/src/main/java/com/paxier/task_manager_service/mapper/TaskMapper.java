package com.paxier.task_manager_service.mapper;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.model.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  Task toApiModel(TaskEntity entity);

  @Mapping(target = "id", ignore = true)
  TaskEntity toEntity(Task task);
}
