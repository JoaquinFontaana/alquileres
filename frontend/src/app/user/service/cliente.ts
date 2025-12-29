import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterClienteRequest } from '@models';
import { environment } from 'src/enviroments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private readonly baseUrl = `${environment.apiUrl}/clientes`
  private readonly httpClient = inject(HttpClient)
  
  registerCliente(registerData:RegisterClienteRequest):Observable<string>{
    return this.httpClient.post(this.baseUrl, registerData, { 
      responseType: 'text' 
    });
  }

}
