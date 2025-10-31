import { Component, inject } from '@angular/core';
import { AuthStore } from '@auth-store';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  readonly authStore = inject(AuthStore)
}
