import { inject } from "@angular/core";
import {}  from "@models"
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals'
import {SucursalData} from "./service/sucursal-data"
import {rxMethod} from '@ngrx/signals/rxjs-interop'
import { tapResponse } from '@ngrx/operators';
import {pipe, switchMap, tap} from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";

interface SucursalState  {
    error: string | null;
    isLoading: boolean;
    sucursales: string[];
};

const initialState: SucursalState = {
    isLoading: false,
    error: null as string | null,
    sucursales: []
};

export const SucursalStore = signalStore(
  { providedIn: 'root' },

  withState({
    ...initialState
  }),
  withMethods((store, sucursalService = inject(SucursalData)) => ({
    
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
  //withHooks para manejar el ciclo de vida del store.
  withHooks({
    onInit(store) {
      store.loadSucursales();
    },
  })
);