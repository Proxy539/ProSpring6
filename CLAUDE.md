# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Gradle multi-module project following the structure of the *Pro Spring 6* book. Each module corresponds to a book chapter and demonstrates progressively more advanced Spring features. Modules are largely independent, though some depend on `:chapter02` (shared base classes) or `:chapter05` (AspectJ classes).

## Build Commands

```bash
# Build all modules
./gradlew build

# Build a specific module
./gradlew :chapter05:build

# Run all tests across all modules
./gradlew test

# Run tests for a specific module
./gradlew :chapter04:test

# Run a single test class
./gradlew :chapter04:test --tests "com.apress.prospring6.four.MessageRendererTest"

# Run a specific test method
./gradlew :chapter04:test --tests "com.apress.prospring6.four.MessageRendererTest.testStandardOutMessageRenderer"

# Run a chapter's main class (chapters using the 'application' plugin: chapter02, chapter03, chapter04)
./gradlew :chapter02:run
./gradlew :chapter02:run -Pmain=com.apress.prospring6.two.annotated.HelloWorldSpringAnnotated

# Run a Spring Boot chapter's application (chapter04-boot, chapter05-boot)
./gradlew :chapter04-boot:bootRun
```

## Module Structure

| Module | Topic |
|---|---|
| `chapter02` | Spring DI basics — IoC, `MessageProvider`/`MessageRenderer` pattern, annotation-based config |
| `chapter03` | Bean wiring — autowiring, constructor injection, collections, bean naming, lifecycle |
| `chapter04` | Advanced bean configuration — `ApplicationContext`, `Environment`, property editors, bean lifecycle hooks, `@Aware` interfaces. Has unit + integration tests (`*IT.java`). Depends on `:chapter02`. |
| `chapter04-boot` | Spring Boot equivalent of chapter04 (`spring-boot-starter`, no web). Depends on `:chapter02`. |
| `chapter05` | AOP — Spring AOP proxies, AspectJ pointcuts/advice, introductions, `@Aspect` annotation-based config. Plain `spring-context` + `spring-aop` + `aspectjweaver`, no Boot. |
| `chapter05-boot` | Spring Boot equivalent of chapter05, using `spring-boot-starter-aspectj`. Depends on `:chapter05`. |
| `chapter06` | Spring JDBC data access (MariaDB `SINGER`/`ALBUM` schema). Scaffolded only — `docker-compose.yml` brings up a MariaDB container seeded via `docker-build/scripts/CreateTable.sql` / `InsertData.sql`; no Java source yet. |

## Key Architecture Notes

- **Java 21**, **Spring Framework 7.0.8** (chapters 02, 03, 04, 05, 06), **Spring Boot 4.1.0** (chapter04-boot, chapter05-boot), **AspectJ 1.9.24** (chapter05).
- Each non-Boot chapter's demo classes are standalone `main()` programs, not web apps. Run them via `./gradlew :chapterXX:run`.
- `chapter04` uses both unit tests (Mockito) and Spring integration tests (`*IT.java` suffix) with `spring-test`.
- `chapter05` uses `spring-aop` + `aspectjweaver` and `AnnotationConfigApplicationContext` directly in tests rather than `@SpringBootTest`; `chapter05-boot` wraps the same aspects/pointcuts in a Boot app (`spring-boot-starter-aspectj`) and tests them with `@SpringBootTest`.
- `chapter06` expects a local MariaDB instance for its (upcoming) JDBC examples — start it with `docker compose -f chapter06/docker-compose.yml up -d` before running that module; schema/seed data live in `chapter06/docker-build/scripts/`.
- Gradle configuration cache is enabled (`org.gradle.configuration-cache=true`) — avoid tasks that break it.
- `chapter02`, `chapter03`, and `chapter04` use the `application` plugin and support a `-Pmain=<className>` property to select which main class to run.
