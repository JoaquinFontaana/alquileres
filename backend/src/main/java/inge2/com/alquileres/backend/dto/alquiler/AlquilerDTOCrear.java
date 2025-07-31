package inge2.com.alquileres.backend.dto.alquiler;

import inge2.com.alquileres.backend.model.enums.Extra;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AlquilerDTOCrear {
    @Embedded
    private RangoFecha rangoFecha;
    @NotBlank(message = "La licen del conductor es obligatorio")
    private String licenciaConductor;
    @NotBlank(message = "Patente del auto es obligatorio")
    private String patenteAuto;
    @NotBlank(message = "La sucursal donde se retira el auto es obligatoria, ingresa el nombre")
    private String sucursal;
    @NotBlank(message = "Los extras son obligatorios")
    private List<Extra> extras;

}
