package inge2.com.alquileres.backend.scheduler;

import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.PagoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlquileresScheduled {

    private final PagoService pagoService;
    private final AlquilerService alquilerService;

    public AlquileresScheduled(PagoService pagoService, AlquilerService alquilerService) {
        this.pagoService = pagoService;
        this.alquilerService = alquilerService;
    }

    @Scheduled(fixedDelay = 600000*30)
    public void deletePagosPendientes(){
        this.pagoService.deletePagosPendientes();
    }

    @Scheduled(fixedDelay = 60000000*24) // 24 horas
    public void finalizarAlquileresVencidos() {
        this.alquilerService.finalizarAlquileresVencidosNoRetirados();
    }
}
