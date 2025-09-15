package inge2.com.alquileres.backend.service.filter.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;

import java.util.List;

public class ClienteFilterDecorator extends AlquilerDecorator{

    private String clienteMail;

    public ClienteFilterDecorator(AlquilerFilterComponent alquilerFilterComponent, String clienteMail) {
        super(alquilerFilterComponent);
        this.clienteMail = clienteMail;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter(a -> a.getCliente().getEmail().equals(clienteMail))
                .toList();
    }
}
