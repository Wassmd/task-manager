package com.paxier.task_manager_service.model;

import com.paxier.task_manager_service.api.model.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "task", schema = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;
  String title;
  String description;
  @Enumerated(EnumType.STRING)
  TaskStatus status;
  LocalDate dueDate;

  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<TaskWatcherEntity> watchers = new ArrayList<>();
}
