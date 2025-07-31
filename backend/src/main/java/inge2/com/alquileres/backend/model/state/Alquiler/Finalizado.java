package inge2.com.alquileres.backend.model.state.Alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class Finalizado implements EstadoAlquiler{

    @Override
    public void cancelar(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede cancelar.");
    }

    @Override
    public boolean retiroDisponible(Alquiler alquiler) {
        return false;
    }

    @Override
    public void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede iniciar nuevamente.");
    }

    @Override
    public void bajaAuto(Alquiler alquiler, AlquilerService alquilerService) {

    }

    @Override
    public void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa) {
        this.excepcionFinalizacionDoble();
    }

    @Override
    public void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler) {
        this.excepcionFinalizacionDoble();
    }

    @Override
    public void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService) {
        this.excepcionFinalizacionDoble();
    }

    @Override
    public void procesarPago(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException(" El alquiler se encuentra finalizado, no se puede procesar el pago.");
    }

    @Override
    public EstadoAlquilerEnum getEstadoAlquilerEnum() {
        return EstadoAlquilerEnum.FINALIZADO;
    }

    private void excepcionFinalizacionDoble(){
        throw new IllegalStateException("El alquiler ya se encuentra finalizado, no se puede finalizar nuevamente.");
    }
}
