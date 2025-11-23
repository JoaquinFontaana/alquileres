package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.user.EmpleadoDTO;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTOListar;
import inge2.com.alquileres.backend.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/empleados") @AllArgsConstructor
public class EmpleadoController {
    private final EmpleadoService empleadoService;


    @PostMapping()
    @PreAuthorize( "hasAuthority('ADMIN')")
    public ResponseEntity<String> crearEmpleado(@RequestBody @Valid EmpleadoDTO empleadoDTO){
        this.empleadoService.crearEmpleado(empleadoDTO);
        return new ResponseEntity<>("Empleado registrado con exito", HttpStatus.CREATED);
    }

    //Solo mandar los datos a actualizar y el email actual del empleado (obligatorio)
    @PatchMapping()
    @PreAuthorize( "hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizarDatosEmpleado(@RequestBody @Valid EmpleadoDTOActualizar empleadoDTO){
        this.empleadoService.actualizarEmpleado(empleadoDTO);
        return new ResponseEntity<>("Empleado actualizado con exito",HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize( "hasAuthority('ADMIN')")
    public List<EmpleadoDTOListar> listarEmpleados(){
        return this.empleadoService.listarEmpleados();
    }

    @DeleteMapping("/{email}")
    @PreAuthorize( "hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable String email){
        this.empleadoService.eliminarEmpleado(email);
        return new ResponseEntity<>("Empleado eliminado con exito", HttpStatus.OK);
    }
}
