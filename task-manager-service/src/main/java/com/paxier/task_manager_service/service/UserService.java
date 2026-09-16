package com.paxier.task_manager_service.service;

import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.mapper.TaskMapper;
import com.paxier.task_manager_service.model.UserEntity;
import com.paxier.task_manager_service.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final TaskMapper taskMapper;

  public List<User> getUsers() {
    return userRepository.findAll().stream().map(taskMapper::toApiModel).toList();
  }

  public User getUser(UUID id) {
    return taskMapper.toApiModel(userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id)));
  }

  public User createUser(User user) {
    UserEntity entity = UserEntity.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .active(true)
        .build();
    return taskMapper.toApiModel(userRepository.save(entity));
  }

  public void deactivateUser(UUID id) {
    UserEntity entity = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    entity.setActive(false);
    userRepository.save(entity);
  }
}
