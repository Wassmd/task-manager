package com.paxier.task_manager_service.repository;

import com.paxier.task_manager_service.model.TaskEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

  @Query("SELECT DISTINCT t FROM TaskEntity t LEFT JOIN FETCH t.watchers")
  List<TaskEntity> findAllWithWatchers();
}

