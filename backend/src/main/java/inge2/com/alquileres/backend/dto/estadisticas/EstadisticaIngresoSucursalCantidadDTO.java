package inge2.com.alquileres.backend.dto.estadisticas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstadisticaIngresoSucursalCantidadDTO {
    private String ciudad;
    private Double montoTotal;
    private Long cantidadAlquileres;
}
