package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.repository.IAlquilerRepository;
import inge2.com.alquileres.backend.service.helper.AlquilerHelperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class AlquilerService {

    private final IAlquilerRepository repository;

    private final AlquilerHelperService alquilerHelperService;
    private final RembolsoService rembolsoService;
    private final EmailService emailService;

    public AlquilerService(IAlquilerRepository repository, AlquilerHelperService alquilerHelperService, RembolsoService rembolsoService, EmailService emailService) {
        this.repository = repository;
        this.alquilerHelperService = alquilerHelperService;
        this.rembolsoService = rembolsoService;
        this.emailService = emailService;
    }

    @Transactional
    public void cancelarReserva(Long codigoAlquiler) {
        this.alquilerHelperService.findById(codigoAlquiler).cancelar(this);
    }

    public void sendEmailBajaAuto(Alquiler alquiler, String body) {
        String subject = "Su auto reservado "+ alquiler.getAuto().getModelo() + " ya no se encuentra disponible";
        this.emailService.sendEmail(alquiler.getCliente().getMail(), subject, body);
    }

    @Transactional
    public void rembolsarAlquiler(Alquiler alquiler,double montoRembolso){
        this.rembolsoService.crearRembolso(alquiler, montoRembolso);
    }

    @Transactional
    public void eliminarAlquiler(Alquiler alquiler){
        this.repository.delete(alquiler);
    }

    @Transactional
    public Alquiler saveAlquiler(Alquiler alquiler){
        return this.repository.save(alquiler);
    }

    public void finalizarAlquileresVencidosNoRetirados() {
         this.repository.findVencidosRetiroPendiente(LocalDate.now())
                 .forEach(a-> a.finalizarVencido(this));
    }

    public List<Alquiler> findRetiroPendienteByCiudad(String ciudad) {
        return this.repository.findRetiroPendienteByCiudad(ciudad);
    }

    public List<Alquiler> findEnUsoByCiudad(String ciudad) {
        return this.repository.findEnUsoByCiudad(ciudad);
    }

}
