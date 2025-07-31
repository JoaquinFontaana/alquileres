import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { baseUrlApi } from '../../consts';
import { Vehicle, VehicleFilter } from '@models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehiclesData {
  private readonly httpClient = inject(HttpClient)
  private readonly baseUrl = `${baseUrlApi}/auto`

  getVehicles(filters?:VehicleFilter): Observable<Vehicle[]> {
      const url = this.baseUrl.concat('/listar');
      let params = new HttpParams()
      if(filters) {
        filters.toQueryMap().forEach((value,key) => {
          params = params.append(key,value)
        })
      }
      return this.httpClient.get<Vehicle[]>(url, { params })
  }


}
