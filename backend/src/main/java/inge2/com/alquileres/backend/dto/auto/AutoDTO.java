package inge2.com.alquileres.backend.dto.auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.model.enums.TiposRembolso;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public abstract class AutoDTO {
    @NotBlank(message = "La patente es obligatoria")
    private String patente;
    @Positive(message = "La capacidad debe ser mayor a cero")
    private int capacidad;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @Positive(message = "El precio por dia debe ser positivo")
    @NotNull(message = "El precio por dia es obligatorio")
    private double precioPorDia;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "La categoria es obligatoria")
    private CategoriaAuto categoria;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El rembolso es obligatorio")
    private TiposRembolso rembolso;
    @Enumerated(EnumType.STRING)
    private EstadoAutoEnum estado;
    @NotBlank(message = "La sucursal es obligatoria")
    private String sucursal;

    public AutoDTO(){
        
    }
    public AutoDTO(Auto auto) {
        this.patente = auto.getPatente();
        this.capacidad = auto.getCapacidad();
        this.marca = auto.getMarca();
        this.modelo = auto.getModelo();
        this.precioPorDia = auto.getPrecioPorDia();
        this.categoria = auto.getCategoria();
        this.rembolso = auto.getRembolso();
        this.estado = auto.getEstado();
        this.sucursal = auto.getSucursal().getCiudad();
    }
}
