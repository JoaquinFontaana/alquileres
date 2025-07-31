package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.user.EmpleadoDTO;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.service.SucursalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {
    @Autowired
    private SucursalService service;

    @PostMapping("/admin/crear")
    public ResponseEntity<String> crearSucursal(@Valid @RequestBody Sucursal sucursal){
        service.crearSucursal(sucursal);
        return ResponseEntity.ok("Sucursal creada con exito");
    }

    @GetMapping("/admin/empleados")
    public List<EmpleadoDTO> listarEmpleados(@NotBlank @RequestParam String ciudad){
        return this.service.listarEmpleadosSucursal(ciudad);
    }

    @GetMapping("/listar")
    public List<String> listarSucursales(){
        return service.listarSucursales();
    }
}
