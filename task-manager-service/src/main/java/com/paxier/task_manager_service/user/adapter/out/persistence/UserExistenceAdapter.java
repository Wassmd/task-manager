package com.paxier.task_manager_service.user.adapter.out.persistence;

import com.paxier.task_manager_service.task.application.port.out.UserExistencePort;
import com.paxier.task_manager_service.user.adapter.out.persistence.repository.UserJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Satisfies the {@code task} feature's {@link UserExistencePort}. This is the single, deliberate
 * place where the {@code user} feature depends on the {@code task} feature (user -> task), realising
 * the dependency inversion between the two features.
 */
@Component
@RequiredArgsConstructor
public class UserExistenceAdapter implements UserExistencePort {

  private final UserJpaRepository userJpaRepository;

  @Override
  public boolean existsById(UUID userId) {
    return userJpaRepository.existsById(userId);
  }
}
