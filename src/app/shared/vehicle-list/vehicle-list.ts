import { Component, inject, Signal } from '@angular/core';
import { Vehicle } from '@models';
import { VehiclesStore } from '@vehicles/store/vehicles-store';
import { VehicleCard } from '@vehicles/vehicle-card/vehicle-card';
@Component({
  selector: 'app-vehicle-list',
  imports: [VehicleCard],
  templateUrl: './vehicle-list.html',
  styleUrl: './vehicle-list.scss'
})
export class VehicleList {
  protected store = inject(VehiclesStore)
  protected vehicles:Signal<Vehicle[]> = this.store.entities
}
