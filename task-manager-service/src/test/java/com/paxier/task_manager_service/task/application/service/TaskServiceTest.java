package com.paxier.task_manager_service.task.application.service;


import static com.paxier.task_manager_service.api.model.TaskStatus.DONE;
import static com.paxier.task_manager_service.api.model.TaskStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.task.application.port.out.TaskNotificationPort;
import com.paxier.task_manager_service.task.application.port.out.TaskRepositoryPort;
import com.paxier.task_manager_service.task.application.port.out.UserExistencePort;
import com.paxier.task_manager_service.task.domain.TaskDetails;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  TaskRepositoryPort taskRepository;

  @Mock
  UserExistencePort userExistence;

  @Mock
  TaskNotificationPort taskNotification;

  @InjectMocks
  private TaskService taskService;

  @Test
  void getTasks_returnsTwoTasksWithExpectedTitlesAndStatus() {
    Task first = new Task("My first task", OPEN);
    first.setId(UUID.randomUUID());
    Task second = new Task("My second task", DONE);
    second.setId(UUID.randomUUID());

    given(taskRepository.findAllWithWatchers()).willReturn(List.of(first, second));

    List<Task> tasks = taskService.getTasks();

    assertThat(tasks)
        .hasSize(2)
        .extracting(Task::getTitle, Task::getStatus)
        .containsExactlyInAnyOrder(
            tuple("My first task", OPEN),
            tuple("My second task", DONE)
        );
  }

  @Test
  void createTask_savesTaskAndNotifiesAndReturnsSavedTask() {
    // given
    UUID userId = UUID.randomUUID();
    Task taskToCreate = new Task("New Task", OPEN);
    taskToCreate.setDescription("This is a new task");
    taskToCreate.setDueDate(LocalDate.now().plusDays(7));
    taskToCreate.setUserId(userId);

    Task savedTask = new Task("New Task", OPEN);
    savedTask.setId(UUID.randomUUID());
    savedTask.setDescription("This is a new task");
    savedTask.setDueDate(taskToCreate.getDueDate());
    savedTask.setUserId(userId);

    given(userExistence.existsById(userId)).willReturn(true);
    given(taskRepository.save(any(Task.class))).willReturn(savedTask);

    // when
    Task createdTask = taskService.createTask(taskToCreate);

    // then
    assertThat(createdTask).isNotNull();
    assertThat(createdTask.getId()).isEqualTo(savedTask.getId());
    assertThat(createdTask.getTitle()).isEqualTo("New Task");
    assertThat(createdTask.getStatus()).isEqualTo(OPEN);

    verify(taskNotification).notifyTaskCreated(any(TaskDetails.class));
  }
}
