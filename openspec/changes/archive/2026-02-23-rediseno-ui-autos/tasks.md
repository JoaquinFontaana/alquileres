# Tasks: Rediseño UI — Listado de Vehículos

## Phase 1: Base Card — Hover & Skeleton (card.html / card.scss)

- [ ] 1.1 **`card.html`** — Envolver `<img>` en `<div class="card-image-wrapper">` y reemplazar el `@placeholder` text por `<div class="skeleton-image"></div>`
- [ ] 1.2 **`card.scss`** — Agregar `.card-image-wrapper { position: relative; overflow: hidden }` y transición hover en `.custom-card`: `transition: transform 0.28s ease, box-shadow 0.28s ease` con `&:hover { transform: translateY(-6px) scale(1.012); box-shadow: 0 20px 44px rgba(0,0,0,.22), 0 4px 12px rgba(61,93,236,.18) }`
- [ ] 1.3 **`card.scss`** — Agregar `.skeleton-image` con animación shimmer (`@keyframes skeleton-shimmer`) para el placeholder de la imagen deferida; fijar `height: 200px; aspect-ratio: 16/9` al wrapper para evitar layout shift

---

## Phase 2: Vehicle Card — Badge, Specs con Íconos, Precio Premium (vehicle-card.html / vehicle-card.scss)

- [ ] 2.1 **`vehicle-card.ts`** — Importar `MatIconModule` en el array `imports[]` del componente `VehicleCard`
- [ ] 2.2 **`vehicle-card.html`** — Agregar slot `<div class="category-badge"><mat-icon>category</mat-icon> {{ localVehicle.categoria }}</div>` como primer hijo del slot `[content]` pasado a `app-card` (usando `position: absolute` sobre la imagen via `card-image-wrapper`)
- [ ] 2.3 **`vehicle-card.html`** — Reemplazar los tres `<span class="spec">` por versiones con `<mat-icon>`: `people` para capacidad, `currency_exchange` para rembolso, `category` para categoría
- [ ] 2.4 **`vehicle-card.html`** — Envolver el precio en `<div class="price-tag"><span class="price-amount">${{ localVehicle.precioPorDia }}</span><span class="price-unit">/día</span></div>`
- [ ] 2.5 **`vehicle-card.scss`** — Estilar `.category-badge`: `position: absolute; top: 0.75rem; left: 0.75rem; z-index: 2; display: flex; align-items: center; gap: 0.3rem; padding: 0.3rem 0.7rem; background: rgba(15,17,23,.62); backdrop-filter: blur(8px); border: 1px solid rgba(255,255,255,.18); border-radius: 20px; color: #fff; font-size: 0.72rem; font-weight: 600; text-transform: uppercase` + `&:hover` → fondo primary
- [ ] 2.6 **`vehicle-card.scss`** — Actualizar `.spec`: cambiar `background-color: #f7fafc` por `background: rgba(61,93,236,0.10); border: 1px solid rgba(61,93,236,0.2); color: $primary-color` y agregar estilo para `mat-icon` dentro (`font-size: 0.9rem; vertical-align: middle`)
- [ ] 2.7 **`vehicle-card.scss`** — Estilar `.price-amount`: `font-size: 1.55rem; font-weight: 800; background: linear-gradient(135deg, $primary-color 0%, #818cf8 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text` y `.price-unit`: `font-size: 0.8rem; color: #64748b`
- [ ] 2.8 **`vehicle-card.scss`** — Agregar `transition: transform 0.28s ease` a la imagen hover mediante `::ng-deep .card-image-wrapper img` y `:host:hover ::ng-deep .card-image-wrapper img { transform: scale(1.05) }`

---

## Phase 3: Vehicle List — Título con Gradiente & Empty State (vehicle-list.html / vehicle-list.scss)

- [ ] 3.1 **`vehicle-list.html`** — Reemplazar `<h1>Vehiculos</h1>` por el bloque:
  ```html
  <div class="list-title">
    <div class="list-title-icon"><mat-icon>directions_car</mat-icon></div>
    <div class="list-title-text">
      <h1>Vehículos disponibles</h1>
      <p>Seleccioná tu auto ideal antes de confirmar las fechas</p>
    </div>
  </div>
  ```
- [ ] 3.2 **`vehicle-list.html`** — Reemplazar el texto `No hay vehiculos para listar....` en `@empty` por:
  ```html
  <div class="empty-state">
    <div class="empty-state-icon"><mat-icon>search_off</mat-icon></div>
    <h3>No hay vehículos disponibles</h3>
    <p>Probá cambiando los filtros de categoría, sucursal o rango de fechas.</p>
  </div>
  ```
- [ ] 3.3 **`vehicle-list.ts`** — Confirmar que `MatIconModule` ya está importado (o agregarlo si falta)
- [ ] 3.4 **`vehicle-list.scss`** — Estilar `.list-title`: `display: flex; align-items: center; gap: 0.75rem; margin-bottom: 0.5rem` — `.list-title-icon`: pill cuadrada 44×44px con `background: linear-gradient(135deg, $primary-color 0%, #818cf8 100%); border-radius: 12px` — `.list-title-text h1`: gradient text `linear-gradient(135deg, #fff, #a5b4fc)`
- [ ] 3.5 **`vehicle-list.scss`** — Estilar `.empty-state`: `grid-column: 1 / -1; display: flex; flex-direction: column; align-items: center; gap: 1rem; padding: 4rem 2rem; border: 1.5px dashed rgba(255,255,255,0.12); border-radius: 16px` — `.empty-state-icon mat-icon`: `font-size: 3rem; color: rgba(255,255,255,0.2)` — `.empty-state h3, p`: texto atenuado

---

## Phase 4: Vehicle Filter — Cosmético Glass (vehicle-filter.scss)

- [ ] 4.1 **`vehicle-filter.scss`** — Agregar a `.vehicle-filter-container`: `background: rgba(255,255,255,0.05); backdrop-filter: blur(8px); -webkit-backdrop-filter: blur(8px); border: 1px solid rgba(255,255,255,0.10); border-radius: $border-radius; padding: 0.65rem 1rem`

---

## Phase 5: Verificación

- [ ] 5.1 Ejecutar `npx ng test --no-watch --browsers=ChromeHeadless` en `frontend/` y confirmar que todos los tests existentes pasan (los specs actuales solo validan `should create`)
- [ ] 5.2 Levantar `npm run dev` y verificar visualmente en Chrome: hover en cards, badge flotante, specs con íconos, precio grande, empty state (filtrar sin resultados), título de sección
- [ ] 5.3 Validar responsivo: mobile 1 col (≤576px), tablet 2 col (577–768px), laptop 3 col (769–1024px), desktop 4 col (1025px+)
- [ ] 5.4 Verificar que los botones de acción (Alquilar / Modificar / Eliminar) siguen funcionando y que el routing no se rompe
- [ ] 5.5 Comprobar que `rental-card` y otros usos de `app-card` no se ven afectados negativamente por los cambios en `card.scss`
