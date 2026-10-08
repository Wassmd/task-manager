package com.paxier.task_manager_service.task.application.port.in;

import com.paxier.task_manager_service.task.domain.TaskWatcherCommand;

public interface TaskWatcherUseCase {

  void addTaskWatcher(TaskWatcherCommand command);
}
