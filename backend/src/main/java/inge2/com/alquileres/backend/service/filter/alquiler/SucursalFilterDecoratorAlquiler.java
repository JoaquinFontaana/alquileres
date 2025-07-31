package inge2.com.alquileres.backend.service.filter.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;

import java.util.List;

public class SucursalFilterDecoratorAlquiler extends AlquilerDecorator{
    private String nombreSucursal;

    public SucursalFilterDecoratorAlquiler(AlquilerFilterComponent component, String nombreSucursal){
        super(component);
        this.nombreSucursal = nombreSucursal;
    }

    @Override
    public List<Alquiler> filtrar(List<Alquiler> alquileres) {
        return alquileres.stream()
                .filter( alquiler -> alquiler.getSucursal().getCiudad().equals(nombreSucursal))
                .toList();
    }

}
