# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Gradle multi-module project following the structure of the *Pro Spring 6* book. Each module corresponds to a book chapter and demonstrates progressively more advanced Spring features. Modules are largely independent, though some depend on `:chapter02` for shared base classes.

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

# Run a chapter's main class (chapters using 'application' plugin)
./gradlew :chapter02:run
./gradlew :chapter02:run -Pmain=com.apress.prospring6.two.annotated.HelloWorldSpringAnnotated
```

## Module Structure

| Module | Topic |
|---|---|
| `chapter02` | Spring DI basics — IoC, `MessageProvider`/`MessageRenderer` pattern, annotation-based config |
| `chapter03` | Bean wiring — autowiring, constructor injection, collections, bean naming, lifecycle |
| `chapter04` | Advanced bean configuration — `ApplicationContext`, `Environment`, property editors, bean lifecycle hooks, `@Aware` interfaces. Has unit + integration tests (`*IT.java`). Depends on `:chapter02`. |
| `chapter04-boot` | Spring Boot equivalent of chapter04. Depends on `:chapter02`. |
| `chapter05` | AOP — Spring AOP proxies, AspectJ pointcuts/advice, introductions, `@Aspect` annotation-based config |

## Key Architecture Notes

- **Java 21**, **Spring Framework 7.0.8** (chapters 02–04), **Spring Boot 4.1.0** (chapter04-boot), **AspectJ 1.9.24** (chapter05).
- Each chapter's demo classes are standalone `main()` programs, not web apps. Run them via `./gradlew :chapterXX:run`.
- `chapter04` uses both unit tests (Mockito) and Spring integration tests (`*IT.java` suffix) with `spring-test`.
- `chapter05` uses `spring-aop` + `aspectjweaver` and `AnnotationConfigApplicationContext` directly in tests rather than `@SpringBootTest`.
- Gradle configuration cache is enabled (`org.gradle.configuration-cache=true`) — avoid tasks that break it.
- `chapter02` and `chapter04` use the `application` plugin and support a `-Pmain=<className>` property to select which main class to run.
