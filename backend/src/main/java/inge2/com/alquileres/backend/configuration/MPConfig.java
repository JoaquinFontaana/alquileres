package inge2.com.alquileres.backend.configuration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPConfig {
    @Value("${MERCADOPAGO_ACCESS_TOKEN}")
    private String accessToken;

    @PostConstruct
    public void init(){
        MercadoPagoConfig.setAccessToken(this.accessToken);
    }

}
