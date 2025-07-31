package inge2.com.alquileres.backend.service.filter.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;

import java.util.List;

public abstract class AlquilerDecorator implements AlquilerFilterComponent{

    private AlquilerFilterComponent alquilerFilterComponent;

    public List<Alquiler> getAlquileres(){
        List<Alquiler> listaPrevia = this.alquilerFilterComponent.getAlquileres();
        return this.filtrar(listaPrevia);
    }
    public abstract List<Alquiler> filtrar(List<Alquiler> alquileres);

    public AlquilerDecorator(AlquilerFilterComponent alquilerFilterComponent) {
        this.alquilerFilterComponent = alquilerFilterComponent;
    }
}
