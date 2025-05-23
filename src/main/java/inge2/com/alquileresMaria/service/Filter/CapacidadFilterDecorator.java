package inge2.com.alquileresMaria.service.Filter;

import inge2.com.alquileresMaria.model.Auto;

import java.util.List;

public class CapacidadFilterDecorator extends FilterDecorator{
    private int capacidadFilter;

    public CapacidadFilterDecorator(IAutoFilter componente, int capacidadFilter) {
        super(componente);
        this.capacidadFilter = capacidadFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream().filter(auto -> auto.getCapacidad() >= this.capacidadFilter).toList();
    }
}
