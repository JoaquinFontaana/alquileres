package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.user.EmpleadoDTO;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTOListar;
import inge2.com.alquileres.backend.service.ClienteService;
import inge2.com.alquileres.backend.service.EmpleadoService;
import inge2.com.alquileres.backend.service.useCase.Alquiler.CambiarAutoUseCase;
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

    //Solo mandar los datos a actualizar y el mail actual del empleado (obligatorio)
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

    @DeleteMapping("/{mail}")
    @PreAuthorize( "hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable String mail){
        this.empleadoService.eliminarEmpleado(mail);
        return new ResponseEntity<>("Empleado eliminado con exito", HttpStatus.OK);
    }
}
