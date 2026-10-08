package com.paxier.task_manager_service.task.application.port.out;

import com.paxier.task_manager_service.task.domain.TaskDetails;

public interface TaskNotificationPort {

  void notifyTaskCreated(TaskDetails taskDetails);
}
