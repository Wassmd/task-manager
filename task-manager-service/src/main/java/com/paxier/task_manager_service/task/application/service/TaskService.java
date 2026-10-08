package com.paxier.task_manager_service.task.application.service;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.task.application.port.in.TaskUseCase;
import com.paxier.task_manager_service.task.application.port.out.TaskNotificationPort;
import com.paxier.task_manager_service.task.application.port.out.TaskRepositoryPort;
import com.paxier.task_manager_service.task.application.port.out.UserExistencePort;
import com.paxier.task_manager_service.task.domain.TaskDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskUseCase {

  private final TaskRepositoryPort taskRepository;
  private final UserExistencePort userExistence;
  private final TaskNotificationPort taskNotification;

  @Override
  public List<Task> getTasks() {
    return taskRepository.findAllWithWatchers();
  }

  @Override
  public Task createTask(Task task) {
    if (task.getUserId() != null && !userExistence.existsById(task.getUserId())) {
      throw new IllegalArgumentException("User not found with id: " + task.getUserId());
    }

    Task savedTask = taskRepository.save(task);

    taskNotification.notifyTaskCreated(toTaskDetails(savedTask));

    return savedTask;
  }

  private TaskDetails toTaskDetails(Task task) {
    return new TaskDetails(
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        task.getDueDate()
    );
  }
}
