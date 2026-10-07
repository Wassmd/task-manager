package com.paxier.task_manager_service.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskWatcherCommand {
  private final String email;
  private final UUID taskId;
}
