package com.paxier.task_manager_service.email;

import com.paxier.task_manager_service.model.TaskDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class EmailService {

  @Async
  @EventListener
  public void sendEmail(TaskDetails taskDetails)  {
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
