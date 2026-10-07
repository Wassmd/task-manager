package com.paxier.task_manager_service.application.port.out;

import java.util.UUID;

public interface TaskWatcherRepositoryPort {

  void add(UUID taskId, String email);
}
