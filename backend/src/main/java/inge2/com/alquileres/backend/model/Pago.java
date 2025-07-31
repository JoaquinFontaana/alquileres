package inge2.com.alquileres.backend.model;

import com.mercadopago.resources.preference.Preference;
import inge2.com.alquileres.backend.model.enums.EstadoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity @Getter @Setter
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(
            optional = false,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "alquiler_id")
    private Alquiler alquiler;
    @NotBlank
    private String initPoint;
    private Long paymentId;
    @Positive(message = "El monto del pago debe ser positivo") @NotNull
    private Double monto;
    @Enumerated(EnumType.STRING) @NotNull
    @Column(length = 20)
    private EstadoPago estadoPago;
    @NotNull
    private OffsetDateTime fechaCreacion;
    @NotNull
    private OffsetDateTime fechaExpiracion;

    public Pago(Preference preference,Alquiler alquiler) {
        this.alquiler = alquiler;
        this.initPoint = preference.getInitPoint();
        this.monto = alquiler.calcularTotal();
        this.estadoPago = EstadoPago.PENDIENTE;
        this.fechaCreacion = preference.getDateCreated();
        this.fechaExpiracion = this.fechaCreacion.plusMinutes(15);
    }

    public Pago(){

    }
}
