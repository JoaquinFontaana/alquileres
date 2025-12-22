export class VehicleFilter {
    private nombreSucursal?: string;
    private fechaDesde?: string;
    private fechaHasta?: string;
    private categorias?: string;
    private estado?: string;


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

    public setEstado(value: string): void {
        this.estado = value;
    }



    public toQueryMap(): Map<string, any> {
        const queryMap = new Map<string, any>();
        
        if (this.nombreSucursal) queryMap.set('nombreSucursal', this.nombreSucursal);
        if (this.fechaDesde) queryMap.set('fechaDesde', this.fechaDesde);
        if (this.fechaHasta) queryMap.set('fechaHasta', this.fechaHasta);
        if (this.categorias) queryMap.set('categorias', this.categorias);
        if (this.estado) queryMap.set('estadoAutoEnum', this.estado);

        return queryMap;
    }
}