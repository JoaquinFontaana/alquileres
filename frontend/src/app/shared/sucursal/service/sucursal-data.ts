import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { baseUrlApi } from '../../consts';
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
}
