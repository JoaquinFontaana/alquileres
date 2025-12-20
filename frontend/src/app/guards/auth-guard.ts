import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStore } from '@auth-store';

export const authGuard: CanActivateFn = (route, state) => {
  const authStore = inject(AuthStore);
  const router = inject(Router);
  
  if (authStore.isAuthenticated()) {
    return true;
  }
  
  // Redirigir al login si no está autenticado
  return router.createUrlTree(['/login']);
};
