package com.paxier.task_manager_service.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

/**
 * Enforces the hexagonal (ports & adapters) architecture of the task-manager-service.
 *
 * <p>Layer directions:
 * {@code domain <- application <- adapter}, with {@code config} wiring everything together.
 * The generated {@code api} package is intentionally excluded from the layer checks
 * (it is a framework-neutral transport shared by core and adapters).
 */
@AnalyzeClasses(
    packages = "com.paxier.task_manager_service",
    importOptions = DoNotIncludeTests.class)
class HexagonalArchitectureTest {

  private static final String BASE = "com.paxier.task_manager_service";

  /**
   * The primary requirement: no cyclic dependencies between the top-level modules
   * (adapter, application, domain, api, config).
   */
  @ArchTest
  static final ArchRule no_cyclic_dependencies_between_slices =
      slices()
          .matching(BASE + ".(*)..")
          .should().beFreeOfCycles();

  /** Hexagon layering: adapters depend on the core, never the other way around. */
  @ArchTest
  static final ArchRule hexagonal_layering =
      layeredArchitecture()
          .consideringOnlyDependenciesInLayers()
          .layer("Domain").definedBy(BASE + ".domain..")
          .layer("Application").definedBy(BASE + ".application..")
          .layer("Adapters").definedBy(BASE + ".adapter..")
          .layer("Config").definedBy(BASE + ".config..")

          .whereLayer("Adapters").mayNotBeAccessedByAnyLayer()
          .whereLayer("Application").mayOnlyBeAccessedByLayers("Adapters", "Config")
          .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Adapters", "Config");

  /** The application core must stay free of web/persistence framework types. */
  @ArchTest
  static final ArchRule application_is_free_of_frameworks =
      ArchRuleDefinition.noClasses()
          .that().resideInAPackage(BASE + ".application..")
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
          .that().resideInAPackage(BASE + ".domain..")
          .should().dependOnClassesThat()
          .resideInAnyPackage(BASE + ".application..", BASE + ".adapter..")
          .because("the domain is the innermost layer");

  /** Use-case implementations belong to the application.service package. */
  @ArchTest
  static final ArchRule services_reside_in_application_service =
      ArchRuleDefinition.classes()
          .that().haveSimpleNameEndingWith("Service")
          .should().resideInAPackage(BASE + ".application.service..");

  /** Persistence adapters implement outbound ports and live in the persistence package. */
  @ArchTest
  static final ArchRule persistence_adapters_reside_in_persistence_package =
      ArchRuleDefinition.classes()
          .that().haveSimpleNameEndingWith("PersistenceAdapter")
          .should().resideInAPackage(BASE + ".adapter.out.persistence..");
}
