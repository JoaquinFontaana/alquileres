package inge2.com.alquileres.backend.model.state.Auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class Disponible implements EstadoAuto{
    @Override
    public void darDeBaja(Auto auto, AlquilerService alquilerService, AutoService autoService) {
        auto.getReservas().forEach(alquiler -> alquiler.bajaAuto(alquilerService));
        auto.cambiarEstado(new DeBaja());
        autoService.saveAuto(auto);
    }

    @Override
    public void iniciarAlquiler(Auto auto, AutoService autoService) {
        auto.cambiarEstado(new Alquilado());
        autoService.saveAuto(auto);
    }

    @Override
    public void iniciarMantenimiento(Auto auto, AutoService autoService, AlquilerService alquilerService) {
        auto.cambiarEstado(new Mantenimiento());
        auto.getReservas().forEach(alquiler -> alquiler.bajaAuto(alquilerService));
        autoService.saveAuto(auto);
    }

    @Override
    public boolean estaDisponible() {
        return true;
    }

    @Override
    public boolean esAlquilable() {
        return true;
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
        return EstadoAutoEnum.DISPONIBLE;
    }


}
