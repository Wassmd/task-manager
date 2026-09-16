package com.paxier.task_manager_service.controller;

import com.paxier.task_manager_service.api.UsersApi;
import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.service.UserService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

  private final UserService userService;

  @Override
  public ResponseEntity<List<User>> apiV1UsersGet() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @Override
  public ResponseEntity<User> apiV1UsersPost(User user) {
    User saved = userService.createUser(user);
    return ResponseEntity.created(URI.create("/api/v1/users/" + saved.getId())).body(saved);
  }

  @Override
  public ResponseEntity<User> apiV1UsersUserIdGet(UUID userId) {
    return ResponseEntity.ok(userService.getUser(userId));
  }

  @Override
  public ResponseEntity<Void> apiV1UsersUserIdDelete(UUID userId) {
    userService.deactivateUser(userId);
    return ResponseEntity.noContent().build();
  }
}
