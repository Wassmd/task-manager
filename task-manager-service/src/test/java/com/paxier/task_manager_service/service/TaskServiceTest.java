package com.paxier.task_manager_service.service;


import static com.paxier.task_manager_service.api.model.TaskStatus.DONE;
import static com.paxier.task_manager_service.api.model.TaskStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.mapper.TaskMapper;
import com.paxier.task_manager_service.model.TaskEntity;
import com.paxier.task_manager_service.model.UserEntity;
import com.paxier.task_manager_service.repository.TaskRepository;
import com.paxier.task_manager_service.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  TaskRepository taskRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  TaskMapper taskMapper;

  @InjectMocks
  private TaskService taskService;

  @Test
  void getTasks_returnsTwoTasksWithExpectedTitlesAndStatus() {
    given(taskRepository.findAllWithWatchers()).willReturn(List.of(
        TaskEntity.builder().id(java.util.UUID.randomUUID()).title("My first task").status(OPEN).build(),
        TaskEntity.builder().id(java.util.UUID.randomUUID()).title("My second task").status(DONE).build()
    ));

    given(taskMapper.toApiModel((any(TaskEntity.class)))).willAnswer(invocation -> {
      TaskEntity entity = invocation.getArgument(0);
      Task task = new Task(entity.getTitle(), entity.getStatus(), UUID.randomUUID());
      task.setId(entity.getId());
      return task;
    });

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
  void createTask_savesTaskAndReturnsSavedTask() {
    // given
    UUID userId = UUID.randomUUID();
    Task taskToCreate = new Task("New Task", OPEN, userId);
    taskToCreate.setDescription("This is a new task");
    taskToCreate.setDueDate(LocalDate.now().plusDays(7));

    UserEntity user = UserEntity.builder().id(userId).firstName("John").lastName("Doe")
        .email("john@doe.com").active(true).build();

    // Mock the behavior of the repository to return a saved entity with an ID
    TaskEntity savedEntity = TaskEntity.builder()
        .id(java.util.UUID.randomUUID())
        .title(taskToCreate.getTitle())
        .status(taskToCreate.getStatus())
        .description(taskToCreate.getDescription())
        .dueDate(taskToCreate.getDueDate())
        .build();

    Task expectedTask = new Task(savedEntity.getTitle(), savedEntity.getStatus(), userId);
    expectedTask.setDescription(savedEntity.getDescription());
    expectedTask.setDueDate(savedEntity.getDueDate());
    expectedTask.setId(savedEntity.getId());

    given(userRepository.findById(userId)).willReturn(Optional.of(user));
    given(taskRepository.save(any(TaskEntity.class))).willReturn(savedEntity);
    given(taskMapper.toEntity(any(Task.class))).willReturn(savedEntity);
    given(taskMapper.toApiModel(savedEntity)).willReturn(expectedTask);

    // when
    Task createdTask = taskService.createTask(taskToCreate);

    // then
    assertThat(createdTask).isNotNull();
    assertThat(createdTask.getId()).isEqualTo(savedEntity.getId());
    assertThat(createdTask.getTitle()).isEqualTo(taskToCreate.getTitle());
    assertThat(createdTask.getStatus()).isEqualTo(taskToCreate.getStatus());
    assertThat(createdTask.getDescription()).isEqualTo(taskToCreate.getDescription());
    assertThat(createdTask.getDueDate()).isEqualTo(taskToCreate.getDueDate());
  }
}