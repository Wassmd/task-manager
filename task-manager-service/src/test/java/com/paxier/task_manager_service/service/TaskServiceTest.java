package com.paxier.task_manager_service.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.api.model.TaskStatus;
import java.util.List;
import org.junit.jupiter.api.Test;

class TaskServiceTest {

  private final TaskService taskService = new TaskService();

  @Test
  void getTasks_returnsTwoTasksWithExpectedTitlesAndStatus() {
    List<Task> tasks = taskService.getTasks();

    assertThat(tasks)
        .hasSize(2)
        .extracting(Task::getTitle, Task::getStatus)
        .containsExactlyInAnyOrder(
            tuple("My first task", TaskStatus.OPEN),
            tuple("My second task", TaskStatus.OPEN)
        );

    assertThat(tasks)
        .extracting(Task::getId)
        .doesNotContainNull()
        .doesNotHaveDuplicates();
  }
}