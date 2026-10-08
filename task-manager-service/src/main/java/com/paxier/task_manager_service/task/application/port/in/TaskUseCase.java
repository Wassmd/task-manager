package com.paxier.task_manager_service.task.application.port.in;

import com.paxier.task_manager_service.api.model.Task;
import java.util.List;

public interface TaskUseCase {

  List<Task> getTasks();

  Task createTask(Task task);
}
