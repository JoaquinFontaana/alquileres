import { Component, inject } from '@angular/core';
import { AuthStore } from '@auth-store';
@Component({
  selector: 'app-sidenav',
  imports: [],
  templateUrl: './sidenav.html',
  styleUrl: './sidenav.scss'
})
export class Sidenav {
  readonly authStore = inject(AuthStore)
}
