package inge2.com.alquileres.backend.service.filter.auto;

import inge2.com.alquileres.backend.model.Auto;

import java.util.List;

public class SucursalFilterDecorator extends FilterDecorator{
    private String nombreSucursal;

    public SucursalFilterDecorator(IAutoFilter componente, String nombreSucursal) {
        super(componente);
        this.nombreSucursal = nombreSucursal;
    }

    @Override
    public List<Auto> filtrar(List<Auto> autos) {
        return autos.stream()
                .filter(auto -> auto.getSucursal().getCiudad().equals(nombreSucursal))
                .toList();
    }
}
