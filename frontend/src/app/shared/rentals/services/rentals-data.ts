import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { baseUrlApi, addToken } from '../../consts';
import { Rental } from '@models';
import { Observable } from 'rxjs';

export interface RentalFilter {
  nombreSucursal?: string;
  fechaDesde?: string;
  fechaHasta?: string;
  estadoAlquilerEnum?: string[];
  clienteMail?: string;
  estadoPago?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class RentalsData {
  private readonly httpClient = inject(HttpClient);
  private readonly baseUrl = `${baseUrlApi}/alquileres`;

  // Listar alquileres del cliente autenticado
  getClientRentals(token: string): Observable<Rental[]> {
    const headers = addToken(token);
    return this.httpClient.get<Rental[]>(`${this.baseUrl}/cliente`, { headers });
  }

  // Listar todos los alquileres (admin/empleado) con filtros
  getAllRentals(filters: RentalFilter, token: string): Observable<Rental[]> {
    const headers = addToken(token);
    let params = new HttpParams();
    
    if (filters.nombreSucursal) params = params.append('nombreSucursal', filters.nombreSucursal);
    if (filters.fechaDesde) params = params.append('fechaDesde', filters.fechaDesde);
    if (filters.fechaHasta) params = params.append('fechaHasta', filters.fechaHasta);
    if (filters.clienteMail) params = params.append('clienteMail', filters.clienteMail);
    
    // Para arrays, agregar cada elemento individualmente
    if (filters.estadoAlquilerEnum && filters.estadoAlquilerEnum.length > 0) {
      filters.estadoAlquilerEnum.forEach(estado => {
        params = params.append('estadoAlquilerEnum', estado);
      });
    }
    
    if (filters.estadoPago && filters.estadoPago.length > 0) {
      filters.estadoPago.forEach(estado => {
        params = params.append('estadoPago', estado);
      });
    }
    
    return this.httpClient.get<Rental[]>(this.baseUrl, { headers, params });
  }

  // Listar alquileres pendientes de retiro (empleado)
  getPendingPickups(sucursal: string, token: string): Observable<Rental[]> {
    const headers = addToken(token);
    const params = new HttpParams().set('sucursal', sucursal);
    return this.httpClient.get<Rental[]>(`${this.baseUrl}/pendientes-retiro`, { headers, params });
  }

  // Listar alquileres pendientes de devolución (empleado)
  getPendingReturns(sucursal: string, token: string): Observable<Rental[]> {
    const headers = addToken(token);
    const params = new HttpParams().set('sucursal', sucursal);
    return this.httpClient.get<Rental[]>(`${this.baseUrl}/pendientes-devolucion`, { headers, params });
  }

  // Cancelar reserva
  cancelRental(id: number, token: string): Observable<string> {
    const headers = addToken(token);
    return this.httpClient.put<string>(`${this.baseUrl}/${id}/cancelado`, {}, { headers });
  }

  // Registrar entrega del vehículo
  markAsDelivered(id: number, token: string): Observable<string> {
    const headers = addToken(token);
    return this.httpClient.post<string>(`${this.baseUrl}/${id}/entregado`, {}, { headers });
  }

  // Recibir vehículo sin daños
  markAsReturnedCorrect(id: number, token: string): Observable<string> {
    const headers = addToken(token);
    return this.httpClient.post<string>(`${this.baseUrl}/${id}/recibido-correcto`, {}, { headers });
  }

  // Recibir vehículo con daños (multa)
  markAsReturnedWithFine(data: { codigoAlquiler: number; multa: number }, token: string): Observable<string> {
    const headers = addToken(token);
    return this.httpClient.post<string>(`${this.baseUrl}/recibido-multa`, data, { headers });
  }
}
