package com.paxier.task_manager_service.task.application.port.out;

import com.paxier.task_manager_service.api.model.Task;
import java.util.List;
import java.util.UUID;

public interface TaskRepositoryPort {

  List<Task> findAllWithWatchers();

  Task save(Task task);

  boolean existsById(UUID id);
}
