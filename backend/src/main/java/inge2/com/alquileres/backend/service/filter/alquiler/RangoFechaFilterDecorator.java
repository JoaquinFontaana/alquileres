package inge2.com.alquileres.backend.service.filter.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;

import java.util.List;

public class RangoFechaFilterDecorator extends AlquilerDecorator{

    private final RangoFecha rangoFecha;

    public RangoFechaFilterDecorator(AlquilerFilterComponent alquilerFilterComponent, RangoFecha rangoFecha) {
        super(alquilerFilterComponent);
        this.rangoFecha = rangoFecha;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter(a -> !a.sinSolapamiento(rangoFecha))
                .toList();
    }
}
