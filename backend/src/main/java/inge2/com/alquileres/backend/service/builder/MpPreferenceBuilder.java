package inge2.com.alquileres.backend.service.builder;

import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileres.backend.dto.DatosPagoDTO;
import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
@Component
public class MpPreferenceBuilder {

    @Value("${NGROK_URL}")
    private String publicUrl;
    public Preference crearPreferenceMulta(Cliente cliente, DatosPagoDTO datosPagoDTO){
        return this.crearPreference(
                cliente.getId().toString(),
                cliente.getMontoMulta(),
                datosPagoDTO,
                "/checkOut/notificacion/multa"
        );
    }
    public Preference crearPreferenceAlquiler(Alquiler alquiler, DatosPagoDTO datosPagoDTO) {
            return this.crearPreference(
                    alquiler.getId().toString(),
                    alquiler.calcularTotal(),
                    datosPagoDTO,
                    "/checkOut/notificacion/alquiler"
            );
    }

    private Preference crearPreference(String externalReference, Double monto, DatosPagoDTO datosPagoDTO, String urlNotificacion){
        try {
            PreferenceItemRequest item = this.crearPreferenceItemRequest(datosPagoDTO, monto);
            PreferenceBackUrlsRequest backUrlsRequest = this.crearPreferenceBackUrlsRequest(datosPagoDTO);

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .backUrls(backUrlsRequest)
                    //Para implementar las notificaciones la API debe estar en un dominio accesible en internet
                    .notificationUrl(publicUrl + urlNotificacion)
                    //Referencia a la tabla Pago guardada en la BD (id del alquiler)
                    .externalReference(externalReference)
                    .autoReturn("approved")
                    .expirationDateFrom(OffsetDateTime.now(ZoneOffset.UTC))
                    .expirationDateTo(OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(15))
                    .build();
            PreferenceClient client = new PreferenceClient();
            return client.create(preferenceRequest);
        }
        catch (MPApiException ex){
            System.out.println(publicUrl + urlNotificacion);
            System.err.println("Status: " + ex.getStatusCode());
            System.err.println("Response body: " + ex.getApiResponse().getContent());
            throw new RuntimeException("No se pudo procesar el alquiler: " + ex.getApiResponse().getContent(), ex);
        }
        catch (MPException ex){
            System.err.println("Error al : " + ex.getMessage());
            throw new RuntimeException("No se pudo procesar el alquiler.", ex);
        }
    }

    private PreferenceItemRequest crearPreferenceItemRequest(DatosPagoDTO datosPagoDTO, double total){
        return PreferenceItemRequest.builder()
                .title(datosPagoDTO.getTitulo())
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(new BigDecimal(total))
                .build();
    }

    private PreferenceBackUrlsRequest crearPreferenceBackUrlsRequest(DatosPagoDTO datosPagoDTO){
        return  PreferenceBackUrlsRequest.builder()
                .success(datosPagoDTO.getSuccessUrl()) //Rutas de redirrecion en el frontend
                .failure(datosPagoDTO.getFailureUrl())
                .pending(datosPagoDTO.getPendingUrl())
                .build();
    }
    public void rembolsar(double monto,Long paymentId) {
        try {
            PaymentRefundClient client = new PaymentRefundClient();
            System.out.println(BigDecimal.valueOf(monto));
            client.refund(paymentId, BigDecimal.valueOf(monto));
        }
        catch (MPApiException ex){
            System.err.println("Status: " + ex.getStatusCode());
            System.err.println("Response body: " + ex.getApiResponse().getContent());
            throw new RuntimeException("No se pudo procesar el reembolso: " + ex.getApiResponse().getContent(), ex);
        }
        catch (MPException ex){
            System.err.println("Error al : " + ex.getMessage());
            throw new RuntimeException("No se pudo procesar el reembolso.", ex);
        }
    }
}
