import { HttpHeaders } from "@angular/common/http";
export const TOKEN_KEY = 'auth_token'

export const addToken = (token: string): HttpHeaders => {
  return new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
} 