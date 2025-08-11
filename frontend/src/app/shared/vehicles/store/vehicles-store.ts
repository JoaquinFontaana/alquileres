import { inject } from "@angular/core";
import { Vehicle, VehicleFilter } from "@models"
import { patchState, signalStore, type, withHooks, withMethods, withState } from '@ngrx/signals'
import { entityConfig, setAllEntities, withEntities } from '@ngrx/signals/entities'
import {rxMethod} from '@ngrx/signals/rxjs-interop'
import { tapResponse } from '@ngrx/operators';
import { VehiclesData } from "../services/vehicles-data";
import {pipe, switchMap, tap} from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";

interface VehiclesState  {
    error: string | null;
    isLoading: boolean;
};

const initialState: VehiclesState = {
    isLoading: false,
    error: null as string | null,
};

const vehicleConfig = entityConfig({
    entity: type<Vehicle>(),
    selectId: (vehicle: Vehicle) => vehicle.patente
})

export const VehiclesStore = signalStore(
  { providedIn: 'root' },

  // 2. Usa `withEntities` para gestionar la colección. Esto es mejor que un array simple.
  withEntities<Vehicle>(vehicleConfig),
  withState({
    ...initialState
  }),
  withMethods((store, vehiclesService = inject(VehiclesData)) => ({
    
    loadVehicles: rxMethod<VehicleFilter | undefined>(
      pipe(
        //Tap es la funcion que se ejecuta luego de que se ejecute el metodo, produciendo un efecto secundario
        tap(() => patchState(store, { isLoading: true, error: null })),
        // Reciibe el parametro de la funcion y ejecuta un callback que llama al service
        switchMap((filters) =>
          vehiclesService.getVehicles(filters).pipe(
            //En base al obersvable que retorna el service, ejecuta una pipe
            tapResponse({
              //Se ejecuta en caso de éxito.
              next: (vehicles:Vehicle[]) => {
                patchState(store, setAllEntities(vehicles, vehicleConfig));
              },
              error: (error: HttpErrorResponse) => {
                patchState(store, { error: `Error al cargar vehículos: ${error.message}` });
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
      store.loadVehicles(undefined);
    },
  })
);