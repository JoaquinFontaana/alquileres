package inge2.com.alquileres.backend.model.state.Auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class Alquilado implements EstadoAuto{
    @Override
    public void darDeBaja(Auto auto, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El auto se encuentra alquilado, no se puede dar de baja.");
    }

    @Override
    public EstadoAutoEnum getEstadoAutoEnum() {
        return EstadoAutoEnum.ALQUILADO;
    }

    @Override
    public void iniciarMantenimiento(Auto auto, AutoService autoService, AlquilerService alquilerService) {
        auto.cambiarEstado(new Mantenimiento());
        auto.getReservas().forEach(alquiler -> alquiler.bajaAuto(alquilerService));
        autoService.saveAuto(auto);
    }

    @Override
    public boolean estaDisponible() {
        return false;
    }

    @Override
    public boolean esAlquilable() {
        return true;
    }

    @Override
    public void finalizarAlquiler(Auto auto, AutoService autoService) {
        auto.cambiarEstado(new Disponible());
        autoService.saveAuto(auto);
    }

    @Override
    public void finalizarMantenimiento(Auto auto, AutoService autoService) {
        throw  new IllegalStateException("El auto no se encuentra en mantenimiento, no se puede finalizar el mantenimiento.");
    }

    @Override
    public void iniciarAlquiler(Auto auto, AutoService autoService) {
        throw new IllegalStateException("El auto no se encuentra disponible para el retiro, por favor cambialo por otro auto similar.");
    }
}
