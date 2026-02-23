## Exploration: rediseno-ui-autos

### Current State

El listado de vehículos está compuesto por tres capas de componentes:

1. **`VehicleList`** (`shared/vehicles/vehicle-list/`) — Contenedor principal. Muestra título "Vehiculos", el filtro y un `<section class="vehicle-list">` que itera con `@for` y renderiza `app-vehicle-card`.
   - La grilla usa el mixin `responsive-grid(1.5rem)`: 1 col (mobile), 2 col (tablet), 3 col (laptop), 4 col (desktop). Con `grid-auto-rows: 1fr` para igualar alturas.
   - Fondo: `$bg-transparent` (`#000000a1`) con `$card-border-radius` (20px).

2. **`VehicleCard`** (`shared/vehicles/vehicle-card/`) — Wrapper delgado sobre `app-card`. Proyecta specs (capacidad, reembolso, categoría), precio y acciones (Alquilar / Editar / Eliminar).

3. **`Card`** (`shared/card/`) — Componente base con `mat-card` (Angular Material). Tiene header con título, imagen deferida a viewport, `mat-card-content` (ng-content) y `mat-card-actions`.
   - Estilos: sombra `box-shadow: 0 4px 12px rgba(0,0,0,0.2)`, imagen de 200px de alto, color de fondo `$bg-light` (blanco), `border-radius: 8px`, `max-width: 400px`.

4. **`VehicleFilter`** (`shared/vehicles/vehicle-filter/`) — Formulario reactivo con selects de sucursal, categoría, estado y date-range picker. Se ubica inline en el header del list.

**Design system existente:**
- `$primary-color: #3d5dec`, `$primary-hover: #2d4ad3`
- Roboto / Helvetica como fuente
- Mixins responsive bien estructurados
- Variables de colores oscuros (`$bg-dark`, `$bg-dark-lighter`) disponibles pero poco usadas en cards

**Limitaciones actuales:**
- La card tiene `max-width: 400px` pero el grid la puede centrar de forma inconsistente
- No hay efecto hover en la card completa (solo en botones)
- La imagen no tiene overlay ni indicación de estado (disponible/no disponible)
- El badge de specs (capacidad, reembolso, categoría) es un span plano con fondo `#f7fafc`, sin íconos
- El título de la sección ("Vehiculos") no tiene estilo de sección diferenciado
- El estado vacío ("No hay vehiculos...") no tiene UI dedicada

### Affected Areas

- `frontend/src/app/shared/vehicles/vehicle-list/vehicle-list.html` — Agregar clase para empty state, ajuste del título
- `frontend/src/app/shared/vehicles/vehicle-list/vehicle-list.scss` — Mejorar espaciado, título, empty state
- `frontend/src/app/shared/vehicles/vehicle-card/vehicle-card.html` — Agregar badge de estado, íconos en specs
- `frontend/src/app/shared/vehicles/vehicle-card/vehicle-card.scss` — Hover en card, mejoras visuales de specs y precio
- `frontend/src/app/shared/card/card.html` — Posible overlay en imagen
- `frontend/src/app/shared/card/card.scss` — Hover/transform en card, transición en imagen

### Approaches

1. **Mejora incremental de la card existente (mat-card)**
   - Mantener `app-card` y `mat-card` como base
   - Agregar hover elevation (`transform: translateY(-4px)`, `box-shadow` más pronunciado)
   - Agregar overlay semitransparente en imagen para mostrar categoría/estado
   - Usar íconos Material en los specs badges
   - Mejorar tipografía del precio (más grande, color llamativo)
   - Pros: mínimo riesgo, pocos archivos afectados, compatible con todo lo existente
   - Cons: limitado por las restricciones de mat-card (estructura fija)
   - Effort: Low

2. **Reemplazo del card base por diseño custom (mantener interfaz ng-content)**
   - Reescribir `card.html` y `card.scss` completamente sin depender de `mat-card`
   - Layout: imagen hero con aspect-ratio fijo, badge flotante de disponibilidad/categoría, contenido abajo
   - Glassmorphism o dark card con gradiente para modo oscuro
   - Hover con tilt/scale y box-shadow animado
   - Pros: máxima flexibilidad visual, diseño premium, sin restricciones de Material
   - Cons: puede romper el card en otros contextos que no sean vehículos, mayor esfuerzo, requiere revisar `rental-card` si usa `app-card`
   - Effort: Medium

3. **Nueva variante de card para vehículos (sin tocar card base)**
   - Crear estilos premium directamente en `vehicle-card.scss` usando encapsulación profunda `:host`
   - Sobreescribir mat-card solo en el contexto de `vehicle-card`
   - Agregar imagen overlay, badges de specs con íconos, animaciones hover
   - Pros: aislado, seguro para otros usos de `app-card`, fácil rollback
   - Cons: duplicación de estilos entre card.scss y vehicle-card.scss
   - Effort: Low/Medium

### Recommendation

**Approach 3 + mejoras en vehicle-list.scss y vehicle-list.html**

Enriquecer `vehicle-card` con estilos propios que transformen la card en algo premium (hover, overlay en imagen, specs con íconos, precio prominente, badge de estado) y mejorar `vehicle-list` (título con gradiente, empty state visual, ajuste de gap). No tocar `card.scss` base para no romper `rental-card` u otros usos.

Adicionalmente mejorar el filter (`vehicle-filter.scss`) para que sea más elegante (borde sutil, fondo acristalado).

### Risks

- Angular Material puede interferir con estilos profundos dentro de `mat-card` (requiere `::ng-deep` o encapsulación `ViewEncapsulation.None` o estilos en el `:host`)
- La imagen usa `@defer (on viewport)` — el placeholder debe mantener las proporciones para evitar layout shift
- Si `rental-card` comparte `app-card`, cualquier cambio en `card.scss` afecta ambos

### Ready for Proposal

Yes — el scope está claro y el riesgo es bajo con el Approach 3.
