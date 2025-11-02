export class VehicleFilter {
    private nombreSucursal?: string;
    private fechaDesde?: string;
    private fechaHasta?: string;
    private categorias?: string;
    private estadoAlquilerEnum?: string[];
    private clienteMail?: string;
    private estadoPago?: string[];


   public setNombreSucursal(value: string): void {
        this.nombreSucursal = value;
    }

    public setFechaDesde(value: string): void {
        this.fechaDesde = value;
    }

    public setFechaHasta(value: string): void {
        this.fechaHasta = value;
    }

    public setCategoria(value: string): void {
        this.categorias = value;
    }

    public setEstadoAlquilerEnum(value: string[]): void {
        this.estadoAlquilerEnum = value;
    }

    public setClienteMail(value: string): void {
        this.clienteMail = value;
    }

    public setEstadoPago(value: string[]): void {
        this.estadoPago = value;
    }


    public toQueryMap(): Map<string, any> {
        const queryMap = new Map<string, any>();
        
        if (this.nombreSucursal) queryMap.set('nombreSucursal', this.nombreSucursal);
        if (this.fechaDesde) queryMap.set('fechaDesde', this.fechaDesde);
        if (this.fechaHasta) queryMap.set('fechaHasta', this.fechaHasta);
        if (this.categorias) queryMap.set('categorias', this.categorias);
        if (this.estadoAlquilerEnum) queryMap.set('estadoAlquilerEnum', this.estadoAlquilerEnum);
        if (this.clienteMail) queryMap.set('clienteMail', this.clienteMail);
        if (this.estadoPago) queryMap.set('estadoPago', this.estadoPago);

        return queryMap;
    }
}