package inge2.com.alquileres.backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Alquileres de Vehículos API")
                        .version("1.0")
                        .description("Documentación de los endpoints de la API de alquileres de vehículos."));
    }
}
