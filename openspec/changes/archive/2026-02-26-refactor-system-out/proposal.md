# Proposal: Refactor System Out — Structured SLF4J Logging

## Intent

The backend currently uses `System.out.println` and `System.err.println` for output in several key components. These raw console prints are not captured by the application's logging framework, making it impossible to control log levels, route logs to files/aggregators (e.g., Loki, CloudWatch), or filter output in production. Additionally, the global `ControllerAdvice` exception handler silently swallows all errors without emitting any log, making debugging nearly impossible.

This change replaces all raw console output with structured SLF4J logging and adds observability to the service and use-case layers.

## Scope

### In Scope
- Replace all `System.out.println` / `System.err.println` in `MpPreferenceBuilder.java` (7 occurrences) with SLF4J calls
- Add `log.warn` / `log.error` to every handler in `ControllerAdvice.java` (11 handlers)
- Add `@Slf4j` + `log.info` / `log.debug` operational logging to key services: `AlquilerService`, `RembolsoService`, `FileStorageService`, `CheckOutAlquilerService`
- Add `@Slf4j` + `log.info` entry logging to use cases: `CrearAlquilerUseCase`, `CambiarAutoUseCase`, `EliminarAutoUseCase`

### Out of Scope
- Configuring external log aggregators or log shipping
- Adding logging to controller layer (handled by ControllerAdvice centrally)
- Changing log format / Logback XML configuration
- Adding logging to filter/decorator pattern classes

## Approach

Use Lombok's `@Slf4j` annotation (Lombok is already a project dependency via `@AllArgsConstructor` usage) to inject a `log` field. Apply SLF4J parameterized style (`{}` placeholders) throughout. Error-level logs always include the exception instance to preserve the stack trace. No new dependencies are needed — Spring Boot Starter includes SLF4J + Logback.

**Log level strategy:**
- `log.error` → unexpected failures (IO errors, MercadoPago API failures, unhandled runtime exceptions)
- `log.warn` → business violations, not-found, auth issues, data conflicts
- `log.info` → domain events (rental created, refund processed, preference registered)
- `log.debug` → diagnostic details (amounts, internal state)

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `service/builder/MpPreferenceBuilder.java` | Modified | Replace 7× raw prints with `log.error`/`log.debug` + add `@Slf4j` |
| `exception/ControllerAdvice.java` | Modified | Add `@Slf4j` + log call to all 11 exception handlers |
| `service/AlquilerService.java` | Modified | Add `@Slf4j` + `log.info` on key methods |
| `service/RembolsoService.java` | Modified | Add `@Slf4j` + `log.info` on refund processing |
| `service/FileStorageService.java` | Modified | Add `@Slf4j` + `log.error` in IOException catch blocks |
| `service/checkOut/CheckOutAlquilerService.java` | Modified | Add `@Slf4j` + `log.info` on preference creation |
| `service/useCase/Alquiler/CrearAlquilerUseCase.java` | Modified | Add `@Slf4j` + `log.info` at entry |
| `service/useCase/Alquiler/CambiarAutoUseCase.java` | Modified | Add `@Slf4j` + `log.info` on auto swap |
| `service/useCase/Auto/EliminarAutoUseCase.java` | Modified | Add `@Slf4j` + `log.warn` on deletion |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Lombok `@Slf4j` not on classpath for some class | Low | Lombok is already used project-wide; all services compile with it |
| Log statements expose sensitive data (email, amounts) | Low | Only log non-PII business identifiers; amounts are operational data |
| Tests fail due to logger initialization | Low | All changes are additive; no logic altered; existing 13 tests unchanged |

## Rollback Plan

All changes are purely additive (annotation + log statements). To rollback:
1. Remove `@Slf4j` annotation from modified classes
2. Remove the `log.*` statements added
3. Restore the 7 `System.out/err.println` calls in `MpPreferenceBuilder.java`

Git revert is the fastest path: `git revert HEAD` if committed atomically.

## Dependencies

- Lombok on classpath (already present)
- SLF4J + Logback (bundled with `spring-boot-starter`, already present)

## Success Criteria

- [ ] Zero `System.out` / `System.err` in any `.java` file
- [ ] Every `@ExceptionHandler` in `ControllerAdvice` emits a structured log line
- [ ] Domain events (rental created, refund initiated) appear in application log at INFO level
- [ ] MercadoPago failures appear in log at ERROR level with full stack trace
- [ ] All 13 existing tests continue to pass
