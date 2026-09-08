package com.paxier.task_manager_service.controller;

import static com.paxier.task_manager_service.api.model.TaskStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.paxier.task_manager_service.api.model.Task;
import com.paxier.task_manager_service.config.SecurityConfig;
import com.paxier.task_manager_service.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(TaskRestController.class)
@Import(SecurityConfig.class)
@WithMockUser
class TaskRestControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  MockMvcTester mockMvcTester;

  @MockitoBean
  TaskService taskService;

  @Test
  void testGetAllTasks() throws Exception {
    Task task1 = new Task("My first task", OPEN);
    task1.description("My first task description");
    Task task2 = new Task("My second task", OPEN);
    task1.description("My second task description");

    List<Task> tasks = List.of(task1, task2);

    given(taskService.getTasks()).willReturn(tasks);

    mockMvc.perform(get("/api/v1/tasks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$.[0].title").value("My first task"));
  }

  @Test
  void testGetAllTasksTester() {
    given(taskService.getTasks()).willReturn(getTasks());
    assertThat(mockMvcTester.get().uri("/api/v1/tasks"))
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$[0].title").isEqualTo("My first task");
  }

  List<Task> getTasks() {
    Task task1 = new Task( "My first task", OPEN);
    task1.description("My first task description");
    Task task2 = new Task("My second task", OPEN);
    task1.description("My second task description");

    return List.of(task1, task2);
  }

  @Test
  void testPostCreateTask() {
    given(taskService.createTask(any(Task.class)))
        .willReturn(new Task("My first task", OPEN));

    assertThat(mockMvcTester.post()
        .uri("/api/v1/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"title\": \"My first task\", \"status\": \"OPEN\"}"))
        .hasStatus(HttpStatus.CREATED)
        .bodyJson()
        .extractingPath("$.title").isEqualTo("My first task");
  }
}