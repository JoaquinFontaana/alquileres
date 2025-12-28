import { HttpHeaders } from "@angular/common/http";
export const baseUrlApi = 'http://localhost:8080';
export const TOKEN_KEY = 'auth_token'

// URL base para redirecciones de pago (MercadoPago no acepta localhost)
// En desarrollo, usar ngrok o similar. En producción, usar el dominio real.
export const paymentRedirectBaseUrl = 'https://tu-dominio-ngrok.ngrok-free.app';

export const addToken = (token: string): HttpHeaders => {
  return new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
} 