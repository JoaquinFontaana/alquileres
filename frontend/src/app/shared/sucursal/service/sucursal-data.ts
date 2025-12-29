import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { addToken } from '../../consts';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Sucursal } from '@models';
@Injectable({
  providedIn: 'root'
})
export class SucursalData {
  private readonly baseUrl = `${environment.apiUrl}/sucursales` 
  private readonly httpClient = inject(HttpClient);
  
  getSucursales():Observable<Sucursal[]>{
    return this.httpClient.get<Sucursal[]>(this.baseUrl);
  }

  createSucursal(sucursal:Sucursal,token:string): Observable<string> {
    const headers = addToken(token)
    return this.httpClient.post<string>(this.baseUrl, sucursal, { headers,responseType: 'text' as 'json' });
  }
}
