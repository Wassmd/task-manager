package com.paxier.task_manager_service.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TaskWatcherRequest {
  @NotBlank
  @Email
  @Size(max = 255)
  private String emailId;

  @NotNull
  private UUID taskId;
}
