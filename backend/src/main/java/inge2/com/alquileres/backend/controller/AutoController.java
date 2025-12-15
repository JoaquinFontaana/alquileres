package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOCambiarAuto;
import inge2.com.alquileres.backend.dto.auto.AutoDTOActualizar;
import inge2.com.alquileres.backend.dto.auto.AutoDTOCrear;
import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.dto.auto.AutoFilterDTO;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.model.enums.TiposRembolso;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import inge2.com.alquileres.backend.service.AutoService;
import inge2.com.alquileres.backend.service.useCase.Alquiler.CambiarAutoUseCase;
import inge2.com.alquileres.backend.service.useCase.Auto.EliminarAutoUseCase;
import inge2.com.alquileres.backend.service.useCase.Auto.ListarAutosUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autos") @AllArgsConstructor
public class AutoController {

    private final AutoService autoService;
    private final EliminarAutoUseCase eliminarAutoUseCase;
    private final ListarAutosUseCase listarAutosUseCase;
    private final CambiarAutoUseCase cambiarAutoUseCase;

    @DeleteMapping("/{patente}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminarAuto(@PathVariable @NotBlank String patente){
        this.eliminarAutoUseCase.eliminarAuto(patente);
        return ResponseEntity.ok("Auto eliminado con exito");
    }
    @GetMapping("/disponibilidad/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENTE','EMPLEADO')")
    public ResponseEntity<Boolean> getDisponibilidad(@PathVariable @NotBlank Long id,@ModelAttribute RangoFecha rangoFecha){
        boolean res = autoService.disponibilidad(rangoFecha,id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> crearAuto(@Valid @ModelAttribute AutoDTOCrear autoDto){
        this.autoService.crearAuto(autoDto);
        return ResponseEntity.ok("Auto creado con exito");
    }

    @PutMapping("/cambiar-auto")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<String> cambiarAuto(@Valid @RequestBody AlquilerDTOCambiarAuto alquilerDTOCambiarAuto){
        this.cambiarAutoUseCase.cambiarAuto(alquilerDTOCambiarAuto);
        return ResponseEntity.ok("Auto cambiado exitosamente");
    }

    @PutMapping(consumes =MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizarAuto(@Valid @ModelAttribute AutoDTOActualizar autoActualizado){
        this.autoService.actualizarAuto(autoActualizado);
        return ResponseEntity.ok("Auto actualizado con exito");
    }

    @GetMapping("/categorias")
    public List<CategoriaAuto> getCategorias(){
        return List.of(CategoriaAuto.values());
    }

    @GetMapping("/estados")
    public List<EstadoAutoEnum> getEstados(){
        return List.of(EstadoAutoEnum.values());
    }

    @GetMapping("/rembolsos")
    public List<TiposRembolso> getRembolsos(){
        return List.of(TiposRembolso.values());
    }
    /**
     * Lista los autos disponibles aplicando filtros combinados (todos opcionales).
     * <p>Los filtros se envían como parámetros de query, y se combinan con lógica AND.</p>
     *
     * Filtros disponibles:
     * <ul>
     *     <li><b>nombreSucursal</b>: nombre de la sucursal (String).</li>
     *     <li><b>fechaDesde</b> y <b>fechaHasta</b>: rango de fechas deseado (formato yyyy-MM-dd).</li>
     *     <li><b>capacidad</b>: capacidad mínima requerida (entero positivo).</li>
     *     <li><b>categorias</b>: lista de categorías (puede repetirse en la query).</li>
     * </ul>
     *
     * Ejemplo de request:
     * <pre>
     * GET /auto/listar?nombreSucursal=La%20Plata&fechaDesde=2025-06-01&fechaHasta=2025-06-10&capacidad=5&categorias=SEDAN&categorias=CAMIONETA
     * </pre>
     *
     * @param opcionesFiltrado objeto con los parámetros de filtrado, mapeado desde query parameters.
     * @return lista de autos disponibles que cumplen los filtros.
    @Operation(
            summary = "Listar autos con filtros opcionales",
            description = "Permite filtrar autos por nombre de sucursal, rango de fechas, capacidad mínima y categorías. Todos los filtros son opcionales y se combinan con lógica AND."
    )*/

    @GetMapping()
    public List<AutoDTOListar> listarAutos(@ModelAttribute AutoFilterDTO opcionesFiltrado){
        return this.listarAutosUseCase.listarAutos(opcionesFiltrado);
    }


    @PatchMapping("/{patente}/reparacion-finalizada")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<String> reparado(@Valid @PathVariable String patente){
        this.autoService.finalizarMantenimiento(patente);
        return ResponseEntity.ok("Mantenimiento finalizado con exito");
    }

}
