package inge2.com.alquileres.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SucursalDTO {
    @NotNull(message = "La latitud de la sucursal es obligatoria")
    private double latitud;
    @NotNull(message = "La latitud de la sucursal es obligatoria")
    private double longitud;
    @NotBlank(message = "La latitud de la sucursal es obligatoria")
    private String ciudad;
}
