package com.paxier.task_manager_service.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;

@Builder
@Getter
public class TaskWatcherDTO {
  private  final String email;
  private final UUID taskId;
}
