# Tasks: Refactor System Out — Structured SLF4J Logging

## Phase 1: Fix Raw Console Output (MpPreferenceBuilder)

- [x] 1.1 Add `@Slf4j` annotation to `MpPreferenceBuilder.java`
- [x] 1.2 Replace `System.out.println(BigDecimal.valueOf(monto))` on line 92 with `log.debug("Processing refund for amount: {}", BigDecimal.valueOf(monto))`
- [x] 1.3 Replace `System.err.println("Status: " + ex.getStatusCode())` (lines 63, 95) with `log.error("MercadoPago API error. Status: {}", ex.getStatusCode())`
- [x] 1.4 Replace `System.err.println("Response body: " + ...)` (lines 64, 96) with `log.error("MercadoPago response body: {}", ex.getApiResponse().getContent())`
- [x] 1.5 Replace `System.err.println("Error al : " + ex.getMessage())` (lines 67, 99) with `log.error("MercadoPago MP error: {}", ex.getMessage(), ex)`

## Phase 2: Centralize Error Logging in ControllerAdvice

- [x] 2.1 Add `@Slf4j` annotation to `ControllerAdvice.java`
- [x] 2.2 Add `log.warn("Validation failed: {}", ex.getMessage())` in `handleMethodArgumentNotValid`
- [x] 2.3 Add `log.warn("Entity not found: {}", ex.getMessage())` in `handleEntityNotFound`
- [x] 2.4 Add `log.warn("Entity conflict: {}", ex.getMessage())` in `handleEntityExist`
- [x] 2.5 Add `log.error("Mail delivery failed: {}", ex.getMessage(), ex)` in `handlerMailException`
- [x] 2.6 Add `log.warn("JWT error: {}", ex.getMessage())` in `handlerTokenException`
- [x] 2.7 Add `log.warn("Expired JWT token")` in `handlerExpiredTokenException`
- [x] 2.8 Add `log.warn("Auth credentials not found: {}", ex.getMessage())` in `handlerCredentialsNotFound`
- [x] 2.9 Add `log.error("IO error: {}", ex.getMessage(), ex)` in `ioExceptionHandler`
- [x] 2.10 Add `log.warn("Illegal argument: {}", ex.getMessage())` in `ilegalArgumentHandler`
- [x] 2.11 Add `log.warn("Data integrity violation: {}", ex.getMessage())` in `handlerDataIntegrityViolation`
- [x] 2.12 Add `log.error("Unhandled runtime exception: {}", ex.getMessage(), ex)` in `handleGenericException`

## Phase 3: Service Layer Operational Logging

- [x] 3.1 Add `@Slf4j` to `AlquilerService.java` + `log.info` on `cancelarReserva`, `rembolsarAlquiler`, `eliminarAlquiler`, `finalizarAlquileresVencidosNoRetirados`
- [x] 3.2 Add `@Slf4j` to `RembolsoService.java` + `log.info("Processing refund. Amount: {}", montoRembolso)` before the `if` block
- [x] 3.3 Add `@Slf4j` to `FileStorageService.java` + `log.error("Image upload failed: {}", ex.getMessage(), ex)` in `guardarImagen` catch + `log.error("Image delete failed: {}", ex.getMessage(), ex)` in `deleteImage` catch
- [x] 3.4 Add `@Slf4j` to `CheckOutAlquilerService.java` + `log.info("Registering rental for email: {}", email)` at start of `registrarAlquiler` + `log.info("Preference created, payment URL generated")` before return

## Phase 4: Use-Case Entry Logging

- [x] 4.1 Add `@Slf4j` to `CrearAlquilerUseCase.java` + `log.info("Creating rental. Client: {}, Auto: {}", email, alquilerDTO.getPatenteAuto())` at start of `crearAlquiler`
- [x] 4.2 Add `@Slf4j` to `CambiarAutoUseCase.java` + `log.info` with old and new plate at entry
- [x] 4.3 Add `@Slf4j` to `EliminarAutoUseCase.java` + `log.warn("Deleting auto")` with identifying info at entry

## Phase 5: Verification

- [x] 5.1 Run `mvn test` from `backend/` — all 13 tests must pass
- [x] 5.2 Grep project for `System.out` and `System.err` — must return zero results in `src/main`
- [x] 5.3 Manually trigger a not-found error and confirm `ControllerAdvice` emits a `WARN` log line in console
- [x] 5.4 Confirm docker-compose log output shows structured Spring Boot log format (not raw `println` text)
