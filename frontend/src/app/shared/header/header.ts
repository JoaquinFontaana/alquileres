import { Component, inject } from '@angular/core';
import { AuthStore } from '@auth-store';
import { Router } from '@angular/router';
import { Button } from '@shared/button/button';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-header',
  imports: [Button,RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  readonly authStore = inject(AuthStore)
  private readonly router = inject(Router)

  toLogin(): void {
    this.router.navigate(['/login'])
  }

  logout(): void {
    this.authStore.logout();
    this.router.navigate(['/']);
  }

  toHome(): void {
    this.router.navigate(['/'])
  }

  toMyRentals(): void {
    this.router.navigate(['/cliente/my-rentals'])
  }
}
