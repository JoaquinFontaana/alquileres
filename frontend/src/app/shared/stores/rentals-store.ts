import { inject, computed } from "@angular/core";
import { Rental, CheckOutAlquilerDTO } from "@models";
import { patchState, signalStore, type, withHooks, withMethods, withState, withComputed } from '@ngrx/signals';
import { entityConfig, setAllEntities, withEntities } from '@ngrx/signals/entities';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { tapResponse } from '@ngrx/operators';
import { RentalsData, RentalFilter } from "../rentals/services/rentals-data";
import { pipe, switchMap, tap } from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";
import { AuthStore } from "@auth-store";

interface RentalsState {
  error: string | null;
  success: string | null;
  isLoading: boolean;
  activeFilter: 'all' | 'client' | 'pending-pickup' | 'pending-return';
  selectedSucursal: string | null;
}

const initialState: RentalsState = {
  isLoading: false,
  error: null,
  success: null,
  activeFilter: 'client',
  selectedSucursal: null
};

const rentalConfig = entityConfig({
  entity: type<Rental>(),
  selectId: (rental: Rental) => rental.codigoReserva
});

export const RentalsStore = signalStore(
  { providedIn: 'root' },

  withEntities<Rental>(rentalConfig),
  withState({
    ...initialState
  }),

  withComputed((store) => ({
    // Computed para filtrar alquileres por estado
    activeRentals: computed(() =>
      store.entities().filter(r => r.estadoAlquilerEnum === 'EN_USO')
    ),
    pendingRentals: computed(() =>
      store.entities().filter(r => r.estadoAlquilerEnum === 'CONFIRMACION_PENDIENTE' || r.estadoAlquilerEnum === 'RETIRO_PENDIENTE')
    ),
    completedRentals: computed(() =>
      store.entities().filter(r => r.estadoAlquilerEnum === 'FINALIZADO')
    ),
    canceledRentals: computed(() =>
      store.entities().filter(r => r.estadoAlquilerEnum === 'CANCELADO')
    ),
  })),

  withMethods((store, rentalsService = inject(RentalsData), authStore = inject(AuthStore)) => ({

    // Cargar alquileres del cliente autenticado
    loadClientRentals: rxMethod<void>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, activeFilter: 'client' })),
        switchMap(() => {
          const token = authStore.token() || '';
          return rentalsService.getClientRentals(token).pipe(
            tapResponse({
              next: (rentals: Rental[]) => {
                patchState(store, setAllEntities(rentals, rentalConfig));
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: `Error al cargar alquileres: ${error.message}` });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Cargar todos los alquileres con filtros (admin/empleado)
    loadAllRentals: rxMethod<RentalFilter>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, activeFilter: 'all' })),
        switchMap((filters: RentalFilter) => {
          const token = authStore.token() || '';
          return rentalsService.getAllRentals(filters, token).pipe(
            tapResponse({
              next: (rentals: Rental[]) => {
                patchState(store, setAllEntities(rentals, rentalConfig));
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: `Error al cargar alquileres: ${error.message}` });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Cargar alquileres pendientes de retiro
    loadPendingPickups: rxMethod<string>(
      pipe(
        tap((sucursal) => patchState(store, {
          isLoading: true,
          error: null,
          activeFilter: 'pending-pickup',
          selectedSucursal: sucursal
        })),
        switchMap((sucursal: string) => {
          const token = authStore.token() || '';
          return rentalsService.getPendingPickups(sucursal, token).pipe(
            tapResponse({
              next: (rentals: Rental[]) => {
                patchState(store, setAllEntities(rentals, rentalConfig));
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: `Error al cargar pendientes de retiro: ${error.message}` });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Cargar alquileres pendientes de devolución
    loadPendingReturns: rxMethod<string>(
      pipe(
        tap((sucursal) => patchState(store, {
          isLoading: true,
          error: null,
          activeFilter: 'pending-return',
          selectedSucursal: sucursal
        })),
        switchMap((sucursal: string) => {
          const token = authStore.token() || '';
          return rentalsService.getPendingReturns(sucursal, token).pipe(
            tapResponse({
              next: (rentals: Rental[]) => {
                patchState(store, setAllEntities(rentals, rentalConfig));
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: `Error al cargar pendientes de devolución: ${error.message}` });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Cancelar una reserva
    cancelRental: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, success: null })),
        switchMap((id: number) => {
          const token = authStore.token() || '';
          return rentalsService.cancelRental(id, token).pipe(
            tapResponse({
              next: () => {
                patchState(store, {
                  success: 'Reserva cancelada exitosamente',
                  error: null
                });
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, {
                  error: `Error al cancelar reserva: ${error.message}`,
                  success: null
                });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Marcar como entregado (empleado)
    markAsDelivered: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, success: null })),
        switchMap((id: number) => {
          const token = authStore.token() || '';
          return rentalsService.markAsDelivered(id, token).pipe(
            tapResponse({
              next: () => {
                patchState(store, {
                  success: 'Alquiler iniciado exitosamente',
                  error: null
                });
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, {
                  error: `Error al marcar como entregado: ${error.message}`,
                  success: null
                });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Marcar como devuelto sin daños
    markAsReturnedCorrect: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, success: null })),
        switchMap((id: number) => {
          const token = authStore.token() || '';
          return rentalsService.markAsReturnedCorrect(id, token).pipe(
            tapResponse({
              next: () => {
                patchState(store, {
                  success: 'Alquiler finalizado exitosamente',
                  error: null
                });
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, {
                  error: `Error al finalizar alquiler: ${error.message}`,
                  success: null
                });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Marcar como devuelto con multa
    markAsReturnedWithFine: rxMethod<{ codigoAlquiler: number; multa: number }>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, success: null })),
        switchMap((data) => {
          const token = authStore.token() || '';
          return rentalsService.markAsReturnedWithFine(data, token).pipe(
            tapResponse({
              next: () => {
                patchState(store, {
                  success: 'Alquiler finalizado con multa registrada',
                  error: null
                });
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, {
                  error: `Error al finalizar alquiler con multa: ${error.message}`,
                  success: null
                });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Checkout de alquiler - obtiene URL de pago de MercadoPago
    checkoutAlquiler: rxMethod<CheckOutAlquilerDTO>(
      pipe(
        tap(() => patchState(store, {
          isLoading: true,
          error: null,
          success: null,
        })),
        switchMap((checkoutData: CheckOutAlquilerDTO) => {
          const token = authStore.token() || '';
          return rentalsService.checkoutAlquiler(checkoutData, token).pipe(
            tapResponse({
              next: (paymentUrl: string) => {
                patchState(store, {
                  success: "Redirigiendo al pago....",
                  error: null
                });
                // Redireccionar a MercadoPago
                globalThis.location.href = paymentUrl;
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, {
                  error: `Error al procesar el pago: ${error.error || error.message}`,
                });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          );
        })
      )
    ),

    // Limpiar mensajes de error/éxito
    clearMessages: () => {
      patchState(store, { error: null, success: null });
    },

    // Resetear el store
    reset: () => {
      patchState(store, {
        ...initialState
      });
    }
  })),

  withHooks({
    onInit(store) {
      // Auto-cargar alquileres del cliente si está autenticado
      const authStore = inject(AuthStore);
      if (authStore.isAuthenticated()) {
        store.loadClientRentals();
      }
    }
  })
);
