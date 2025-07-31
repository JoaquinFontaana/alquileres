package inge2.com.alquileres.backend.dto.user;

import inge2.com.alquileres.backend.model.Empleado;
import inge2.com.alquileres.backend.model.enums.EstadoEmpleado;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class EmpleadoDTOListar extends EmpleadoDTO{
    @NotNull
    private EstadoEmpleado estado;
    private LocalDate fechaBaja;

    public EmpleadoDTOListar(Empleado empleado) {
        super(empleado);
        this.estado = empleado.getEstado();
        this.fechaBaja = empleado.getFechaBaja();
    }

    public EmpleadoDTOListar() { super(); }

}
