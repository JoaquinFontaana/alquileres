package inge2.com.alquileres.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Entity
@Getter @Setter
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La ciudad es obligatoria")
    @Column (nullable = false, unique = true)
    private String ciudad;
    @NotNull(message = "La latitud de la sucursal es obligatoria")
    private Double latitud;
    @NotNull(message = "La longitud de la sucursal es obligatoria")
    private Double longitud;
    @OneToMany(mappedBy = "sucursal")
    private List<Auto> autos;
    @OneToMany(mappedBy = "trabajaEnSucursal")
    private List<Empleado> empleados;

    public Sucursal(String ciudad) {
        this.ciudad = ciudad;
        this.empleados = new ArrayList<>();
        this.autos = new ArrayList<>();
    }

    public Sucursal() {

    }
}
