package inge2.com.alquileres.backend.service.filter.auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;

import java.util.List;

public class DisponibilidadFilterDecorator extends FilterDecorator{
    private RangoFecha rangoFechaFilter;

    public DisponibilidadFilterDecorator(IAutoFilter componente, RangoFecha rangoFechaFilter) {
        super(componente);
        this.rangoFechaFilter = rangoFechaFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto ->
                        auto.disponibleEnRangoFechas(this.rangoFechaFilter)
                )
                .toList();
    }

}