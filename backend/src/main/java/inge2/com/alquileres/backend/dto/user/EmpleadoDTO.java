package inge2.com.alquileres.backend.dto.user;

import inge2.com.alquileres.backend.model.Empleado;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmpleadoDTO extends PersonaDTO{
    @NotNull(message = "La sucursal donde trabaja el empleado es obligatoria, ingresa el nombre")
    private String trabajaEnSucursal;

    public EmpleadoDTO(Empleado empleado){
        super(empleado);
        this.trabajaEnSucursal = empleado.getTrabajaEnSucursal().getCiudad();
    }
    public EmpleadoDTO(){
        super();
    }
}
