package com.paxier.task_manager_service.application.service;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.application.port.in.TaskUseCase;
import com.paxier.task_manager_service.application.port.out.TaskNotificationPort;
import com.paxier.task_manager_service.application.port.out.TaskRepositoryPort;
import com.paxier.task_manager_service.application.port.out.UserRepositoryPort;
import com.paxier.task_manager_service.domain.TaskDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskUseCase {

  private final TaskRepositoryPort taskRepository;
  private final UserRepositoryPort userRepository;
  private final TaskNotificationPort taskNotification;

  @Override
  public List<Task> getTasks() {
    return taskRepository.findAllWithWatchers();
  }

  @Override
  public Task createTask(Task task) {
    if (task.getUserId() != null && userRepository.findById(task.getUserId()).isEmpty()) {
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
