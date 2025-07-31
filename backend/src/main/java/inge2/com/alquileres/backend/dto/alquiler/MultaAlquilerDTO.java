package inge2.com.alquileres.backend.dto.alquiler;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultaAlquilerDTO {
    @Positive(message = "El monto de la multa debe ser positivo")
    @NotNull(message = "El monto de la multa es obligatorio")
    private int montoMulta;
    @NotNull (message = "El codigo del alquiler es obligatorio")
    private Long codigoAlquiler;
}
