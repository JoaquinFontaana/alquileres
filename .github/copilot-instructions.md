# Alquileres - AI Coding Agent Instructions

## Project Overview
Full-stack vehicle rental application using **Spring Boot 3.4.1** (Java 17) backend with MySQL, and **Angular 20** (standalone components) frontend. The system manages rental workflows with state machines, JWT authentication, and scheduled tasks.

## Architecture & Core Patterns

### Backend (Spring Boot)
**Package Structure**: `inge2.com.alquileres.backend`
- **State Pattern**: Critical for business logic - `Auto` and `Alquiler` entities use state machines
  - `model/state/auto/`: `EstadoAuto` interface → `Disponible`, `Alquilado`, `Mantenimiento`, `DeBaja` states
  - `model/state/alquiler/`: `EstadoAlquiler` interface → `ConfirmacionPendiente`, `RetiroPendiente`, `EnUso`, `Finalizado`, `Cancelado` states
  - State factories handle transitions; never modify state directly - use state methods
- **Use Case Pattern**: Business logic in `service/useCase/` (e.g., `CrearAlquilerUseCase`, `ListarAutosUseCase`)
  - Use cases coordinate services and helpers
  - Always annotated with `@Service @AllArgsConstructor`
- **Helper Services**: `service/helper/` for common validations (e.g., `AutoHelperService`, `ClienteHelperService`)
- **MapStruct**: All entity↔DTO conversions use MapStruct mappers in `mapper/` (e.g., `AutoMapper`, `AlquilerMapper`)
  - Annotate with `@Mapper(componentModel = "spring")`
  - Use `@Mapping` for custom field mappings
- **Scheduled Tasks**: `scheduler/AlquileresScheduled.java` handles pending payments cleanup and expired rentals

**Security & Auth**:
- JWT-based (`io.jsonwebtoken` 0.11.5) - see `security/JWTService.java`
- Method-level: `@PreAuthorize("hasAuthority('CLIENT')")` or `'ADMIN'` on controllers
- CORS configured for `http://localhost:3000` in `configuration/CorsConfig.java`

### Frontend (Angular 20)
**State Management**: NgRx Signals (`@ngrx/signals`)
- `signalStore` with `withState`, `withMethods`, `withComputed`, `withHooks`
- Example: `user/store/auth-store.ts` manages authentication state
- Shared stores: `shared/stores/vehicles-store.ts`, `sucursal-store.ts`, `empleado-store.ts`

**Component Patterns**:
- Standalone components only (no modules)
- Signal-based reactivity: `signal()`, `computed()`, `effect()`
- Input/output via signals: `input<T>()`, `output<T>()`
- Example: `shared/vehicles/vehicle-card/vehicle-card.ts`

**Routing & Guards**:
- Functional guards in `guards/`: `authGuard`, `adminGuard`, `clienteGuard`
- Use `CanActivateFn` or `CanActivateChildFn`
- Guards inject `AuthStore` to check authentication/roles

**API Communication**:
- Base URL in `shared/consts.ts`: `baseUrlApi = 'http://localhost:8080'`
- Token helper: `addToken(token)` returns headers with Bearer token
- Services in feature folders use `HttpClient` with `inject()`

## Development Workflows

### Running the Application
```bash
# Full stack with Docker (recommended)
docker-compose up --build

# Backend standalone (requires JDK 17, MySQL running)
cd backend
./mvnw spring-boot:run

# Frontend standalone
cd frontend
npm start  # Runs on http://localhost:3000
```

### Testing
```bash
# Backend tests
cd backend
./mvnw test

# Frontend tests
cd frontend
npm test
```

### Key Files & Configuration
- **Backend config**: `backend/src/main/resources/application.properties` (DB, JPA, mail settings)
- **Backend dependencies**: `backend/pom.xml` (MapStruct 1.5.5, JWT, Lombok, MySQL connector)
- **Frontend config**: `frontend/package.json`, `frontend/angular.json`
- **Environment**: Backend `.env` file (not tracked) for DB credentials, JWT secret, Cloudinary, MercadoPago

## Code Conventions

### Backend Java
- Use **Lombok**: `@AllArgsConstructor`, `@Getter`, `@Setter` on entities/DTOs
- **Never expose entities directly** - always use DTOs
- **Transaction boundaries**: `@Transactional` on use cases modifying multiple entities
- **Validation**: Use Jakarta `@Valid` on controller DTOs; custom validations in helper services
- **Exceptions**: Custom exceptions in `exception/` package

### Frontend TypeScript
- **File naming**: `component-name.ts` (no `.component.ts` suffix)
- **Imports**: Use path aliases - `@models`, `@auth-store`, `@shared/consts`
- **Signals over Observables**: Prefer signals for local state; Observables for HTTP/async
- **Reactive patterns**: Use `rxMethod` from `@ngrx/signals/rxjs-interop` for side effects in stores
- **Type safety**: All models in `app/models/` with `index.ts` for exports

### Naming Patterns
- **Backend DTOs**: `<Entity>DTO<Action>` (e.g., `AutoDTOListar`, `AlquilerDTOCrear`)
- **Frontend models**: Match backend DTOs but use TypeScript conventions (PascalCase interfaces)
- **Services**: `<Domain>Service` for data services, `<Domain>Data` for HTTP services in frontend

## Critical Gotchas
1. **State transitions**: Always use state object methods (e.g., `auto.getEstado().iniciarAlquiler(auto, autoService)`) - don't set state fields directly
2. **MapStruct**: Run `mvn clean compile` after changing mappers to regenerate implementations
3. **Angular signals**: Use `computed()` for derived state, not manual updates in `effect()`
4. **CORS**: Backend only allows `localhost:3000` - update `CorsConfig.java` for new origins
5. **JWT expiration**: Tokens validated in `AuthStore.isTokenValid()` - check `exp * 1000 > Date.now()`
6. **Scheduled tasks**: Fixed delay tasks in `AlquileresScheduled.java` - don't create duplicate schedulers

## External Integrations
- **Cloudinary**: Image uploads for vehicle photos (`service/FileStorageService.java`)
- **MercadoPago**: Payment processing (`service/PagoService.java`, `configuration/MPConfig.java`)
- **Email**: Spring Boot Mail for notifications (`service/` - check for `JavaMailSender` usage)

## Common Tasks
- **Add endpoint**: Create DTO → Mapper → Use Case → Controller (update `SecurityConfig` if auth changes)
- **Add state transition**: Modify state interface + implementations, update factory if needed
- **Add frontend route**: Update `app.routes.ts`, add guard if protected
- **Add vehicle filter**: Extend `service/filter/auto/BaseAutoFilter.java` chain of responsibility

## Key Documentation
- Backend README: [backend/README.md](../backend/README.md)
- Frontend README: [frontend/README.md](../frontend/README.md)
- State tests: `backend/target/surefire-reports/` for state machine test results
