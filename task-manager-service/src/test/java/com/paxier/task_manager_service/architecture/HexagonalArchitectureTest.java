package com.paxier.task_manager_service.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

/**
 * Enforces the feature-based hexagonal (ports & adapters) architecture.
 *
 * <p>Two features ({@code task} incl. its task-watcher, and {@code user}) are each organised as a
 * hexagon ({@code domain <- application <- adapter}). The only inter-feature dependency is
 * {@code user -> task}, created by {@code UserExistenceAdapter} implementing the task feature's
 * {@code UserExistencePort} (dependency inversion). The generated {@code api} package is a shared,
 * framework-neutral transport and is intentionally excluded from the layer checks.
 */
@AnalyzeClasses(
    packages = "com.paxier.task_manager_service",
    importOptions = DoNotIncludeTests.class)
class HexagonalArchitectureTest {

  private static final String BASE = "com.paxier.task_manager_service";

  /**
   * The primary requirement: no cyclic dependencies between the top-level slices
   * (task, user, config, shared, api).
   */
  @ArchTest
  static final ArchRule no_cyclic_dependencies_between_slices =
      slices()
          .matching(BASE + ".(*)..")
          .should().beFreeOfCycles();

  /** Hexagon layering (applies within every feature): adapters depend on the core, never reversed. */
  @ArchTest
  static final ArchRule hexagonal_layering =
      layeredArchitecture()
          .consideringOnlyDependenciesInLayers()
          .layer("Domain").definedBy("..domain..")
          .layer("Application").definedBy("..application..")
          .layer("Adapters").definedBy("..adapter..")
          .layer("Config").definedBy("..config..")

          .whereLayer("Adapters").mayNotBeAccessedByAnyLayer()
          .whereLayer("Application").mayOnlyBeAccessedByLayers("Adapters", "Config")
          .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Adapters", "Config");

  /** The application core must stay free of web/persistence framework types. */
  @ArchTest
  static final ArchRule application_is_free_of_frameworks =
      ArchRuleDefinition.noClasses()
          .that().resideInAPackage("..application..")
          .should().dependOnClassesThat()
          .resideInAnyPackage(
              "org.springframework.web..",
              "org.springframework.data..",
              "jakarta.persistence..")
          .because("the application core must not depend on inbound/outbound frameworks");

  /** The domain is the innermost layer and must not know about application or adapters. */
  @ArchTest
  static final ArchRule domain_depends_on_nothing_inward =
      ArchRuleDefinition.noClasses()
          .that().resideInAPackage("..domain..")
          .should().dependOnClassesThat()
          .resideInAnyPackage("..application..", "..adapter..")
          .because("the domain is the innermost layer");

  /**
   * The {@code task} feature must never depend on the {@code user} feature. The relationship is
   * inverted via {@code UserExistencePort}, so the only compile-time edge is {@code user -> task}.
   */
  @ArchTest
  static final ArchRule task_feature_does_not_depend_on_user_feature =
      ArchRuleDefinition.noClasses()
          .that().resideInAPackage(BASE + ".task..")
          .should().dependOnClassesThat().resideInAPackage(BASE + ".user..")
          .because("the task -> user dependency is inverted through UserExistencePort");

  /**
   * The {@code user} feature may only reach the {@code task} feature through its ports, never through
   * task-internal domain, services, or adapters.
   */
  @ArchTest
  static final ArchRule user_feature_only_touches_task_through_ports =
      ArchRuleDefinition.noClasses()
          .that().resideInAPackage(BASE + ".user..")
          .should().dependOnClassesThat()
          .resideInAnyPackage(
              BASE + ".task.domain..",
              BASE + ".task.application.service..",
              BASE + ".task.adapter..")
          .because("features must communicate only through published ports");

  /** Use-case implementations belong to an application.service package. */
  @ArchTest
  static final ArchRule services_reside_in_application_service =
      ArchRuleDefinition.classes()
          .that().haveSimpleNameEndingWith("Service")
          .should().resideInAPackage("..application.service..");

  /** Persistence adapters live in a persistence package. */
  @ArchTest
  static final ArchRule persistence_adapters_reside_in_persistence_package =
      ArchRuleDefinition.classes()
          .that().haveSimpleNameEndingWith("PersistenceAdapter")
          .should().resideInAPackage("..adapter.out.persistence..");
}
