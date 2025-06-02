package inge2.com.alquileresMaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private String ciudad;
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
