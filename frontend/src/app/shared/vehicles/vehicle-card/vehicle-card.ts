import { Component, Signal, computed, input, output } from '@angular/core';
import { Vehicle, CardAction } from '@models';
import { Card } from '@shared/card/card';
import { Button } from '@shared/button/button';

interface ActionConfig {
  label: string;
  type: 'primary' | 'secondary' | 'danger';
}

@Component({
  selector: 'app-vehicle-card',
  imports: [Card, Button],
  templateUrl: './vehicle-card.html',
  styleUrl: './vehicle-card.scss'
})
export class VehicleCard {
  vehicle = input.required<Vehicle>();
  actions = input<CardAction[]>([]);
  
  title: Signal<string> = computed(() => (`${this.vehicle().modelo}`));
  
  // Emite la acción cuando se hace clic
  actionClicked = output<CardAction>();
  
  // Configuración de cada acción
  private readonly actionConfigs: Record<CardAction, ActionConfig> = {
    [CardAction.RENT]: { label: 'Alquilar', type: 'primary' },
    [CardAction.EDIT]: { label: 'Modificar', type: 'secondary' },
    [CardAction.DELETE]: { label: 'Eliminar', type: 'danger' },
    [CardAction.VIEW]: { label: 'Ver Detalles', type: 'secondary' },
    [CardAction.CANCEL]: { label: 'Cancelar', type: 'danger' }
  };
  
  getActionConfig(action: CardAction): ActionConfig {
    return this.actionConfigs[action];
  }
  
  onActionClick(action: CardAction): void {
    this.actionClicked.emit(action);
  }
}
