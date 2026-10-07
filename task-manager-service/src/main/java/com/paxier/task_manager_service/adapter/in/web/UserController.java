package com.paxier.task_manager_service.adapter.in.web;

import com.paxier.task_manager_service.api.UsersApi;
import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.application.port.in.UserUseCase;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

  private final UserUseCase userUseCase;

  @Override
  public ResponseEntity<List<User>> apiV1UsersGet() {
    return ResponseEntity.ok(userUseCase.getUsers());
  }

  @Override
  public ResponseEntity<User> apiV1UsersPost(User user) {
    User saved = userUseCase.createUser(user);
    return ResponseEntity.created(URI.create("/api/v1/users/" + saved.getId())).body(saved);
  }

  @Override
  public ResponseEntity<User> apiV1UsersUserIdGet(UUID userId) {
    return ResponseEntity.ok(userUseCase.getUser(userId));
  }

  @Override
  public ResponseEntity<Void> apiV1UsersUserIdDelete(UUID userId) {
    userUseCase.deactivateUser(userId);
    return ResponseEntity.noContent().build();
  }
}
