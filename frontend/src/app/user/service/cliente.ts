import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { baseUrlApi } from '@shared/consts';
import { RegisterClienteRequest } from '@models';
@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private readonly baseUrl = `${baseUrlApi}/clientes`
  private readonly httpClient = inject(HttpClient)
  
  registerCliente(registerData:RegisterClienteRequest):Observable<string>{
    return this.httpClient.post<string>(this.baseUrl,registerData);
  }

}
