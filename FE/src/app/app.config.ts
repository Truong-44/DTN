import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations'; // Thêm import này
import { routes } from './app.routes';
import { HeaderComponent } from './shared/components/header/header.component';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // provideAnimations() {
    //   // return { provide: HeaderComponent, useValue: HeaderComponent }
    // }
  ]
};
