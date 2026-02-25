package inge2.com.alquileres.backend.configuration;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class MPConfig {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        MercadoPagoConfig.setAccessToken(this.accessToken);
    }

}
