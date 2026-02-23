# Proposal: Rediseño UI — Listado de Vehículos

## Intent

El listado de vehículos utiliza tarjetas de Angular Material con estilos mínimos. No existe efecto hover en las cards, los badges de especificaciones son spans planos sin íconos, el empty state es puro texto, y el título no tiene jerarquía visual. El objetivo es elevar la experiencia visual al nivel de una app moderna de rental sin cambiar la arquitectura ni el comportamiento funcional.

## Scope

### In Scope

- Mejorar `vehicle-card.html/.scss`: hover animado, specs con íconos Material, precio prominente, badge de estado del vehículo en la imagen
- Mejorar `vehicle-list.html/.scss`: título con gradiente, sección header mejorada, empty state avec ilustración/mensaje amigable
- Mejorar `card.html/.scss` (base): transición suave, efecto hover elevation, placeholder de carga con skeleton
- Mejorar `vehicle-filter.scss`: apariencia más pulida (fondo glass, bordes sutiles)

### Out of Scope

- Cambios en la lógica de negocio (filtros, acciones, routing)
- Paginación (no existe actualmente)
- Componentes fuera del listado (rent-vehicle, my-rentals, admin)
- Cambio de Angular Material como dependencia base

## Approach

**Approach 3 — Enriquecer vehicle-card con estilos propios + mejoras en vehicle-list, sin romper card base.**

1. En `card.scss`: agregar transición `transform` y `box-shadow` en hover. Mejorar placeholder con skeleton shimmer.
2. En `vehicle-card.scss`: overlay semitransparente sobre imagen con badge de categoría/estado flotante, specs badges con íconos Material (`people`, `currency_exchange`, `category`), precio en fuente grande y color primario.
3. En `vehicle-list.html`: reemplazar `<h1>Vehiculos</h1>` por título con ícono y subtítulo. Mejorar el empty state con ícono grande y mensaje descriptivo.
4. En `vehicle-list.scss`: título con gradiente de texto, empty state centrado y estilizado.
5. En `vehicle-filter.scss`: fondo con leve glassmorphism o fondo sutil diferenciado.

## Affected Areas

| Área | Impacto | Descripción |
|------|---------|-------------|
| `shared/card/card.html` | Modified | Agregar clase para skeleton placeholder |
| `shared/card/card.scss` | Modified | Hover elevation + skeleton shimmer |
| `shared/vehicles/vehicle-card/vehicle-card.html` | Modified | Badge de estado en imagen, íconos en specs |
| `shared/vehicles/vehicle-card/vehicle-card.scss` | Modified | Hover, overlay imagen, specs premium, precio prominente |
| `shared/vehicles/vehicle-list/vehicle-list.html` | Modified | Título mejorado con ícono, empty state visual |
| `shared/vehicles/vehicle-list/vehicle-list.scss` | Modified | Estilos del nuevo título y empty state |
| `shared/vehicles/vehicle-filter/vehicle-filter.scss` | Modified | Visual cosmético del filtro |

## Risks

| Riesgo | Probabilidad | Mitigación |
|--------|-------------|------------|
| `::ng-deep` puede volver a afectar otros contextos de `mat-card` | Baja | Usar `:host` lo máximo posible; `::ng-deep` solo si no hay alternativa |
| Imagen `@defer` puede causar layout shift al cargar | Baja | Fijar `aspect-ratio` o `min-height` al contenedor de imagen en placeholder |
| `rental-card` puede verse afectado si se cambia `card.scss` base | Media | Revisar `rental-card.html/.scss` antes de tocar `card.scss`; preferir cambios en `:host` de vehicle-card |

## Rollback Plan

Todos los cambios son de estilos. El rollback consiste en revertir `card.scss`, `vehicle-card.html`, `vehicle-card.scss`, `vehicle-list.html`, `vehicle-list.scss` y `vehicle-filter.scss` a su versión git anterior con `git checkout -- <file>`. No hay cambios en lógica, datos ni rutas.

## Dependencies

- Angular Material Icons (ya disponible en el proyecto)
- Variables y mixins SCSS existentes (`variables.scss`, `mixins.scss`)

## Success Criteria

- [ ] Las cards muestran un efecto hover visible (elevación + sombra) en desktop
- [ ] Cada card muestra un badge flotante de categoría sobre la imagen
- [ ] Las specs de capacidad, reembolso y categoría incluyen íconos Material reconocibles
- [ ] El precio por día es visualmente prominente (tamaño/color diferenciado)
- [ ] El empty state muestra un mensaje y ícono amigables en lugar de texto plano
- [ ] El título del listado tiene jerarquía visual clara
- [ ] El diseño es responsivo en mobile (1 col), tablet (2 col) y desktop (3-4 col)
- [ ] No se rompe la funcionalidad de filtros, acciones (alquilar/editar/eliminar) ni el routing
