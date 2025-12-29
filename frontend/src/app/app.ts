import { Component, signal,inject, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from '@shared/header/header';
import { MatSidenav, MatSidenavModule} from '@angular/material/sidenav'
import { Sidenav } from '@shared/sidenav/sidenav';
import { AuthStore } from '@auth-store';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Footer } from '@shared/footer/footer';
@Component({
  selector: 'app-root',
  standalone:true,
  imports: [RouterOutlet,Header,MatSidenavModule,Sidenav,MatIconModule,MatButtonModule,Footer],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('alquileres-maria-frontend');
  readonly authStore = inject(AuthStore);
  sidenavOpened = signal(false);
  
  @ViewChild('sidenav') sidenav!: MatSidenav;

  toggleSidenav(): void {
    if (this.sidenav) {
      this.sidenav.toggle();
    }
  }

  onSidenavChange(opened: boolean): void {
    this.sidenavOpened.set(opened);
  }
}
