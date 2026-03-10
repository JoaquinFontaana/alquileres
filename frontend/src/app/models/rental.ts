import { Vehicle, Rembolso } from './index';
export interface Rental {
    codigoReserva: number;
    auto: Vehicle;
    mailCliente: string;
    rangoFechas: {
        fechaDesde: Date;
        fechaHasta: Date;
    }
    estadoPago: string;
    estadoAlquilerEnum:string;
    monto: number;
    sucursal: string;
    urlPago?: string;
    rembolsoDTO?: Rembolso; 
}