import { Persona } from './index'
export interface Empleado extends Persona {
    trabajaEnSucursal: string;
    estado: string;
    fechaBaja?: Date;
}