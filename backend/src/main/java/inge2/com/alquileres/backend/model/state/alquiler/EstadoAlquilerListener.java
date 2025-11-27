package inge2.com.alquileres.backend.model.state.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import jakarta.persistence.PostLoad;
import org.springframework.stereotype.Component;


@Component
public class EstadoAlquilerListener {

    private final EstadoAlquilerFactory factory;

    public EstadoAlquilerListener(EstadoAlquilerFactory factory) {
        this.factory = factory;
    }

    @PostLoad
    public void setState(Alquiler alquiler) {
        alquiler.setState(factory.getEstado(alquiler.getEstadoAlquilerEnum()));
    }
}

