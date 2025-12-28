import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { CustomErrorStateMatcher } from '@shared/custom-error-state-matcher';
import { routes } from './app.routes';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { ErrorStateMatcher } from '@angular/material/core';
import { APP_BASE_HREF } from '@angular/common';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideHttpClient(withFetch()),
    provideRouter(routes, withComponentInputBinding()), // ✅ Habilita input binding
    {provide: APP_BASE_HREF, useValue:'/'},
    {provide:ErrorStateMatcher, useClass:CustomErrorStateMatcher}
  ]
};
