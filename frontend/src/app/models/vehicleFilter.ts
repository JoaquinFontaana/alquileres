export interface VehicleFilterFormData {
    nombreSucursal?: string | null;
    categorias?: string | null;
    fechaDesde?: string | null;
    fechaHasta?: string | null;
    estados?: string | null;
}

export class VehicleFilter {
    private nombreSucursal?: string;
    private fechaDesde?: string;
    private fechaHasta?: string;
    private categorias?: string;
    private estado?: string;

    /**
     * Crea un VehicleFilter a partir de los datos del formulario
     * @param formData Datos del formulario
     * @param formatDateFn Función para formatear fechas
     * @param isAdmin Indica si el usuario es administrador
     * @returns Una instancia de VehicleFilter configurada
     */
    static fromFormData(
        formData: VehicleFilterFormData, 
        formatDateFn: (date: string | null) => string | null,
        isAdmin = false
    ): VehicleFilter {
        const filter = new VehicleFilter();
        
        if (formData.nombreSucursal) {
            filter.setNombreSucursal(formData.nombreSucursal);
        }
        
        if (formData.categorias) {
            filter.setCategoria(formData.categorias);
        }
        
        if (formData.fechaDesde) {
            const fechaFormateada = formatDateFn(formData.fechaDesde);
            if (fechaFormateada) {
                filter.setFechaDesde(fechaFormateada);
            }
        }
        
        if (formData.fechaHasta) {
            const fechaFormateada = formatDateFn(formData.fechaHasta);
            if (fechaFormateada) {
                filter.setFechaHasta(fechaFormateada);
            }
        }
        
        if (formData.estados && isAdmin) {
            filter.setEstado(formData.estados);
        }
        
        return filter;
    }

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