package inge2.com.alquileres.backend.model.state.Auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class DeBaja implements EstadoAuto{
    @Override
    public void darDeBaja(Auto auto, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El auto ya se encuentra dado de baja, no se puede dar de baja nuevamente.");
    }
    @Override
    public void iniciarAlquiler(Auto auto, AutoService autoService) {
        throw new IllegalStateException("El auto no se encuentra disponible para el retiro, por favor cambialo por otro auto similar.");
    }

    public void iniciarMantenimiento(Auto auto, AutoService autoService, AlquilerService alquilerService) {
        auto.cambiarEstado( new Mantenimiento());
        autoService.saveAuto(auto);
    }

    @Override
    public boolean estaDisponible() {
        return false;
    }

    @Override
    public boolean esAlquilable() {
        return false;
    }

    @Override
    public void finalizarAlquiler(Auto auto, AutoService autoService) {
        throw  new IllegalStateException("El auto no se encuentra alquilado, no se puede finalizar el alquiler.");
    }

    @Override
    public void finalizarMantenimiento(Auto auto, AutoService autoService) {
        throw  new IllegalStateException("El auto no se encuentra en mantenimiento, no se puede finalizar el mantenimiento.");
    }

    @Override
    public EstadoAutoEnum getEstadoAutoEnum() {
        return EstadoAutoEnum.BAJA;
    }
}
