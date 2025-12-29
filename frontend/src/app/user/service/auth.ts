import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest, LoginResponse } from '@models';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly httpClient = inject(HttpClient)
  private readonly baseUrl:string = `${environment.apiUrl}/auth`

  login(loginData:LoginRequest):Observable<LoginResponse>{
    return this.httpClient.post<LoginResponse>(`${this.baseUrl}/login`,loginData)
  }

}
