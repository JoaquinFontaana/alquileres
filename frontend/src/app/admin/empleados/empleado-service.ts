import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Empleado } from '@models';
import { Observable } from 'rxjs';
import { baseUrlApi,addToken } from '@shared/consts';
@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  readonly url = `${baseUrlApi}/empleados` 
  readonly httpClient = inject(HttpClient)
  
  getEmpleados(token:string):Observable<Empleado[]>{
    const headers = addToken(token)
    return this.httpClient.get<Empleado[]>(this.url,{headers})
  }

  
}
