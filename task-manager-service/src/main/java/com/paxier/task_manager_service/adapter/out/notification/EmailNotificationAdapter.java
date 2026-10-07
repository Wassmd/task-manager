package com.paxier.task_manager_service.adapter.out.notification;

import com.paxier.task_manager_service.application.port.out.TaskNotificationPort;
import com.paxier.task_manager_service.domain.TaskDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailNotificationAdapter implements TaskNotificationPort {

  @Async
  @Override
  public void notifyTaskCreated(TaskDetails taskDetails) {
    IO.println("Sending email...");
    IO.println("Task Detail: " + taskDetails + "on Thread: " + Thread.currentThread());

    try {
      Thread.sleep(5000);
      throw new InterruptedException("Simulated error while sending email");

    } catch (InterruptedException e) {
      log.error("Error while sending email", e);
    }

    IO.println("Email sent!");
  }
}
