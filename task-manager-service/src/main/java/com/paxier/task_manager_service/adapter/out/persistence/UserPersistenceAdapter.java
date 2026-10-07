package com.paxier.task_manager_service.adapter.out.persistence;

import com.paxier.task_manager_service.adapter.out.persistence.entity.UserEntity;
import com.paxier.task_manager_service.adapter.out.persistence.mapper.PersistenceMapper;
import com.paxier.task_manager_service.adapter.out.persistence.repository.UserJpaRepository;
import com.paxier.task_manager_service.api.model.User;
import com.paxier.task_manager_service.application.port.out.UserRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

  private final UserJpaRepository userJpaRepository;
  private final PersistenceMapper mapper;

  @Override
  public List<User> findAll() {
    return userJpaRepository.findAll().stream().map(mapper::toApiModel).toList();
  }

  @Override
  public Optional<User> findById(UUID id) {
    return userJpaRepository.findById(id).map(mapper::toApiModel);
  }

  @Override
  public User save(User user) {
    UserEntity entity = UserEntity.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .active(true)
        .build();
    return mapper.toApiModel(userJpaRepository.save(entity));
  }

  @Override
  public void deactivate(UUID id) {
    userJpaRepository.findById(id).ifPresent(entity -> {
      entity.setActive(false);
      userJpaRepository.save(entity);
    });
  }
}
