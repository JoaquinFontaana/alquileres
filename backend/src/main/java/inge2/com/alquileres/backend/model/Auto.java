package inge2.com.alquileres.backend.model;

import inge2.com.alquileres.backend.dto.auto.AutoDTO;
import inge2.com.alquileres.backend.dto.auto.AutoDTOActualizar;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.model.enums.TiposRembolso;
import inge2.com.alquileres.backend.model.state.Auto.EstadoAuto;
import inge2.com.alquileres.backend.model.state.Auto.EstadoAutoListener;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter @Setter @NoArgsConstructor
@EntityListeners(EstadoAutoListener.class)
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La patente es obligatoria")
    @Column(unique = true, nullable = false)
    private String patente;
    @Positive(message = "La capacidad debe ser mayor a cero")
    private int capacidad;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @NotNull(message = "El precio por dia es obligatorio")
    @Positive(message = "El precio por dia debe ser positivo")
    private double precioPorDia;
    @Enumerated(EnumType.STRING)
    private CategoriaAuto categoria;
    @Enumerated(EnumType.STRING)
    private TiposRembolso rembolso;
    @Enumerated(EnumType.STRING)
    private EstadoAutoEnum estado;
    @OneToMany(mappedBy = "auto")
    private List<Alquiler> reservas;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
    @NotBlank(message = "La imagen es obligatoria")
    @Column(unique = true, nullable = false)
    private String rutaImagen;
    @Transient
    private EstadoAuto state;

    public Auto(AutoDTO dto, Sucursal sucursal, String rutaImagen) {
        this.asignarDTOaAuto(dto);
        this.sucursal = sucursal;
        this.rutaImagen = rutaImagen;
    }

    public void actualizarAuto(AutoDTOActualizar dto, Sucursal sucursal){
        if (dto.getPrecioPorDia() != null) {
            this.precioPorDia = dto.getPrecioPorDia();
        }
        if (dto.getCategoria() != null) {
            this.categoria = dto.getCategoria();
        }
        if (dto.getRembolso() != null) {
            this.rembolso = dto.getRembolso();
        }
        if (dto.getEstado() != null) {
            this.estado = dto.getEstado();
        }
        this.sucursal = sucursal;
    }


    public void actualizarAutoImagen(AutoDTOActualizar dto, Sucursal sucursal,String rutaImagen){
        this.actualizarAuto(dto,sucursal);
        this.rutaImagen = rutaImagen;
    }

    private void asignarDTOaAuto(AutoDTO dto){
        this.patente = dto.getPatente();
        this.capacidad = dto.getCapacidad();
        this.marca = dto.getMarca();
        this.modelo = dto.getModelo();
        this.precioPorDia = dto.getPrecioPorDia();
        this.categoria = dto.getCategoria();
        this.rembolso = dto.getRembolso();
        this.estado = EstadoAutoEnum.DISPONIBLE;
    }
    public boolean disponibleEnRangoFechas(RangoFecha rango){
        return this.getReservas().stream().filter(alquiler -> alquiler.getEstadoAlquilerEnum() != EstadoAlquilerEnum.CANCELADO).allMatch(alquiler -> alquiler.sinSolapamiento(rango));
    }
    public boolean estaDisponible(){
        return this.state.estaDisponible();
    }

    public boolean esAlquilable(){
        return this.state.esAlquilable();
    }

    public void iniciarAlquiler(AutoService autoService){
        this.state.iniciarAlquiler(this,autoService);
    }

    public void finalizarAlquiler(AutoService autoService){
        this.state.finalizarAlquiler(this,autoService);
    }

    public void iniciarMantenimiento(AutoService autoService,AlquilerService alquilerService){
        this.state.iniciarMantenimiento(this,autoService,alquilerService);
    }


    public void darDeBaja(AlquilerService alquilerService, AutoService autoService) {
        this.state.darDeBaja(this, alquilerService, autoService);
    }

    public void cambiarEstado(EstadoAuto estadoAuto) {
        this.state = estadoAuto;
        this.setEstado(estadoAuto.getEstadoAutoEnum());
    }

    public void finalizarMantenimiento(AutoService autoService) {
        this.state.finalizarMantenimiento(this, autoService);
    }

    public double calcularRembolso(double monto){
        return this.rembolso.calcularRembolso(monto);
    }
}
