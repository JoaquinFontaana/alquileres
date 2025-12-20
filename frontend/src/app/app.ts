import { Component, signal,inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from '@shared/header/header';
import {MatSidenavModule} from '@angular/material/sidenav'
import { Sidenav } from '@shared/sidenav/sidenav';
import { AuthStore } from '@auth-store';
@Component({
  selector: 'app-root',
  standalone:true,
  imports: [RouterOutlet,Header,MatSidenavModule,Sidenav],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('alquileres-maria-frontend');
  readonly authStore = inject(AuthStore);
}
