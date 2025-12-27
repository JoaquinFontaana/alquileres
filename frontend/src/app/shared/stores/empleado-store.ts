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
    // Primer withMethods: métodos base
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
        createEmpleado: rxMethod<Partial<Empleado>>(
            pipe(
                tap(() => patchState(store, { isLoading: true, error: null, success: null })),
                switchMap((empleado: Partial<Empleado>) => {
                    const token = authStore.token() || '';
                    return empleadoService.createEmpleado(empleado, token).pipe(
                        tapResponse({
                            next: (message: string) => {
                                patchState(store, { 
                                    success: message,
                                    isLoading: false 
                                });
                            },
                            error: (error: HttpErrorResponse) => {
                                patchState(store, { 
                                    error: `Error al crear empleado: ${error.message}`,
                                    isLoading: false 
                                });
                            }
                        })
                    );
                })
            )
        ),
        // Método para obtener un empleado por DNI desde el store (caché)
        getEmpleadoByDni: (dni: string) => {
            return computed(() => store.entityMap()[dni]);
        },
        
    })),
    // Segundo withMethods: métodos que usan los anteriores
    withMethods((store, empleadoService = inject(EmpleadoService), authStore = inject(AuthStore)) => ({
        darDeBajaEmpleado: rxMethod<string>(
            pipe(
                tap(() => patchState(store, { isLoading: true, error: null, success: null })),
                switchMap((dni: string) => {
                    const token = authStore.token() || '';
                    return empleadoService.darDeBajaEmpleado(dni, token).pipe(
                        tapResponse({
                            next: (message: string) => {
                                patchState(store, { 
                                    success: message,
                                    error: null 
                                });
                                // Recargar empleados después de dar de baja
                                store.loadEmpleados();
                            },
                            error: (error: HttpErrorResponse) => {
                                patchState(store, { 
                                    error: `Error al dar de baja empleado: ${error.message}`,
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
    })),
    withHooks({
        onInit(store) {
            store.loadEmpleados();
        },
    })
);
