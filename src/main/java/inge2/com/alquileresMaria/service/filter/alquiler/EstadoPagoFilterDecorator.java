package inge2.com.alquileresMaria.service.filter.alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.enums.EstadoPago;

import java.util.List;

public class EstadoPagoFilterDecorator extends AlquilerDecorator {

    private List<EstadoPago> estadoPago;

    public EstadoPagoFilterDecorator(AlquilerFilterComponent alquilerFilterComponent, List<EstadoPago> estadoPago) {
        super(alquilerFilterComponent);
        this.estadoPago = estadoPago;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter(a -> this.estadoPago.contains(a.getPago().getEstadoPago()))
                .toList();
    }
}
