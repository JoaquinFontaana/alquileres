import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthStore } from '@auth-store';

export const authGuard: CanActivateFn = (route, state) => {
  const authStore = inject(AuthStore);
  console.log(authStore.isTokenValid())
  return authStore.isAuthenticated() && authStore.isTokenValid()
};
