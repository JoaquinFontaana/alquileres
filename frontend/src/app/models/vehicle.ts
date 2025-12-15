export interface Vehicle{
    id:string,
    patente: string;
    marca: string;
    modelo: string;
    capacidad: number;
    precioPorDia: number;
    tipoRembolso: string;
    estado: string;
    categoria: string;
    imgUrl: string;
    fechaBaja?: Date;
}