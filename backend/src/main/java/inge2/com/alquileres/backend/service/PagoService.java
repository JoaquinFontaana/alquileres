package inge2.com.alquileres.backend.service;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.Pago;
import inge2.com.alquileres.backend.model.enums.EstadoPago;
import inge2.com.alquileres.backend.repository.IPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service @AllArgsConstructor
public class PagoService {

    private final IPagoRepository pagoRepository;
    private final AlquilerService alquilerService;

    @Transactional
    public Pago crearPago(Preference preference, Alquiler alquiler){
        Pago pago = new Pago(preference,alquiler);
        return this.pagoRepository.save(pago);
    }

    @Transactional
    public void registrarCobro(Payment payment){
        Pago pago = this.findPagoByAlquilerId(Long.parseLong(payment.getExternalReference()));

        pago.setPaymentId(payment.getId());
        pago.setEstadoPago(EstadoPago.PAGADO);
        pago.getAlquiler().procesarPago(alquilerService);

        this.pagoRepository.save(pago);
    }


    private Pago findPagoByAlquilerId(Long alquilerId){
        return pagoRepository.findByAlquiler_id(alquilerId)
                .orElseThrow(() -> new EntityNotFoundException("El pago con el alquilerId " + alquilerId + " no existe"));
    }

    @Transactional
    public void deletePagosPendientes(){
        List<Long> pagos = this.pagoRepository.findPagosExpirados(OffsetDateTime.now(ZoneOffset.UTC), EstadoPago.PENDIENTE).stream().map(Pago::getId).toList();
        this.pagoRepository.deleteAllById(pagos);
    }

}
