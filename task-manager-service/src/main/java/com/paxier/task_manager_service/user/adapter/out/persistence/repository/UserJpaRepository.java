package com.paxier.task_manager_service.user.adapter.out.persistence.repository;

import com.paxier.task_manager_service.user.adapter.out.persistence.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}
