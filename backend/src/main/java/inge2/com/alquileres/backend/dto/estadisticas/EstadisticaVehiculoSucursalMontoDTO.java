package inge2.com.alquileres.backend.dto.estadisticas;

import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class EstadisticaVehiculoSucursalMontoDTO {
    private String ciudad;
    private AutoDTOListar auto;
    private Long cantidadAlquileres;
    private Double montoTotal;
}
