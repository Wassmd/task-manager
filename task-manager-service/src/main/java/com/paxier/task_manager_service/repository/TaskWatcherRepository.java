package com.paxier.task_manager_service.repository;

import com.paxier.task_manager_service.model.TaskWatcherEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskWatcherRepository extends JpaRepository<TaskWatcherEntity, UUID> {

}