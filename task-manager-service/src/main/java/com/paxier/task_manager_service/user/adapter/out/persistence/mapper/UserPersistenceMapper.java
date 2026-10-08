package com.paxier.task_manager_service.user.adapter.out.persistence.mapper;

import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.user.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

  User toApiModel(UserEntity entity);
}
