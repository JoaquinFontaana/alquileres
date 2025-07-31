package inge2.com.alquileres.backend.service.checkOut;

import com.mercadopago.resources.payment.Payment;
import inge2.com.alquileres.backend.dto.DatosPagoDTO;
import inge2.com.alquileres.backend.model.Cliente;
import inge2.com.alquileres.backend.service.ClienteService;
import inge2.com.alquileres.backend.service.builder.MpPreferenceBuilder;
import inge2.com.alquileres.backend.service.helper.AuthHelperService;
import inge2.com.alquileres.backend.service.helper.CheckOutHelperService;
import inge2.com.alquileres.backend.service.helper.ClienteHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckOutMultaService extends AbstractCheckOutService {

    private final ClienteHelperService clienteHelperService;
    private final ClienteService clienteService;
    @Autowired
    public CheckOutMultaService(CheckOutHelperService checkOutHelper, MpPreferenceBuilder mpPreferenceBuilder, AuthHelperService authHelperService, ClienteHelperService clienteHelperService, ClienteService clienteService) {
        super(checkOutHelper, mpPreferenceBuilder, authHelperService);
        this.clienteHelperService = clienteHelperService;
        this.clienteService = clienteService;
    }

    public String pagarMulta(DatosPagoDTO datosPagoDTO) {
        Cliente cliente = this.clienteHelperService.findClienteByEmail(this.getAuthHelperService().getMailOfContext());
        if(cliente.getMontoMulta() == 0){
            throw new IllegalStateException("El monto de la multa es 0, no hay multa a pagar");
        }
        return this.getMpPreferenceBuilder().crearPreferenceMulta(cliente, datosPagoDTO).getInitPoint();
    }

    @Override
    public void procesarPagoAprobado(Payment payment) {
        this.clienteService.registrarPagoMulta(payment.getExternalReference());
    }
}
