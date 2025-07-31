package inge2.com.alquileres.backend.service.filter.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;

import java.util.List;

public class EstadoFilterDecoratorAlquiler extends AlquilerDecorator{

    private List<EstadoAlquilerEnum> estadoAlquilerEnum;

    public EstadoFilterDecoratorAlquiler(AlquilerFilterComponent alquilerFilterComponent, List<EstadoAlquilerEnum> estadoAlquilerEnum) {
        super(alquilerFilterComponent);
        this.estadoAlquilerEnum = estadoAlquilerEnum;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter(a -> this.estadoAlquilerEnum.contains(a.getEstadoAlquilerEnum()))
                .toList();
    }
}
