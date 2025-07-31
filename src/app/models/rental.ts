import { Vehicle, Rembolso } from './index';
export interface Rental {
    codigoReserva: number;
    vehiculo: Vehicle;
    dniConductor: string;
    mailCliente: string;
    fechaDesde: Date;
    fechaHasta: Date;
    estado: string;
    monto: number;
    sucursal: string;
    urlPago?: string;
    rembolso?: Rembolso; 
}