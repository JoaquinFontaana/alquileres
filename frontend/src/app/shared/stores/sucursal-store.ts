import { inject, computed} from "@angular/core";
import { patchState, signalStore, withHooks, withMethods, withState, type, withComputed } from '@ngrx/signals'
import { SucursalData } from "@shared/sucursal/service/sucursal-data";
import {rxMethod} from '@ngrx/signals/rxjs-interop'
import { tapResponse } from '@ngrx/operators';
import {pipe, switchMap, tap} from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";
import { AuthStore } from "@auth-store";
import { Sucursal } from "@models";
import { withEntities, entityConfig} from "@ngrx/signals/entities";
import { getErrorMessage } from "@shared/consts";

interface SucursalState  {
    error: string | null;
    isLoading: boolean;
    sucursales: Sucursal[];
    success: boolean;
};

const initialState: SucursalState = {
    isLoading: false,
    error: null as string | null,
    sucursales: [],
    success: false
};

const sucursalEntity = entityConfig({
    entity: type<Sucursal>(),
    selectId: (sucursal: Sucursal) => sucursal.ciudad
})

export const SucursalStore = signalStore(
  { providedIn: 'root' },

  withState({
    ...initialState
  }),
  withEntities<Sucursal>(sucursalEntity),
  withComputed((store) =>({
    listaCiudades: computed(() => {
            // Mapeamos el array de sucursales para obtener solo los nombres
            const ciudades = store.sucursales().map(s => s.ciudad);
            return ciudades;
          })
  })),
  withMethods((store,sucursalService = inject(SucursalData)) =>({
      loadSucursales: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true, error: null })),
          switchMap(() =>
            sucursalService.getSucursales().pipe(
              tapResponse({
                next: (sucursales: Sucursal[]) => {
                  patchState(store, { sucursales });
                },
                error: (error: HttpErrorResponse) => {
                  patchState(store, { error: getErrorMessage(error, 'Error al cargar las sucursales') });
                },
                finalize: () => {
                  patchState(store, { isLoading: false });
                }
              })
            )
          )
        )
      ),
  })),
  withMethods((store, sucursalService = inject(SucursalData), authStore = inject(AuthStore)) => ({
    createSucursal: rxMethod<Sucursal>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, success: false })),
        switchMap((sucursal: Sucursal) =>{
          const token = authStore.token() || ''
          return sucursalService.createSucursal(sucursal,token).pipe(
            tapResponse({
              next: () => {
                patchState(store, { success: true });
                store.loadSucursales()
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: getErrorMessage(error, 'Error al crear la sucursal'), success: false });
              },
              finalize: () => {
                patchState(store, { isLoading: false });
              }
            })
          )
  })
      )
    )
    
  })),
  //withHooks para manejar el ciclo de vida del store.
  withHooks({
    onInit(store) {
      store.loadSucursales();
    },
  })
);