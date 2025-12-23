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
    sucursal:string;
    fechaBaja?: Date;
}

// Clase para crear vehículos con método para convertir a FormData
export class VehicleCreateDTO {
    patente: string;
    capacidad: number;
    marca: string;
    modelo: string;
    precioPorDia: number;
    categoria: string;
    rembolso: string;
    estado?: string;
    sucursal: string;
    imagen: File;

    constructor(data: {
        patente: string;
        capacidad: number;
        marca: string;
        modelo: string;
        precioPorDia: number;
        categoria: string;
        rembolso: string;
        estado?: string;
        sucursal: string;
        imagen: File;
    }) {
        this.patente = data.patente;
        this.capacidad = data.capacidad;
        this.marca = data.marca;
        this.modelo = data.modelo;
        this.precioPorDia = data.precioPorDia;
        this.categoria = data.categoria;
        this.rembolso = data.rembolso;
        this.estado = data.estado;
        this.sucursal = data.sucursal;
        this.imagen = data.imagen;
    }

    toFormData(): FormData {
        const formData = new FormData();
        
        formData.append('patente', this.patente);
        formData.append('capacidad', this.capacidad.toString());
        formData.append('marca', this.marca);
        formData.append('modelo', this.modelo);
        formData.append('precioPorDia', this.precioPorDia.toString());
        formData.append('categoria', this.categoria);
        formData.append('rembolso', this.rembolso);
        formData.append('sucursal', this.sucursal);
        
        if (this.estado) {
            formData.append('estado', this.estado);
        }
        
        formData.append('imagen', this.imagen);
        
        return formData;
    }
}