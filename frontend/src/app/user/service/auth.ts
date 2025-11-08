import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { baseUrlApi } from '@shared/consts';
import { LoginRequest, LoginResponse } from '@models';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly httpClient = inject(HttpClient)
  private readonly baseUrl:string = `${baseUrlApi}/auth`

  login(loginData:LoginRequest):Observable<LoginResponse>{
    return this.httpClient.post<LoginResponse>(`${this.baseUrl}/login`,loginData)
  }

}
