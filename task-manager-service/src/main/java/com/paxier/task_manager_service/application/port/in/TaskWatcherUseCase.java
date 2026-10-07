package com.paxier.task_manager_service.application.port.in;

import com.paxier.task_manager_service.domain.TaskWatcherCommand;

public interface TaskWatcherUseCase {

  void addTaskWatcher(TaskWatcherCommand command);
}
