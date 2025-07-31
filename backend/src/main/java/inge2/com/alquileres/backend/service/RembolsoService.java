package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.Rembolso;
import inge2.com.alquileres.backend.model.enums.EstadoPago;
import inge2.com.alquileres.backend.repository.IRembolsoRepository;
import inge2.com.alquileres.backend.service.builder.MpPreferenceBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RembolsoService {

    private final MpPreferenceBuilder mpPreferenceBuilder;
    private final IRembolsoRepository rembolsoRepository;

    public RembolsoService(MpPreferenceBuilder mpPreferenceBuilder, IRembolsoRepository rembolsoRepository) {
        this.mpPreferenceBuilder = mpPreferenceBuilder;
        this.rembolsoRepository = rembolsoRepository;
    }
    @Transactional
    public void crearRembolso(Alquiler reserva, double montoRembolso){
        if(montoRembolso > 0) {
            mpPreferenceBuilder.rembolsar(montoRembolso, reserva.getPago().getPaymentId());
        }
        reserva.getPago().setEstadoPago(EstadoPago.REMBOLSADO);
        rembolsoRepository.save(new Rembolso(reserva));
    }
}
