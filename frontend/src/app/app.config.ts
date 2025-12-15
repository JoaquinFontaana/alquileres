import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { CustomErrorStateMatcher } from '@shared/custom-error-state-matcher';
import { routes } from './app.routes';
import { provideClientHydration, withEventReplay, withIncrementalHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { ErrorStateMatcher } from '@angular/material/core';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideHttpClient(withFetch()),
    provideRouter(routes, withComponentInputBinding()), // ✅ Habilita input binding
    provideClientHydration(withEventReplay(), withIncrementalHydration()),
    {provide:ErrorStateMatcher, useClass:CustomErrorStateMatcher}
  ]
};
