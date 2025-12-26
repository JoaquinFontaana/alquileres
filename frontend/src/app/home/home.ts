import { Component, inject, computed } from '@angular/core';
import { RouterLink } from '@angular/router';
import { VehicleCarousel } from './vehicle-carousel/vehicle-carousel';
import { VehiclesStore } from '@shared/stores/vehicles-store';

@Component({
  selector: 'app-home',
  imports: [RouterLink, VehicleCarousel],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {
  private readonly vehiclesStore = inject(VehiclesStore);
  
  // Computed signals para estado derivado reactivo
  protected readonly featuredVehicles = computed(() => {
    const allVehicles = this.vehiclesStore.entities();
    return allVehicles.slice(0, 6);
  });

  protected readonly isLoading = computed(() => this.vehiclesStore.isLoading());
  protected readonly error = computed(() => this.vehiclesStore.error());
}
