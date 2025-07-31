package inge2.com.alquileres.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatosPagoDTO {
    @NotBlank(message = "El titulo de la transaccion es obligatorio")
    private String titulo;
    @NotBlank(message = "Las rutas de redireccion son obligatorias")
    private String successUrl;
    @NotBlank(message = "Las rutas de redireccion son obligatorias")
    private String failureUrl;
    @NotBlank(message = "Las rutas de redireccion son obligatorias")
    private String pendingUrl;

}
