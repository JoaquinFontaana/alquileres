package inge2.com.alquileres.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
public class Rembolso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate fechaRembolso;
    @NotNull
    private double montoRembolsado;
    @OneToOne(optional = false)
    @JoinColumn(name = "alquiler_id")
    private Alquiler alquilerRembolsado;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    public Rembolso(Alquiler alquilerRembolsado) {
        this.fechaRembolso = LocalDate.now();
        this.montoRembolsado = alquilerRembolsado.calcularRembolso();
        this.alquilerRembolsado = alquilerRembolsado;
        this.cliente = alquilerRembolsado.getCliente();
    }
    public Rembolso(Alquiler alquilerRembolsado,double montoRembolsado) {
        this.fechaRembolso = LocalDate.now();
        this.montoRembolsado = montoRembolsado;
        this.alquilerRembolsado = alquilerRembolsado;
        this.cliente = alquilerRembolsado.getCliente();
    }

    public Rembolso() {
    }
}
