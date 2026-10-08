package com.paxier.task_manager_service.user.application.port.out;

import com.paxier.task_manager_service.api.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

  List<User> findAll();

  Optional<User> findById(UUID id);

  User save(User user);

  void deactivate(UUID id);
}
