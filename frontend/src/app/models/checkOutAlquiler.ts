// Enum de extras disponibles para el alquiler
export enum Extra {
  SEGURO = 'SEGURO',
  CADENA_NIEVE = 'CADENA_NIEVE',
  SILLA_BEBE = 'SILLA_BEBE',
  COMBUSTIBLE_COMPLETO = 'COMBUSTIBLE_COMPLETO'
}

// DTO para crear un alquiler
export interface AlquilerDTOCrear {
  rangoFecha: {
    fechaDesde: string; // LocalDate en formato ISO (YYYY-MM-DD)
    fechaHasta: string;
  };
  licenciaConductor: string;
  patenteAuto: string;
  sucursal: string;
  extras: Extra[];
}

// DTO con datos de pago para MercadoPago
export interface DatosPagoDTO {
  titulo: string;
  successUrl: string;
  failureUrl: string;
  pendingUrl: string;
}

// DTO para el checkout del alquiler
export interface CheckOutAlquilerDTO {
  datosPagoDTO: DatosPagoDTO;
  alquilerDTO: AlquilerDTOCrear;
}
