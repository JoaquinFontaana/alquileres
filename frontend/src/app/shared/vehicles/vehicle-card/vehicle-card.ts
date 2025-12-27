import { Component, Signal, computed, input, output } from '@angular/core';
import { Vehicle, VehicleCardAction } from '@models';
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
  actions = input<VehicleCardAction[]>([]);
  
  title: Signal<string> = computed(() => (`${this.vehicle().modelo}`));
  
  // Emite la acción cuando se hace clic
  actionClicked = output<VehicleCardAction>();
  
  // Configuración de cada acción
  private readonly actionConfigs: Record<VehicleCardAction, ActionConfig> = {
    [VehicleCardAction.RENT]: { label: 'Alquilar', type: 'primary' },
    [VehicleCardAction.EDIT]: { label: 'Modificar', type: 'secondary' },
    [VehicleCardAction.DELETE]: { label: 'Eliminar', type: 'danger' },
    [VehicleCardAction.VIEW]: { label: 'Ver Detalles', type: 'secondary' }
  };
  
  getActionConfig(action: VehicleCardAction): ActionConfig {
    console.log(this.vehicle())
    return this.actionConfigs[action];
  }
  
  onActionClick(action: VehicleCardAction): void {
    this.actionClicked.emit(action);
  }
}
