package com.paxier.task_manager_service.task.application.port.out;

import java.util.UUID;

/**
 * Outbound port owned by the {@code task} feature to check whether a referenced user exists.
 *
 * <p>This is the dependency-inversion seam between features: the {@code task} feature declares the
 * abstraction it needs, and the {@code user} feature provides the implementation. Therefore
 * {@code task} never depends on {@code user} at compile time (only {@code user -> task}), which
 * keeps the inter-feature dependency graph acyclic.
 */
public interface UserExistencePort {

  boolean existsById(UUID userId);
}
