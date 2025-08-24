import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { LoadingService } from '../services/loading.service';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);

  // Chỉ show loading cho các request không phải background
  if (!req.headers.has('X-Skip-Loading')) {
    loadingService.show();
  }

  return next(req).pipe(
    finalize(() => {
      if (!req.headers.has('X-Skip-Loading')) {
        loadingService.hide();
      }
    })
  );
};
