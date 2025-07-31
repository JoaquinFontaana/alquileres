package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.estadisticas.*;
import inge2.com.alquileres.backend.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService service;
    // 1. Ingresos por sucursal + cantidad de alquileres
    @GetMapping("/ingresos-por-sucursal")
    public List<EstadisticaIngresoSucursalCantidadDTO> getIngresosPorSucursal() {
        return service.obtenerIngresosPorSucursal();
    }
    // 2. Vehículos por sucursal + monto recaudado
    @GetMapping("/vehiculos-por-sucursal")
    public List<EstadisticaVehiculoSucursalMontoDTO> getVehiculosPorSucursal() {
        return service.obtenerVehiculosPorSucursal();
    }
    // 3. Vehículos más alquilados (general) + monto recaudado
    @GetMapping("/vehiculos-mas-alquilados")
    public List<EstadisticaVehiculoMontoDTO> getVehiculosMasAlquilados() {
        return service.obtenerVehiculosMasAlquilados();
    }
    // 4. Categorías más alquiladas + monto recaudado
    @GetMapping("/categorias-mas-alquiladas")
    public List<EstadisticaCategoriaMontoDTO> getCategoriasMasAlquiladas() {
        return service.obtenerCategoriasMasAlquiladas();
    }
    // 5. Top clientes + monto gastado
    @GetMapping("/top-clientes")
    public List<EstadisticaClienteMontoDTO> getTopClientes() {
        return service.obtenerTopClientes();
    }
    // 6. Ingresos totales + resumen de alquileres y reembolsos
    @GetMapping("/ingresos-totales")
    public EstadisticaIngresoResumenDTO getResumenIngresos() {
        return service.obtenerResumenIngresos();
    }
    // 7. Ingresos en un periodo de tiempo
    @GetMapping("/ingresos-periodo")
    public EstadisticaIngresoPeriodoDTO obtenerIngresosPeriodo(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return service.obtenerIngresosPeriodo(fechaInicio, fechaFin);
    }

}

