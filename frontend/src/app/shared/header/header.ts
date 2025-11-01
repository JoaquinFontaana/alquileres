import { Component, inject } from '@angular/core';
import { AuthStore } from '@auth-store';
import { Router, RouterLink } from '@angular/router';
import { Button } from '@shared/button/button';

@Component({
  selector: 'app-header',
  imports: [Button, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  readonly authStore = inject(AuthStore)
  private readonly router = inject(Router)

  toLogin(): void {
    this.router.navigate(['/login'])
  }
}
