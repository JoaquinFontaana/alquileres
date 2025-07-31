package inge2.com.alquileres.backend.model.state.Alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public class EnUso implements EstadoAlquiler {
    @Override
    public void cancelar(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException("El alquiler se encuentra en uso, no se puede cancelar.");
    }
    @Override
    public boolean retiroDisponible(Alquiler alquiler) {
        return false;
    }

    @Override
    public void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService) {
        throw new IllegalStateException("El alquiler se encuentra en curso, no se puede iniciar nuevamente.");
    }

    @Override
    public void bajaAuto(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException("El alquiler se encuentra en curso, no se puede dar de baja el auto.");
    }

    @Override
    public void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler) {
        alquiler.cambiarEstado(new Finalizado());
        alquiler.getAuto().finalizarAlquiler(autoService);
        alquilerService.saveAlquiler(alquiler);
    }

    @Override
    public void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService) {
         throw new IllegalStateException( "El alquiler se encuentra en curso, no se puede finalizar.");
    }

    @Override
    public void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa) {
        alquiler.setClienteMulta(multa);
        alquiler.cambiarEstado(new Finalizado());
        alquilerService.saveAlquiler(alquiler);
        alquiler.getAuto().iniciarMantenimiento(autoService,alquilerService);
    }

    @Override
    public void procesarPago(Alquiler alquiler, AlquilerService alquilerService) {
        throw new IllegalStateException("El alquiler ya ha sido pagado.");
    }

    @Override
    public EstadoAlquilerEnum getEstadoAlquilerEnum() {
        return EstadoAlquilerEnum.EN_USO;
    }
}
