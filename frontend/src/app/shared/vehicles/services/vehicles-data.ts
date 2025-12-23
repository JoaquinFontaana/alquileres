import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { baseUrlApi } from '../../consts';
import { RangoFecha, Vehicle, VehicleCreateDTO, VehicleFilter } from '@models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehiclesData {
  private readonly httpClient = inject(HttpClient)
  private readonly baseUrl = `${baseUrlApi}/autos`

  
  getVehicles(filters?:VehicleFilter): Observable<Vehicle[]> {
      let params = new HttpParams()
      if(filters) {
        filters.toQueryMap().forEach((value,key) => {
          params = params.append(key,value)
        })
      }
      return this.httpClient.get<Vehicle[]>(this.baseUrl, { params })
  }

  getCategorias(): Observable<string[]>{
    const url = this.baseUrl.concat("/categorias")
    return this.httpClient.get<string[]>(url)
  }

  // Consultar disponibilidad de un vehículo (retorna solo boolean)
  checkDisponibilidad(id: number, rangoFecha: RangoFecha, token: string): Observable<boolean> {
    const params = new HttpParams()
      .append('fechaDesde', rangoFecha.fechaInicio.toISOString().split('T')[0])
      .append('fechaHasta', rangoFecha.fechaFin.toISOString().split('T')[0]);
    
    const headers = this.addToken(token)
    
    return this.httpClient.get<boolean>(`${this.baseUrl}/disponibilidad/${id}`, { params, headers });
  }

  getEstados():Observable<string[]>{
    const url = this.baseUrl.concat("/estados")
    return this.httpClient.get<string[]>(url)
  }

  getRembolsos():Observable<string[]>{
    const url = this.baseUrl.concat("/rembolsos")
    return this.httpClient.get<string[]>(url)
  }

  createVehicle(data:VehicleCreateDTO,token:string):Observable<string>{
    const headers = this.addToken(token);
    const formData = data.toFormData();
    
    return this.httpClient.post<string>(this.baseUrl, formData, {
      headers,
      responseType: 'text' as 'json'
    });
  }

  private addToken(token:string):HttpHeaders{
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  } 
}
