package inge2.com.alquileres.backend.dto.estadisticas;

import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EstadisticaCategoriaMontoDTO {
    private CategoriaAuto categoria;
    private Long cantidadAlquileres;
    private Double montoTotal;

}
