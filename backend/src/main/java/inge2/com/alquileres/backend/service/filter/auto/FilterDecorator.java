package inge2.com.alquileres.backend.service.filter.auto;

import inge2.com.alquileres.backend.model.Auto;

import java.util.List;

public abstract class FilterDecorator implements IAutoFilter{
    private IAutoFilter componente;

    @Override
    public List<Auto> listar() {
        List<Auto> listaPrevia = this.componente.listar();
        //Aplico filtro concreto del decorator
        return this.filtrar(listaPrevia);
    }
    public abstract List<Auto> filtrar(List<Auto> autos);

    public FilterDecorator(IAutoFilter componente) {
        this.componente = componente;
    }
}
