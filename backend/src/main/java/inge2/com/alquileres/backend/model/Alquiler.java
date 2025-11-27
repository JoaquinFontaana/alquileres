package inge2.com.alquileres.backend.model;

import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.model.enums.Extra;
import inge2.com.alquileres.backend.model.state.Alquiler.EstadoAlquiler;
import inge2.com.alquileres.backend.model.state.Alquiler.EstadoAlquilerListener;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
@EntityListeners(EstadoAlquilerListener.class)
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El rango de fechas es obligatorio")
    @Embedded
    private RangoFecha rangoFecha;
    @NotBlank(message = "La licencia del conductor es obligatoria")
    private String licenciaConductor;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne(optional = false)
    @JoinColumn(name = "auto_id")
    private Auto auto;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
    @OneToOne(
            mappedBy = "alquiler",
            optional = false,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Pago pago;
    @Enumerated(EnumType.STRING)
    private EstadoAlquilerEnum estadoAlquilerEnum;
    @Transient
    private EstadoAlquiler state;
    @OneToOne(mappedBy = "alquilerRembolsado")
    private Rembolso rembolso;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "alquiler_extras", joinColumns = @JoinColumn(name = "alquiler_id"))
    @Column(name = "extra")
    private List<Extra> extras;


    public void iniciar(AlquilerService alquilerService, AutoService autoService) {
        this.state.iniciar(this, alquilerService, autoService);
    }

    public void cambiarEstado(EstadoAlquiler estadoAlquiler) {
        this.setState(estadoAlquiler);
        this.setEstadoAlquilerEnum(estadoAlquiler.getEstadoAlquilerEnum());
    }

    public boolean sinSolapamiento(RangoFecha rango){
        return this.rangoFecha.sinSolapamiento(rango);
    }

    public double calcularTotal(){
        return (this.auto.getPrecioPorDia() * this.rangoFecha.cantidadDeDias()) + this.calcularExtras();
    }

    public double calcularExtras(){return this.extras.stream().mapToDouble(Extra::getPrecio).sum();}

    public double calcularRembolso(){
        return this.auto.calcularRembolso(this.calcularTotal());
    }

    public void addExtras(List<Extra> extras){
        if (extras == null) return;
        extras.stream()
                .filter(e -> !this.extras.contains(e))
                .forEach(e -> this.extras.add(e));
    }

    public void setClienteMulta(int montoMulta) {
        this.cliente.setMontoMulta(montoMulta);
    }

    public void finalizar(AutoService autoService,AlquilerService alquilerService) {
        this.state.finalizar(alquilerService,autoService,this);
    }

    public void finalizarConMantenimiento(AlquilerService alquilerService,AutoService autoService,int multa) {
        this.state.finalizarConMantenimiento(this,alquilerService,autoService,multa);
    }

    public void finalizarVencido(AlquilerService alquilerService) {
        this.state.finalizarVencido(this, alquilerService);
    }

    public boolean isTodayOrBefore() { return this.rangoFecha.isTodayOrBefore();}

    public boolean retiroDisponible() {
        return this.state.retiroDisponible(this);
    }

    public void cancelar(AlquilerService alquilerService){
        this.state.cancelar(this,alquilerService);
    }

    public void bajaAuto(AlquilerService alquilerService) {
        this.state.bajaAuto(this, alquilerService);
    }

    public void procesarPago(AlquilerService alquilerService) {
        this.state.procesarPago(this, alquilerService);
    }
}
