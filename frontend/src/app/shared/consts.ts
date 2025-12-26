import { HttpHeaders } from "@angular/common/http";
export const baseUrlApi = 'http://localhost:8080';
export const TOKEN_KEY = 'auth_token'

export const addToken = (token: string): HttpHeaders => {
  return new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
} 