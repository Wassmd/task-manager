# Build context is expected to be the repository root, so that both
# `task-manager-service/` and the sibling `api/` directory (referenced by
# pom.xml as ../api) are available.
#
#   docker build -f task-manager-service/Dockerfile -t task-manager-service .

# ---- Build stage. Maven comes from the image, so the wrapper stays a local concern. ----
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace

# api/ sits one level above task-manager-service/ (see pom.xml: ../api)
COPY api/ ../api

# Dependencies first: this layer survives every change that leaves pom.xml alone.
COPY task-manager-service/pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY task-manager-service/src ./src
RUN mvn -B -ntp package -DskipTests

# ---- Runtime stage. A JRE is enough, no build tools in the final image. ----
FROM eclipse-temurin:25-jre AS runtime
LABEL authors="Wasim"
WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

RUN useradd --system --uid 1001 spring
USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
