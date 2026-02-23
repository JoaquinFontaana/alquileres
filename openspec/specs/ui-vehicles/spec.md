# Spec: UI — Listado de Vehículos

**Domain**: ui-vehicles  
**Source change**: rediseno-ui-autos (2026-02-23)

---

## Requisitos

### Requirement: Vehicle Card Layout

- La `vehicle-card` tiene ancho fijo de `280px` y altura fija de `380px` en desktop
- En mobile el ancho es `100%` y la altura es `auto`
- La imagen ocupa el área superior con `aspect-ratio: 4/3`
- El título aparece en el body de la card (debajo de la imagen), sobre las specs
- El precio (`$X/día`) aparece al final del content, anclado con `margin-top: auto`
- No existe badge flotante de categoría sobre la imagen

### Requirement: Vehicle Card Specs

- Los badges de specs muestran íconos de Angular Material antes del texto:
  - Capacidad → ícono `people`
  - Reembolso → ícono `currency_exchange`
  - Categoría → ícono `category`
- Los badges tienen fondo `rgba(primary, 0.10)` con borde sutil `rgba(primary, 0.20)`

### Requirement: Vehicle Card Price

- El precio usa gradient text: `linear-gradient(135deg, $primary-color, #818cf8)`
- Tamaño `1.55rem`, `font-weight: 800`
- Unidad `/día` a la derecha en `0.8rem`, color muted

### Requirement: Card Hover

- Al hacer hover la `mat-card` aplica `transform: translateY(-6px) scale(1.012)`
- La sombra pasa a `0 20px 44px rgba(0,0,0,.22), 0 4px 12px rgba(primary,.18)`
- La imagen hace zoom a `scale(1.05)` con `transition: 0.28s`

### Requirement: Vehicle List Header

- El título de la sección es `<div class="list-title">` con ícono pill degradado + `<h1>Vehículos disponibles</h1>` + subtítulo
- El header NO usa `<h1>Vehiculos</h1>` solo

### Requirement: Vehicle List Empty State

- Cuando no hay vehículos, se muestra `.empty-state`:
  - Ícono `search_off` de Material centrado en caja redondeada
  - Texto "No hay vehículos disponibles"
  - Texto de ayuda indicando cambiar filtros
  - Borde punteado `1.5px dashed rgba(255,255,255,.12)`

### Requirement: Card Base — Skeleton & Hover

- El `card-image-wrapper` usa `position: relative; overflow: hidden; aspect-ratio: 16/9` (overrideable por contexto)
- El placeholder usa `.skeleton-image` con animación `skeleton-shimmer` (gradient en movimiento)
- `.custom-card` tiene hover elevation definido en `card.scss`
- El `mat-card-header` es condicional: solo se renderiza cuando `title` no está vacío

### Requirement: Vehicle Filter Appearance

- `.vehicle-filter-container` usa glassmorphism: `background: rgba(255,255,255,.05); backdrop-filter: blur(8px); border: 1px solid rgba(255,255,255,.10)`
