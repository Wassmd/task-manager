package com.paxier.task_manager_service.application.port.out;

import com.paxier.task_manager_service.domain.TaskDetails;

public interface TaskNotificationPort {

  void notifyTaskCreated(TaskDetails taskDetails);
}
