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

  createEmpleado(empleado: Partial<Empleado>, token: string): Observable<string> {
    const headers = addToken(token);
    return this.httpClient.post<string>(this.url, empleado, { 
      headers,
      responseType: 'text' as 'json'
    });
  }

  darDeBajaEmpleado(email: string, token: string): Observable<string> {
    const headers = addToken(token);
    return this.httpClient.delete<string>(`${this.url}/${email}`, { 
      headers,
      responseType: 'text' as 'json'
    });
  }
}
