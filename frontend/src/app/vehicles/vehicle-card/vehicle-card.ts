import { Component, Signal, computed, input } from '@angular/core';
import { Vehicle } from '../../models/index';
import { Card } from '@shared/card/card';
@Component({
  selector: 'app-vehicle-card',
  imports: [Card],
  templateUrl: './vehicle-card.html',
  styleUrl: './vehicle-card.scss'
})
export class VehicleCard {
  vehicle = input.required<Vehicle>()
  title:Signal<string> = computed(() => (`${this.vehicle().modelo}`));
  
}
