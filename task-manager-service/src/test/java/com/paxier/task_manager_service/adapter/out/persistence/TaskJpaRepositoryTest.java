package com.paxier.task_manager_service.adapter.out.persistence;

import static com.paxier.task_manager_service.api.model.TaskStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.paxier.task_manager_service.adapter.out.persistence.entity.TaskEntity;
import com.paxier.task_manager_service.adapter.out.persistence.repository.TaskJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class TaskJpaRepositoryTest {

  @Autowired
  private TaskJpaRepository taskRepository;

  @Test
  void testTask_isBeingSaved() {
    TaskEntity task = TaskEntity.builder()
        .title("Test Task")
        .description("This is a test task")
        .status(OPEN)
        .build();

    TaskEntity savedEntity = taskRepository.save(task);

    assertThat(savedEntity).isNotNull();
    assertThat(savedEntity.getId()).isNotNull();
    assertThat(savedEntity.getTitle()).isEqualTo("Test Task");
  }
}
