package inge2.com.alquileres.backend.model.state.Alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class Cancelado implements EstadoAlquiler{
    @Override
    public void cancelar(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException("El alquiler ya se encuentra cancelado, no se puede cancelar nuevamente.");
    }

    @Override
    public boolean retiroDisponible(Alquiler alquiler) {
        return false;
    }

    @Override
    public void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El alquiler se encuentra cancelar, no se puede iniciar.");
    }

    @Override
    public void bajaAuto(Alquiler alquiler, AlquilerService alquilerService) {

    }

    @Override
    public void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler) {
        this.excepcionFinalizacion();
    }

    @Override
    public void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa) {
        this.excepcionFinalizacion();
    }

    @Override
    public void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService) {
        this.excepcionFinalizacion();
    }

    @Override
    public void procesarPago(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException(" El alquiler se encuentra cancelado, no se puede procesar el pago.");
    }

    private void excepcionFinalizacion(){
        throw new IllegalStateException("El alquiler se encuentra cancelado, no se puede finalizar.");
    }

    @Override
    public EstadoAlquilerEnum getEstadoAlquilerEnum() {
        return EstadoAlquilerEnum.CANCELADO;
    }
}
