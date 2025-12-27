import { inject } from "@angular/core";
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals'
import { SucursalData } from "@shared/sucursal/service/sucursal-data";
import {rxMethod} from '@ngrx/signals/rxjs-interop'
import { tapResponse } from '@ngrx/operators';
import {pipe, switchMap, tap} from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";
import { AuthStore } from "@auth-store";

interface SucursalState  {
    error: string | null;
    isLoading: boolean;
    sucursales: string[];
    success: boolean;
};

const initialState: SucursalState = {
    isLoading: false,
    error: null as string | null,
    sucursales: [],
    success: false
};

export const SucursalStore = signalStore(
  { providedIn: 'root' },

  withState({
    ...initialState
  }),
  withMethods((store,sucursalService = inject(SucursalData)) =>({
      loadSucursales: rxMethod<void>(
        pipe(
          tap(() => patchState(store, { isLoading: true, error: null })),
          switchMap(() =>
            sucursalService.getSucursales().pipe(
              tapResponse({
                next: (sucursales: string[]) => {
                  patchState(store, { sucursales });
                },
                error: (error: HttpErrorResponse) => {
                  patchState(store, { error: `Error al cargar las sucursales: ${error.message}` });
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
    createSucursal: rxMethod<string>(
      pipe(
        tap(() => patchState(store, { isLoading: true, error: null, success: false })),
        switchMap((ciudad: string) =>{
          const token = authStore.token() || ''
          return sucursalService.createSucursal(ciudad,token).pipe(
            tapResponse({
              next: () => {
                patchState(store, { success: true });
                store.loadSucursales()
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: `Error al crear la sucursal: ${error.message}`, success: false });
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