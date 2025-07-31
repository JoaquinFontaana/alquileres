package inge2.com.alquileres.backend.service.filter.auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;

import java.util.List;

public class CategoriaFilterDecorator extends FilterDecorator{
    List<CategoriaAuto> categoriasFilter;

    public CategoriaFilterDecorator(IAutoFilter componente, List<CategoriaAuto> categoriasFilter) {
        super(componente);
        this.categoriasFilter = categoriasFilter;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto -> this.categoriasFilter.contains(auto.getCategoria()))
                .toList();
    }
}
