package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.SucursalDTO;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTO;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.service.SucursalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@RequestMapping("/sucursales")
public class SucursalController {
    private final SucursalService service;

    @PostMapping()
    @PreAuthorize( "hasAuthority('ADMIN')")
    public ResponseEntity<String> crearSucursal(@Valid @RequestBody SucursalDTO sucursal){
        service.crearSucursal(sucursal);
        return ResponseEntity.ok("Sucursal creada con exito");
    }

    @GetMapping("/empleados")
    @PreAuthorize( "hasAuthority('ADMIN')")
    public List<EmpleadoDTO> listarEmpleados(@NotBlank @RequestParam String ciudad){
        return this.service.listarEmpleadosSucursal(ciudad);
    }

    @GetMapping()
    public List<SucursalDTO> listarSucursales(){
        return service.listarSucursales();
    }
}
