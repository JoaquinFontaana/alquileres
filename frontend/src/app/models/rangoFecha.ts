export class RangoFecha{
    fechaInicio: Date;
    fechaFin: Date;

    constructor(fechaInicio: Date, fechaFin: Date) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    public cantidadDias(): number {
        const diff = this.fechaFin.getTime() - this.fechaInicio.getTime();
        const dias = Math.ceil(diff / (1000 * 60 * 60 * 24));
        return dias + 1; // +1 para incluir tanto el día de inicio como el de fin
    }
}