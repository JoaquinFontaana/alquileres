import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from '@shared/header/header';
import { VehicleList } from '@shared/vehicle-list/vehicle-list';

@Component({
  selector: 'app-root',
  standalone:true,
  imports: [RouterOutlet,VehicleList,Header],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('alquileres-maria-frontend');
}
