package inge2.com.alquileres.backend.dto;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOCrear;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckOutAlquilerDTO {
    @Embedded
    private DatosPagoDTO datosPagoDTO;
    @Embedded
    private AlquilerDTOCrear alquilerDTO;

}
