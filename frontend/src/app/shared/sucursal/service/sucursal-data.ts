import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { addToken, baseUrlApi } from '../../consts';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class SucursalData {
  private readonly baseUrl = `${baseUrlApi}/sucursales` 
  private readonly httpClient = inject(HttpClient);
  
  getSucursales():Observable<string[]>{
    return this.httpClient.get<string[]>(this.baseUrl);
  }

  createSucursal(ciudad: string,token:string): Observable<string> {
    const headers = addToken(token)
    return this.httpClient.post<string>(this.baseUrl, { ciudad }, { headers,responseType: 'text' as 'json' });
  }
}
