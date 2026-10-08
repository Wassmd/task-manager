package com.paxier.task_manager_service.task.application.port.out;

import java.util.UUID;

public interface TaskWatcherRepositoryPort {

  void add(UUID taskId, String email);
}
