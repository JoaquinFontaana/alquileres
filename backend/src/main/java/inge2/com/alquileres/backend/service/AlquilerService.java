package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.repository.IAlquilerRepository;
import inge2.com.alquileres.backend.service.helper.AlquilerHelperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AlquilerService {
    private final IAlquilerRepository repository;
    private final AlquilerHelperService alquilerHelperService;
    private final RembolsoService rembolsoService;
    private final EmailService emailService;

    @Transactional
    public void cancelarReserva(Long codigoAlquiler) {
        log.info("Cancelling reservation. Id: {}", codigoAlquiler);
        this.alquilerHelperService.findById(codigoAlquiler).cancelar(this);
    }

    public void sendEmailBajaAuto(Alquiler alquiler, String body) {
        String subject = "Su auto reservado " + alquiler.getAuto().getModelo() + " ya no se encuentra disponible";
        this.emailService.sendEmail(alquiler.getCliente().getEmail(), subject, body);
    }

    @Transactional
    public void rembolsarAlquiler(Alquiler alquiler, double montoRembolso) {
        log.info("Processing refund for rental id: {}. Amount: {}", alquiler.getId(), montoRembolso);
        this.rembolsoService.crearRembolso(alquiler, montoRembolso);
    }

    @Transactional
    public void eliminarAlquiler(Alquiler alquiler) {
        log.info("Deleting rental id: {}", alquiler.getId());
        this.repository.delete(alquiler);
    }

    @Transactional
    public Alquiler saveAlquiler(Alquiler alquiler) {
        return this.repository.save(alquiler);
    }

    public void finalizarAlquileresVencidosNoRetirados() {
        log.info("Checking for expired unretrieved rentals");
        this.repository.findVencidosRetiroPendiente(LocalDate.now())
                .forEach(a -> a.finalizarVencido(this));
    }

    public List<Alquiler> findRetiroPendienteByCiudad(String ciudad) {
        return this.repository.findRetiroPendienteByCiudad(ciudad);
    }

    public List<Alquiler> findEnUsoByCiudad(String ciudad) {
        return this.repository.findEnUsoByCiudad(ciudad);
    }

}
