package inge2.com.alquileres.backend.model.state.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;

public interface EstadoAlquiler {
    EstadoAlquilerEnum getEstadoAlquilerEnum();

    void finalizar(AlquilerService alquilerService, AutoService autoService, Alquiler alquiler);

    void finalizarConMantenimiento(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService, int multa);

    void finalizarVencido(Alquiler alquiler, AlquilerService alquilerService);

    boolean retiroDisponible(Alquiler alquiler);

    void cancelar(Alquiler alquiler, AlquilerService alquilerService);

    void iniciar(Alquiler alquiler, AlquilerService alquilerService, AutoService autoService);

    void bajaAuto(Alquiler alquiler, AlquilerService alquilerService);

    void procesarPago(Alquiler alquiler, AlquilerService alquilerService);
}
