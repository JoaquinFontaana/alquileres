package inge2.com.alquileres.backend.model.state.Alquiler;

import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class EstadoAlquilerFactory {

    private final Map<EstadoAlquilerEnum, EstadoAlquiler> estados = new EnumMap<>(EstadoAlquilerEnum.class);

    @PostConstruct
    public void init() {
        estados.put(EstadoAlquilerEnum.RETIRO_PENDIENTE, new RetiroPendiente());
        estados.put(EstadoAlquilerEnum.CANCELADO, new Cancelado());
        estados.put(EstadoAlquilerEnum.EN_USO, new EnUso());
        estados.put(EstadoAlquilerEnum.FINALIZADO, new Finalizado());
        estados.put(EstadoAlquilerEnum.CONFIRMACION_PENDIENTE, new ConfirmacionPendiente());
    }

    public EstadoAlquiler getEstado(EstadoAlquilerEnum estado) {
        EstadoAlquiler impl = estados.get(estado);
        if (impl == null) {
            throw new IllegalArgumentException("No existe implementación para el estado: " + estado);
        }
        return impl;
    }
}