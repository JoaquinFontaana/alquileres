package inge2.com.alquileres.backend.dto.alquiler;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlquilerDTOCambiarAuto {
    @NotNull (message = "La patente del auto nuevo es obligatoria")
    private String patenteAutoNuevo;
    @NotNull(message = "El codigo del alquiler es obligatorio")
    private Long codigoAlquiler;
}
