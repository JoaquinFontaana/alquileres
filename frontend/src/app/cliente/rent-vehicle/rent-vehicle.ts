import { Component, input, computed, effect, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RangoFecha, CheckOutAlquilerDTO, Extra } from '@models';
import { VehiclesStore } from '@shared/stores/vehicles-store';
import { Button } from '@shared/button/button';
import { VehicleCard } from '@shared/vehicles/vehicle-card/vehicle-card';
import { RentalsData } from '@shared/rentals/services/rentals-data';
import { AuthStore } from '@auth-store';

// Precios de los extras (deben coincidir con el backend)
const EXTRA_PRICES: Record<Extra, number> = {
  [Extra.SEGURO]: 3500,
  [Extra.CADENA_NIEVE]: 2500,
  [Extra.SILLA_BEBE]: 1500,
  [Extra.COMBUSTIBLE_COMPLETO]: 5000
};

// Labels legibles para los extras
const EXTRA_LABELS: Record<Extra, string> = {
  [Extra.SEGURO]: 'Seguro',
  [Extra.CADENA_NIEVE]: 'Cadena de Nieve',
  [Extra.SILLA_BEBE]: 'Silla de Bebé',
  [Extra.COMBUSTIBLE_COMPLETO]: 'Combustible Completo'
};

@Component({
  selector: 'app-rent-vehicle',
  imports: [Button, VehicleCard, FormsModule],
  templateUrl: './rent-vehicle.html',
  styleUrl: './rent-vehicle.scss'
})
export class RentVehicle {
  private readonly vehiclesStore = inject(VehiclesStore);
  private readonly router = inject(Router);
  private readonly rentalsData = inject(RentalsData);
  private readonly authStore = inject(AuthStore);
  
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
  
  // Signal para controlar el estado de carga del pago
  isProcessingPayment = signal(false);
  
  // Signals para datos del formulario
  licenciaConductor = signal('');
  selectedExtras = signal<Extra[]>([]);
  
  // Lista de extras disponibles para el template
  readonly availableExtras = Object.values(Extra);
  
  // Método para obtener el label de un extra
  getExtraLabel(extra: Extra): string {
    return EXTRA_LABELS[extra];
  }
  
  // Método para obtener el precio de un extra
  getExtraPrice(extra: Extra): number {
    return EXTRA_PRICES[extra];
  }
  
  // Método para verificar si un extra está seleccionado
  isExtraSelected(extra: Extra): boolean {
    return this.selectedExtras().includes(extra);
  }
  
  // Método para toggle de un extra
  toggleExtra(extra: Extra): void {
    const current = this.selectedExtras();
    if (current.includes(extra)) {
      this.selectedExtras.set(current.filter(e => e !== extra));
    } else {
      this.selectedExtras.set([...current, extra]);
    }
  }
  
  // Computed para calcular el precio de los extras
  readonly precioExtras = computed(() => {
    return this.selectedExtras().reduce((total, extra) => total + EXTRA_PRICES[extra], 0);
  });
  
  // Computed para calcular el precio base del alquiler
  readonly precioBaseAlquiler = computed(() => {
    const rango = this.rangoFechas();
    const vehiculo = this.vehicle();
    return rango.cantidadDias() * vehiculo().precioPorDia;
  });
  
  // Computed para calcular el precio total (alquiler + extras)
  readonly precioTotal = computed(() => {
    return this.precioBaseAlquiler() + this.precioExtras();
  });
  
  // Computed para validar el formulario
  readonly isFormValid = computed(() => {
    return this.licenciaConductor().trim().length > 0 && this.disponible();
  });
  
  goBack(): void {
    this.router.navigate(['/vehicle-list']);
  }
  
  proceedToPayment(): void {
    if (!this.disponible() || this.isProcessingPayment()) {
      return;
    }
    
    const vehicle = this.vehicle();
    const token = this.authStore.token();
    
    if (!token || !vehicle()) {
      console.error('No hay token de autenticación o vehículo');
      return;
    }
    
    // Formatear fechas a ISO (YYYY-MM-DD)
    const fechaInicio = this.fechaInicio();
    const fechaFin = this.fechaFin();
    
    const checkoutData: CheckOutAlquilerDTO = {
      alquilerDTO: {
        rangoFecha: {
          fechaDesde: fechaInicio,
          fechaHasta: fechaFin
        },
        licenciaConductor: this.licenciaConductor(),
        patenteAuto: vehicle().patente,
        sucursal: vehicle().sucursal,
        extras: this.selectedExtras()
      },
      datosPagoDTO: {
        titulo: `Alquiler ${vehicle().marca} ${vehicle().modelo}`,
        successUrl: `${window.location.origin}/cliente/payment-success`,
        failureUrl: `${window.location.origin}/cliente/payment-failure`,
        pendingUrl: `${window.location.origin}/cliente/payment-pending`
      }
    };
    
    this.isProcessingPayment.set(true);
    
    this.rentalsData.checkoutAlquiler(checkoutData, token).subscribe({
      next: (paymentUrl: string) => {
        // Redireccionar a MercadoPago
        window.location.href = paymentUrl;
      },
      error: (error) => {
        console.error('Error al procesar el pago:', error);
        this.isProcessingPayment.set(false);
      }
    });
  }
}
