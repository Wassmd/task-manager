package com.paxier.task_manager_service.user.application.port.in;

import com.paxier.task_manager_service.api.model.User;
import java.util.List;
import java.util.UUID;

public interface UserUseCase {

  List<User> getUsers();

  User getUser(UUID id);

  User createUser(User user);

  void deactivateUser(UUID id);
}
