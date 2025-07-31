package inge2.com.alquileres.backend.service.builder;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import inge2.com.alquileres.backend.service.filter.alquiler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlquilerFilterBuilder {
    @Autowired
    private BaseAlquilerFilter baseAlquilerFilter;

    public AlquilerFilterComponent buildFilter(AlquilerDTOFilter filtros){
        AlquilerFilterComponent filtro = this.baseAlquilerFilter;
        if (filtros.getNombreSucursal() != null){
            filtro = new SucursalFilterDecoratorAlquiler(filtro, filtros.getNombreSucursal());
        }
        if (filtros.getFechaDesde() != null && filtros.getFechaHasta() != null){
            RangoFecha rangoFecha =  new RangoFecha(filtros.getFechaDesde(), filtros.getFechaHasta());
            filtro =  new RangoFechaFilterDecorator(filtro, rangoFecha);
        }
        if (filtros.getEstadoAlquilerEnum() != null){
            filtro = new EstadoFilterDecoratorAlquiler(filtro, filtros.getEstadoAlquilerEnum());
        }
        if (filtros.getClienteMail() != null){
            filtro = new ClienteFilterDecorator(filtro, filtros.getClienteMail());
        }
        if (filtros.getEstadoPago() != null){
            filtro = new EstadoPagoFilterDecorator(filtro, filtros.getEstadoPago());
        }
        return filtro;
    }
}
