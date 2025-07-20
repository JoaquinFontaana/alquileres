package inge2.com.alquileresMaria.model.state.Auto;

import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.model.enums.EstadoAutoEnum;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.AutoService;

public class Mantenimiento implements EstadoAuto{
    @Override
    public void darDeBaja(Auto auto, AlquilerService alquilerService, AutoService autoService) {
        auto.cambiarEstado(new DeBaja());
        autoService.saveAuto(auto);
    }

    @Override
    public void iniciarAlquiler(Auto auto,AutoService autoService ) {
        throw new IllegalStateException("El auto no se encuentra disponible para el retiro, por favor cambialo por otro auto similar.");
    }

    @Override
    public void iniciarMantenimiento(Auto auto, AutoService autoService, AlquilerService alquilerService) {
        throw new IllegalStateException("El auto ya se encuentra en mantenimiento.");
    }

    @Override
    public boolean estaDisponible() {
        return false;
    }

    @Override
    public void finalizarMantenimiento(Auto auto, AutoService autoService) {
        auto.cambiarEstado(new Mantenimiento());
        autoService.saveAuto(auto);
    }

    @Override
    public void finalizarAlquiler(Auto auto, AutoService autoService) {
        throw  new IllegalStateException("El auto no se encuentra alquilado, no se puede finalizar el alquiler.");
    }

    @Override
    public EstadoAutoEnum getEstadoAutoEnum() {
        return EstadoAutoEnum.MANTENIMIENTO;
    }
}
