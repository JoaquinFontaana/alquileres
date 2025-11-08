import { Component, computed, inject, signal, Signal } from '@angular/core';
import { Vehicle } from '@models';
import { VehiclesStore } from '@vehicles/vehicles-store'
import { VehicleCard } from '@vehicles/vehicle-card/vehicle-card';
import { VehicleFilter } from '@vehicles/vehicle-filter/vehicle-filter';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { CardAction } from '@models';

@Component({
  selector: 'app-vehicle-list',
  imports: [VehicleCard, VehicleFilter,MatPaginatorModule],
  templateUrl: './vehicle-list.html',
  styleUrl: './vehicle-list.scss'
})
export class VehicleList {
  private readonly store = inject(VehiclesStore)
  private readonly vehicles:Signal<Vehicle[]> = this.store.entities
  readonly actionsList: CardAction[] = [CardAction.RENT];
  readonly pageIndex = signal(0);
  readonly pageSize = signal(20);

  rentVehicle(vehicle: Vehicle) {

    console.log('Renting vehicle:', vehicle);
  }
  private readonly start: Signal<number> = computed(() => this.pageIndex() * this.pageSize())
  readonly pagedVehicles: Signal<Vehicle[]> = computed(() => this.vehicles().slice(this.start(), this.start() + this.pageSize()))
  
  onPageChange(event: PageEvent) {
    this.pageIndex.set(event.pageIndex);
    this.pageSize.set(event.pageSize);
  }
}
