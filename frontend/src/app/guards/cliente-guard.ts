import { inject } from '@angular/core';
import { CanActivateChildFn } from '@angular/router';
import { AuthStore } from '@auth-store';

export const clienteGuard: CanActivateChildFn = (childRoute, state) => {
  const authStore = inject(AuthStore)
  return authStore.hasRole("CLIENT")
};
