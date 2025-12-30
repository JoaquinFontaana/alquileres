import { Component, computed, inject, Signal, viewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Vehicle, VehicleCardAction } from '@models';
import { VehiclesStore } from '@shared/stores/vehicles-store';
import { VehicleCard } from '@vehicles/vehicle-card/vehicle-card';
import { VehicleFilter } from '@vehicles/vehicle-filter/vehicle-filter';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthStore } from '@auth-store';
import { Button } from '@shared/button/button';

@Component({
  selector: 'app-vehicle-list',
  imports: [VehicleCard, VehicleFilter, MatPaginatorModule, MatButtonModule, MatIconModule,Button],
  templateUrl: './vehicle-list.html',
  styleUrl: './vehicle-list.scss'
})
export class VehicleList {
  private readonly vehicleStore = inject(VehiclesStore);
  private readonly router = inject(Router);
  private readonly authStore = inject(AuthStore);
  
  // Obtener referencia al componente VehicleFilter
  readonly filterComponent = viewChild.required(VehicleFilter);
  
  readonly vehicles: Signal<Vehicle[]> = this.vehicleStore.entities;
  
  // Determinar si el usuario es admin
  readonly isAdmin = computed(() => this.authStore.hasRole('ADMIN'));
  
  // Acciones dinámicas basadas en el rol
  readonly actionsList = computed(() => 
    this.isAdmin() 
      ? [VehicleCardAction.EDIT, VehicleCardAction.DELETE] 
      : [VehicleCardAction.RENT]
  );

  handleAction(action: VehicleCardAction, vehicle: Vehicle) {
    switch (action) {
      case VehicleCardAction.RENT:
        this.rentVehicle(vehicle);
        break;
      case VehicleCardAction.EDIT:
        this.editVehicle(vehicle);
        break;
      case VehicleCardAction.DELETE:
        this.deleteVehicle(vehicle);
        break;
    }
  }

  rentVehicle(vehicle: Vehicle) {
    // Validar que las fechas estén seleccionadas
    if (!this.filterComponent().fechaInicio() || !this.filterComponent().fechaFin()) {
      alert('Por favor, selecciona las fechas de alquiler primero');
      return;
    }
    
    this.router.navigate(['/cliente/rent-vehicle', vehicle.id], {
      queryParams: {
        fechaInicio: this.filterComponent().fechaInicio(),
        fechaFin: this.filterComponent().fechaFin()
      }
    });
  }
  
  editVehicle(vehicle: Vehicle) {
    this.router.navigate(['/admin/vehicles/update', vehicle.id]);
  }
  
  deleteVehicle(vehicle: Vehicle) {
    if (confirm(`¿Estás seguro de que quieres eliminar el vehículo ${vehicle.marca} ${vehicle.modelo} (${vehicle.patente})?`)) {
      // Aquí se implementaría la lógica para eliminar el vehículo
      // Por ejemplo: this.store.deleteVehicle(vehicle.id);
      console.log('Eliminar vehículo:', vehicle);
    }
  }
  
  createVehicle() {
    this.router.navigate(['/admin/vehicles/create']);
  }
  
}
