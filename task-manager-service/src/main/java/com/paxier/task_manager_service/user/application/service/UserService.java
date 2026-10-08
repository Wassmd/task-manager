package com.paxier.task_manager_service.user.application.service;

import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.user.application.port.in.UserUseCase;
import com.paxier.task_manager_service.user.application.port.out.UserRepositoryPort;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

  private final UserRepositoryPort userRepository;

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUser(UUID id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public void deactivateUser(UUID id) {
    userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    userRepository.deactivate(id);
  }
}
