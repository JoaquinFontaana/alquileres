package inge2.com.alquileres.backend.dto.alquiler;

import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import inge2.com.alquileres.backend.model.enums.EstadoPago;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class AlquilerDTOFilter {
    private String nombreSucursal;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private List<EstadoAlquilerEnum> estadoAlquilerEnum;
    private String clienteMail;
    private List<EstadoPago> estadoPago;

    public AlquilerDTOFilter(String nombreSucursal, List<EstadoPago> estadoPago, String clienteMail, LocalDate fechaHasta, List<EstadoAlquilerEnum> estadoAlquilerEnum, LocalDate fechaDesde) {
        this.nombreSucursal = nombreSucursal;
        this.estadoPago = estadoPago;
        this.clienteMail = clienteMail;
        this.fechaHasta = fechaHasta;
        this.estadoAlquilerEnum = estadoAlquilerEnum;
        this.fechaDesde = fechaDesde;
    }
    public AlquilerDTOFilter(){

    }
}
