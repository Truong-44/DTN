import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

export const roleGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  // Get current user from auth service
  const currentUser = auth.currentUser$;

  // For now, return true - should be properly implemented with user role checking
  return true;
};
