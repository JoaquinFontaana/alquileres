package inge2.com.alquileres.backend.model.state.Alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class ConfirmacionPendiente implements EstadoAlquiler{

    @Override
    public void cancelar(Alquiler alquiler, AlquilerService alquilerService) {
        alquilerService.eliminarAlquiler(alquiler);
    }

    @Override
    public boolean retiroDisponible(Alquiler alquiler) {
        return false;
    }

    @Override
    public void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El alquiler esta pendiente de confirmación, no se puede iniciar.");
    }

    @Override
    public void bajaAuto(Alquiler alquiler, AlquilerService alquilerService) {
        alquilerService.sendEmailBajaAuto(alquiler,"Su alquiler pendiente de pago se ha cancelado, porque el auto ya no se encuentra disponible para el alquiler.");
        alquilerService.eliminarAlquiler(alquiler);
    }

    @Override
    public void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler) {
        throw new  IllegalStateException("El alquiler esta pendiente de confirmación, no se puede finalizar.");
    }

    @Override
    public void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService) {
        throw  new IllegalStateException("El alquiler esta pendiente de confirmación, no se puede finalizar vencido.");
    }

    @Override
    public void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa) {
        throw new IllegalStateException("El alquiler esta pendiente de confirmación, no se puede finalizar con mantenimiento.");
    }

    @Override
    public void procesarPago(Alquiler alquiler, AlquilerService alquilerService) {
        alquiler.cambiarEstado(new RetiroPendiente());
        alquilerService.saveAlquiler(alquiler);
    }

    @Override
    public EstadoAlquilerEnum getEstadoAlquilerEnum() {
        return EstadoAlquilerEnum.CONFIRMACION_PENDIENTE;
    }
}
