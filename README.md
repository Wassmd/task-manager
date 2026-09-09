# Task Manager

Spring Boot REST-Service zur Verwaltung von Tasks. API-Contract-first via OpenAPI.

## Stack
- Java 25, Spring Boot 4.1.1
- Spring Web, Security, Data JPA
- PostgreSQL, Flyway
- MapStruct, Lombok

## Struktur
```
api/                     OpenAPI-Spezifikation
task-manager-service/    Spring Boot Anwendung
```

## Starten
```bash
cd task-manager-service
./mvnw spring-boot:run
```

Mit Docker Compose:
```bash
cd task-manager-service
docker compose up
```

## Tests
```bash
cd task-manager-service
./mvnw test
```

## API
Siehe [`api/openapi.yaml`](api/openapi.yaml).
