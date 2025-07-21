package inge2.com.alquileresMaria.model.state.Alquiler;

import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.AutoService;

public class RetiroPendiente implements EstadoAlquiler{

    @Override
    public void cancelar(Alquiler alquiler, AlquilerService alquilerService) {
        alquiler.cambiarEstado(new Cancelado());
        alquilerService.rembolsarAlquiler(alquiler, alquiler.calcularRembolso());
        alquilerService.saveAlquiler(alquiler);
    }

    @Override
    public boolean retiroDisponible(Alquiler alquiler) {
        return alquiler.isTodayOrAfter();
    }

    @Override
    public void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService) {
        if(!alquiler.retiroDisponible()){
            throw new IllegalStateException("El alquiler todavía no se puede retirar, la fecha de retiro no es válida.");
        }
        alquiler.cambiarEstado(new EnUso());
        alquilerService.saveAlquiler(alquiler);
        alquiler.getAuto().iniciarAlquiler(autoService);
    }

    @Override
    public void bajaAuto(Alquiler alquiler, AlquilerService alquilerService) {
        alquilerService.rembolsarAlquiler(alquiler,alquiler.calcularTotal());
        alquiler.cambiarEstado(new Cancelado());
        alquilerService.saveAlquiler(alquiler);
        alquilerService.sendEmailBajaAuto(alquiler,"El alquiler ha sido cancelado porque el auto no se encuentra disponible para el retiro, se ha rembolsado el total del alquiler " + alquiler.calcularTotal());
    }

    @Override
    public void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler) {
        throw new  IllegalStateException("El alquiler no se puede finalizar, el retiro aún no ha sido realizado.");
    }

    @Override
    public void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa) {
        throw  new IllegalStateException("El alquiler no se puede finalizar con mantenimiento, el retiro aún no ha sido realizado.");
    }

    @Override
    public void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService) {
        alquiler.cambiarEstado(new Finalizado());
        alquilerService.saveAlquiler(alquiler);
    }

    @Override
    public EstadoAlquilerEnum getEstadoAlquilerEnum() {
        return EstadoAlquilerEnum.RETIRO_PENDIENTE;
    }
}
