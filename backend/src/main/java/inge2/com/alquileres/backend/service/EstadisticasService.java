package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.dto.estadisticas.*;
import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Cliente;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import inge2.com.alquileres.backend.repository.IAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EstadisticasService {
    @Autowired
    private IAlquilerRepository alquilerRepository;

    // 5. Ingresos en un periodo de tiempo
    public EstadisticaIngresoPeriodoDTO obtenerIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        Double ingresos = alquilerRepository.findIngresosPeridoTiempo(fechaInicio, fechaFin);

        EstadisticaIngresoPeriodoDTO dto = new EstadisticaIngresoPeriodoDTO();
        dto.setIngresos(ingresos != null ? ingresos : 0.0);
        dto.setFechaInicio(fechaInicio.toString());
        dto.setFechaFin(fechaFin.toString());

        return dto;
    }

    public List<EstadisticaIngresoSucursalCantidadDTO> obtenerIngresosPorSucursal() {
        return alquilerRepository.findIngresosYCantidadPorSucursal()
                .stream()
                .map(obj -> new EstadisticaIngresoSucursalCantidadDTO(
                        (String) obj[0],
                        (Double) obj[1],
                        (Long) obj[2]
                )).toList();
    }

    public List<EstadisticaVehiculoSucursalMontoDTO> obtenerVehiculosPorSucursal() {
        return alquilerRepository.findMasAlquiladosSucursalConMonto()
                .stream()
                .map(obj -> new EstadisticaVehiculoSucursalMontoDTO(
                        (String) obj[0],
                        new AutoDTOListar((Auto) obj[1]),
                        (Long) obj[2],
                        (Double) obj[3]
                )).toList();
    }

    public List<EstadisticaVehiculoMontoDTO> obtenerVehiculosMasAlquilados() {
        return alquilerRepository.findVehiculosMasAlquiladosConMonto()
                .stream()
                .map(obj -> new EstadisticaVehiculoMontoDTO(
                        new AutoDTOListar((Auto) obj[0]),
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public List<EstadisticaCategoriaMontoDTO> obtenerCategoriasMasAlquiladas() {
        return alquilerRepository.findCategoriasMasAlquiladasConMonto()
                .stream()
                .map(obj -> new EstadisticaCategoriaMontoDTO(
                        (CategoriaAuto) obj[0],
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public List<EstadisticaClienteMontoDTO> obtenerTopClientes() {
        return alquilerRepository.findTopClientesConMonto()
                .stream()
                .map(obj -> new EstadisticaClienteMontoDTO(
                        new PersonaDTO((Cliente) obj[0]),
                        (Long) obj[1],
                        (Double) obj[2]
                )).toList();
    }

    public EstadisticaIngresoResumenDTO obtenerResumenIngresos() {
        Object result = alquilerRepository.findResumenIngresosTotales();
        if (result == null) {
            return new EstadisticaIngresoResumenDTO(0.0, 0L, 0.0, 0L);
        }

        Object[] resultArray = (Object[]) result;

        Double montoTotal = resultArray[0] != null ? (Double) resultArray[0] : 0.0;
        Long cantidadAlquileres = resultArray[1] != null ? (Long) resultArray[1] : 0L;
        Double montoReembolsado = resultArray[2] != null ? (Double) resultArray[2] : 0.0;
        Long cantidadReembolsos = resultArray[3] != null ? (Long) resultArray[3] : 0L;

        return new EstadisticaIngresoResumenDTO(montoTotal, cantidadAlquileres, montoReembolsado, cantidadReembolsos);
    }


}
