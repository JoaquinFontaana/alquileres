package inge2.com.alquileres.backend.service.checkOut;

import com.mercadopago.resources.payment.Payment;
import inge2.com.alquileres.backend.service.AuthService;
import inge2.com.alquileres.backend.service.builder.MpPreferenceBuilder;
import inge2.com.alquileres.backend.service.helper.CheckOutHelperService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public abstract class AbstractCheckOutService {

    private final CheckOutHelperService checkOutHelper;
    private final MpPreferenceBuilder mpPreferenceBuilder;
    private final AuthService authService;

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
