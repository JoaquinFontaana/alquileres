package inge2.com.alquileres.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El rol debe tener un nombre")
    @Column(nullable = false, unique = true)
    private String nombre;
    public Rol(){

    }
    public Rol(String nombre) {
        this.nombre = nombre;
    }
}
