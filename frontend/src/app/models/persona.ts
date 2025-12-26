import { AuthenticatedUser } from './index';
export interface Persona extends AuthenticatedUser{
    nombre: string;
    apellido: string;
    dni:number;
}