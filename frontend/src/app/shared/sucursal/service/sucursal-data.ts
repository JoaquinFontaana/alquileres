import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { addToken } from '../../consts';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/environment';
@Injectable({
  providedIn: 'root'
})
export class SucursalData {
  private readonly baseUrl = `${environment.apiUrl}/sucursales` 
  private readonly httpClient = inject(HttpClient);
  
  getSucursales():Observable<string[]>{
    return this.httpClient.get<string[]>(this.baseUrl);
  }

  createSucursal(ciudad: string,token:string): Observable<string> {
    const headers = addToken(token)
    return this.httpClient.post<string>(this.baseUrl, { ciudad }, { headers,responseType: 'text' as 'json' });
  }
}
