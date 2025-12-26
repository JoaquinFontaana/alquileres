import { inject, computed } from "@angular/core";
import { Empleado } from "@models"
import { patchState, signalStore, type, withHooks, withMethods, withState } from '@ngrx/signals'
import { entityConfig, setAllEntities, withEntities } from '@ngrx/signals/entities'
import { rxMethod } from '@ngrx/signals/rxjs-interop'
import { tapResponse } from '@ngrx/operators';
import { EmpleadoService } from "../../admin/empleados/empleado-service";
import { pipe, switchMap, tap } from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";
import { AuthStore } from "@auth-store";

interface EmpleadoState {
    error: string | null;
    success: string | null;
    isLoading: boolean;
};

const initialState: EmpleadoState = {
    isLoading: false,
    error: null as string | null,
    success: null as string | null,
};

const empleadoConfig = entityConfig({
    entity: type<Empleado>(),
    selectId: (empleado: Empleado) => empleado.dni
})

export const EmpleadoStore = signalStore(
    { providedIn: 'root' },
    
    withEntities<Empleado>(empleadoConfig),
    withState({
        ...initialState
    }),
    withMethods((store, empleadoService = inject(EmpleadoService), authStore = inject(AuthStore)) => ({
        
        loadEmpleados: rxMethod<void>(
            pipe(
                tap(() => patchState(store, { isLoading: true, error: null })),
                switchMap(() => {
                    const token = authStore.token() || '';
                    return empleadoService.getEmpleados(token).pipe(
                        tapResponse({
                            next: (empleados: Empleado[]) => {
                                patchState(store, setAllEntities(empleados, empleadoConfig));
                            },
                            error: (error: HttpErrorResponse) => {
                                patchState(store, { error: `Error al cargar empleados: ${error.message}` });
                            },
                            finalize: () => {
                                patchState(store, { isLoading: false });
                            }
                        })
                    )
                })
            )
        ),
        
        // Método para obtener un empleado por DNI desde el store (caché)
        getEmpleadoByDni: (dni: string) => {
            return computed(() => store.entityMap()[dni]);
        },
        
    })),
    withHooks({
        onInit(store) {
            store.loadEmpleados();
        },
    })
);
