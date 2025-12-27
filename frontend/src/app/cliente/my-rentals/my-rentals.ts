import { Component, inject, computed, signal, OnInit } from '@angular/core';
import { RentalsStore } from '@rentals-store';
import { RentalCard } from '@shared/rental-card/rental-card';
import { RentalCardAction } from '@models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-rentals',
  imports: [RentalCard],
  templateUrl: './my-rentals.html',
  styleUrl: './my-rentals.scss'
})
export class MyRentals implements OnInit {
  private readonly store = inject(RentalsStore);
  private readonly router = inject(Router);

  // Estado para filtros
  readonly selectedFilter = signal<'all' | 'active' | 'pending' | 'completed' | 'canceled'>('all');

  // Selectores del store
  readonly isLoading = this.store.isLoading;
  readonly error = this.store.error;
  readonly success = this.store.success;

  // Computed para alquileres filtrados
  readonly filteredRentals = computed(() => {
    const filter = this.selectedFilter();
    
    switch(filter) {
      case 'active':
        return this.store.activeRentals();
      case 'pending':
        return this.store.pendingRentals();
      case 'completed':
        return this.store.completedRentals();
      case 'canceled':
        return this.store.canceledRentals();
      default:
        return this.store.entities();
    }
  });

  // Contadores para los filtros
  readonly totalCount = computed(() => this.store.entities().length);
  readonly activeCount = computed(() => this.store.activeRentals().length);
  readonly pendingCount = computed(() => this.store.pendingRentals().length);
  readonly completedCount = computed(() => this.store.completedRentals().length);
  readonly canceledCount = computed(() => this.store.canceledRentals().length);

  ngOnInit(): void {
    // Cargar alquileres del cliente al iniciar
    this.store.loadClientRentals();
  }

  setFilter(filter: 'all' | 'active' | 'pending' | 'completed' | 'canceled'): void {
    this.selectedFilter.set(filter);
  }

  // Determinar acciones disponibles según el estado del alquiler
  getActionsForRental(estado: string): RentalCardAction[] {
    switch(estado) {
      case 'CONFIRMACION_PENDIENTE':
        return [RentalCardAction.PAY, RentalCardAction.CANCEL, RentalCardAction.VIEW];
      case 'RETIRO_PENDIENTE':
        return [RentalCardAction.VIEW, RentalCardAction.CANCEL];
      case 'EN_USO':
        return [RentalCardAction.VIEW];
      case 'FINALIZADO':
        return [RentalCardAction.VIEW];
      case 'CANCELADO':
        return [RentalCardAction.VIEW];
      default:
        return [RentalCardAction.VIEW];
    }
  }

  handleAction(action: RentalCardAction, rentalId: number): void {
    switch(action) {
      case RentalCardAction.CANCEL:
        this.cancelRental(rentalId);
        break;
      case RentalCardAction.PAY:
        this.payRental(rentalId);
        break;
      case RentalCardAction.VIEW:
        this.viewRentalDetails(rentalId);
        break;
    }
  }

  cancelRental(id: number): void {
    if (confirm('¿Estás seguro de que deseas cancelar esta reserva?')) {
      this.store.cancelRental(id);
    }
  }

  payRental(id: number): void {
    const rental = this.store.entities().find(r => r.codigoReserva === id);
    if (rental?.urlPago) {
      // Redirigir a la URL de pago de MercadoPago
      window.location.href = rental.urlPago;
    } else {
      console.error('URL de pago no disponible');
    }
  }

  viewRentalDetails(id: number): void {
    // TODO: Navegar a vista de detalles
    console.log('Ver detalles del alquiler:', id);
  }

  dismissMessage(): void {
    this.store.clearMessages();
  }
}
