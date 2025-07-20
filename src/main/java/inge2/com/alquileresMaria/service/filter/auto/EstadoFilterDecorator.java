package inge2.com.alquileresMaria.service.filter.auto;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.enums.EstadoAutoEnum;

import java.util.List;

public class EstadoFilterDecorator extends FilterDecorator{

    private List<EstadoAutoEnum> estadosFilter;

    public EstadoFilterDecorator(IAutoFilter componente, List<EstadoAutoEnum> estadosFilter) {
        super(componente);
        this.estadosFilter = estadosFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto -> this.estadosFilter.contains(auto.getEstado()))
                .toList();
    }
}
