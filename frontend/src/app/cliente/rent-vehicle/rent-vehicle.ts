import { Component, input, computed, effect, inject } from '@angular/core';
import { Router } from '@angular/router';
import { RangoFecha } from '@models';
import { VehiclesStore } from '@shared/vehicles/vehicles-store';
import { Button } from '@shared/button/button';
import { VehicleCard } from '@shared/vehicles/vehicle-card/vehicle-card';

@Component({
  selector: 'app-rent-vehicle',
  imports: [Button, VehicleCard],
  templateUrl: './rent-vehicle.html',
  styleUrl: './rent-vehicle.scss'
})
export class RentVehicle {
  private readonly vehiclesStore = inject(VehiclesStore);
  private readonly router = inject(Router);
  
  // Route param con transformación automática a number
  readonly id = input.required<number, string>({
    transform: Number
  });
  
  // Query params se mapean automáticamente
  readonly fechaInicio = input.required<string>();
  readonly fechaFin = input.required<string>();
  
  // Computed para crear el objeto RangoFecha
  readonly rangoFechas = computed(() => {
    const inicio = this.fechaInicio();
    const fin = this.fechaFin();
    
    return new RangoFecha(new Date(inicio), new Date(fin));
  });
  
  // Effect para consultar disponibilidad cuando cambian los inputs
  constructor() {
    effect(() => {
      const id = this.id();
      const rango = this.rangoFechas();
      
      if (id && rango) {
        this.vehiclesStore.checkDisponibilidad({ id, rangoFecha:rango });
      }
    });
  }
  
  // Obtiene el vehículo del store (ya cargado del listado previo)
  readonly vehicle = computed(() => {
    const id = this.id();
    return this.vehiclesStore.getVehicleById(id)
  });
  
  disponible = computed(() => this.vehiclesStore.disponibilidad());
  
  // Computed para calcular el precio
  readonly precioTotal = computed(() => {
    const rango = this.rangoFechas();
    const vehiculo = this.vehicle();
    
    return rango.cantidadDias() * vehiculo().precioPorDia;
  });
  
  goBack(): void {
    this.router.navigate(['/cliente/vehicle-list']);
  }
  
  proceedToPayment(): void {
    if (!this.disponible()) {
      return;
    }
    
    // TODO: Implementar navegación a página de pago
    console.log('Proceder al pago:', {
      vehiculoId: this.id(),
      fechaInicio: this.fechaInicio(),
      fechaFin: this.fechaFin(),
      precioTotal: this.precioTotal()
    });
  }
}
