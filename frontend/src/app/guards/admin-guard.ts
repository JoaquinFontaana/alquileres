import { inject } from '@angular/core';
import { CanActivateChildFn, Router } from '@angular/router';
import { AuthStore } from '@auth-store';

export const adminGuard: CanActivateChildFn = (childRoute, state) => {
  const authStore = inject(AuthStore);
  const router = inject(Router);
  
  if (authStore.hasRole('ADMIN')) {
    return true;
  }
  
  router.navigate(['/login']);
  return false;
};
