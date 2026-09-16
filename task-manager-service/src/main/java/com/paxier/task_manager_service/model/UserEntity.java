package com.paxier.task_manager_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_user", schema = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;
  String firstName;
  String lastName;
  String email;

  @Setter
  @Builder.Default
  boolean active = true;
}
