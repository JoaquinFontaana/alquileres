import { Component, computed, inject, signal, Signal, viewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Vehicle,CardAction  } from '@models';
import { VehiclesStore } from '@vehicles/vehicles-store'
import { VehicleCard } from '@vehicles/vehicle-card/vehicle-card';
import { VehicleFilter } from '@vehicles/vehicle-filter/vehicle-filter';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'app-vehicle-list',
  imports: [VehicleCard, VehicleFilter,MatPaginatorModule],
  templateUrl: './vehicle-list.html',
  styleUrl: './vehicle-list.scss'
})
export class VehicleList {
  private readonly store = inject(VehiclesStore);
  private readonly router = inject(Router);
  
  // Obtener referencia al componente VehicleFilter
  readonly filterComponent = viewChild.required(VehicleFilter);
  
  private readonly vehicles:Signal<Vehicle[]> = this.store.entities
  readonly actionsList: CardAction[] = [CardAction.RENT];
  readonly pageIndex = signal(0);
  readonly pageSize = signal(20);

  handleAction(action: CardAction, vehicle: Vehicle) {
    if (action === CardAction.RENT) {
      this.rentVehicle(vehicle);
    }
  }

  rentVehicle(vehicle: Vehicle) {
    const filter = this.filterComponent();
    const fechaInicio = filter.fechaInicio();
    const fechaFin = filter.fechaFin();
    
    // Validar que las fechas estén seleccionadas
    if (!fechaInicio || !fechaFin) {
      alert('Por favor, selecciona las fechas de alquiler primero');
      return;
    }
    
    this.router.navigate(['/cliente/rent-vehicle', vehicle.id], {
      queryParams: {
        fechaInicio,
        fechaFin
      }
    });
  }

  private readonly start: Signal<number> = computed(() => this.pageIndex() * this.pageSize())
  readonly pagedVehicles: Signal<Vehicle[]> = computed(() => this.vehicles().slice(this.start(), this.start() + this.pageSize()))
  
  onPageChange(event: PageEvent) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
  }
}
