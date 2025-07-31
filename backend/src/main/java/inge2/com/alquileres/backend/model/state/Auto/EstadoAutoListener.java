package inge2.com.alquileres.backend.model.state.Auto;

import inge2.com.alquileres.backend.model.Auto;
import jakarta.persistence.PostLoad;
import org.springframework.stereotype.Component;

@Component
public class EstadoAutoListener {
    private final EstadoAutoFactory factory;

    public EstadoAutoListener(EstadoAutoFactory factory) {
        this.factory = factory;
    }

    @PostLoad
    public void setState(Auto auto) {
        auto.setState(this.factory.getEstado(auto.getEstado()));
    }
}
