package inge2.com.alquileres.backend.model.state.Auto;

import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class EstadoAutoFactory {

    private final Map<EstadoAutoEnum, EstadoAuto> estados = new EnumMap<>(EstadoAutoEnum.class);

    @PostConstruct
    public void init() {
        estados.put(EstadoAutoEnum.DISPONIBLE, new Disponible());
        estados.put(EstadoAutoEnum.ALQUILADO, new Alquilado());
        estados.put(EstadoAutoEnum.MANTENIMIENTO, new Mantenimiento());
        estados.put(EstadoAutoEnum.BAJA, new DeBaja());
    }

    public EstadoAuto getEstado(EstadoAutoEnum estado) {
        EstadoAuto impl = estados.get(estado);
        if (impl == null) {
            throw new IllegalArgumentException("No existe implementación para el estado: " + estado);
        }
        return impl;
    }
}