package inge2.com.alquileres.backend.dto.estadisticas;

import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EstadisticaClienteMontoDTO {
    private PersonaDTO cliente;
    private Long cantidadAlquileres;
    private Double montoGastado;
}
