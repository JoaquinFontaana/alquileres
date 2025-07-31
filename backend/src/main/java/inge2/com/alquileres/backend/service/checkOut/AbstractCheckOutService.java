package inge2.com.alquileres.backend.service.checkOut;

import com.mercadopago.resources.payment.Payment;
import inge2.com.alquileres.backend.service.builder.MpPreferenceBuilder;
import inge2.com.alquileres.backend.service.helper.AuthHelperService;
import inge2.com.alquileres.backend.service.helper.CheckOutHelperService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter @Setter
public abstract class AbstractCheckOutService {

    private CheckOutHelperService checkOutHelper;
    private MpPreferenceBuilder mpPreferenceBuilder;
    private AuthHelperService authHelperService;
    @Autowired
    public AbstractCheckOutService(CheckOutHelperService checkOutHelper, MpPreferenceBuilder mpPreferenceBuilder, AuthHelperService authHelperService) {
        this.checkOutHelper = checkOutHelper;
        this.mpPreferenceBuilder = mpPreferenceBuilder;
        this.authHelperService = authHelperService;
    }

    public void procesarNotificacion(String dataId, String type) {
        if (!"payment".equals(type)){
            return;
        }
        Payment payment = this.checkOutHelper.getPayment(dataId);
        //Si el estado es approved se cambia el estado o valor
        if(this.checkOutHelper.checkStatusApproved(payment)) {
            this.procesarPagoAprobado(payment);
        }
    }
    public abstract void procesarPagoAprobado(Payment payment);
}
