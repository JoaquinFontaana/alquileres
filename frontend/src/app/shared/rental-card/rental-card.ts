import { Component, Signal, computed, input, output } from '@angular/core';
import { Rental, RentalCardAction } from '@models';
import { Card } from '@shared/card/card';
import { Button } from '@shared/button/button';
import { DatePipe } from '@angular/common';

interface ActionConfig {
  label: string;
  type: 'primary' | 'secondary' | 'danger' | 'success';
}

@Component({
  selector: 'app-rental-card',
  imports: [Card, Button, DatePipe],
  templateUrl: './rental-card.html',
  styleUrl: './rental-card.scss'
})
export class RentalCard {
  rental = input.required<Rental>();
  actions = input<RentalCardAction[]>([]);
  showClientInfo = input<boolean>(false); // Para empleados/admin
  
  title: Signal<string> = computed(() => {
    return `Reserva #${this.rental().codigoReserva}`;
  });

  estadoLabel: Signal<string> = computed(() => {
    const estado = this.rental().estadoAlquilerEnum;
    
    const labels: Record<string, string> = {
      'CONFIRMACION_PENDIENTE': 'Confirmación Pendiente',
      'RETIRO_PENDIENTE': 'Retiro Pendiente',
      'EN_USO': 'En Uso',
      'FINALIZADO': 'Finalizado',
      'CANCELADO': 'Cancelado'
    };
    
    return labels[estado] || estado;
  });

  estadoClass: Signal<string> = computed(() => {
    const estado = this.rental().estadoAlquilerEnum;

    const classes: Record<string, string> = {
      'CONFIRMACION_PENDIENTE': 'pending',
      'RETIRO_PENDIENTE': 'pickup',
      'EN_USO': 'active',
      'FINALIZADO': 'completed',
      'CANCELADO': 'canceled'
    };
    
    return classes[estado] || 'default';
  });
  
  // Emite la acción cuando se hace clic
  actionClicked = output<RentalCardAction>();
  
  // Configuración de cada acción
  private readonly actionConfigs: Record<RentalCardAction, ActionConfig> = {
    [RentalCardAction.VIEW]: { label: 'Ver Detalles', type: 'secondary' },
    [RentalCardAction.CANCEL]: { label: 'Cancelar', type: 'danger' },
    [RentalCardAction.DELIVER]: { label: 'Entregar', type: 'success' },
    [RentalCardAction.RETURN]: { label: 'Recibir', type: 'success' },
    [RentalCardAction.RETURN_WITH_FINE]: { label: 'Recibir con Multa', type: 'danger' },
    [RentalCardAction.PAY]: { label: 'Pagar', type: 'primary' }
  };
  
  getActionConfig(action: RentalCardAction): ActionConfig {
    return this.actionConfigs[action];
  }
  
  onActionClick(action: RentalCardAction): void {
    this.actionClicked.emit(action);
  }
}
