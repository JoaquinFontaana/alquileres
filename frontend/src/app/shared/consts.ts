import { HttpErrorResponse, HttpHeaders } from "@angular/common/http";
export const TOKEN_KEY = 'auth_token'
export const baseUrlApi = 'http://localhost:8080'

export const addToken = (token: string): HttpHeaders => {
  return new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
} 

/**
 * Extrae el mensaje de error de un HttpErrorResponse.
 * Maneja tanto ResponseEntity<String> como errores genéricos.
 */
export function getErrorMessage(error: HttpErrorResponse, fallbackMessage: string = 'Error desconocido'): string {
  // Si el backend devuelve un string directo (ResponseEntity<String>)
  if (typeof error.error === 'string' && error.error.trim()) {
    return error.error;
  }
  
  // Si el backend devuelve un objeto con mensaje
  if (error.error?.message) {
    return error.error.message;
  }
  
  // Si hay un statusText descriptivo
  if (error.statusText && error.statusText !== 'Unknown Error') {
    return error.statusText;
  }
  
  // Mensaje fallback
  return fallbackMessage;
} 