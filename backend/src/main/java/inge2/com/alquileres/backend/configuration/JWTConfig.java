package inge2.com.alquileres.backend.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration @Getter
public class JWTConfig {
    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    private final Long TOKEN_EXPIRATION = 7000000L;

}