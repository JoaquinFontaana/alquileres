package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOCambiarAuto;
import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import inge2.com.alquileres.backend.service.ClienteService;
import inge2.com.alquileres.backend.service.useCase.Alquiler.CambiarAutoUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    private final ClienteService clienteService;
    private final CambiarAutoUseCase cambiarAutoUseCase;

    public EmpleadoController(ClienteService clienteService, CambiarAutoUseCase cambiarAutoUseCase) {
        this.clienteService = clienteService;
        this.cambiarAutoUseCase = cambiarAutoUseCase;
    }

    @PostMapping("/registrar/cliente")
    public ResponseEntity<String> registrarCliente(@RequestBody @Valid PersonaDTO clienteDTO){
        this.clienteService.registrarClientePresencial(clienteDTO);
        return new ResponseEntity<>("Cliente registrado con exito", HttpStatus.CREATED);
    }

    @GetMapping("/existe/cliente")
    public ResponseEntity<String>  existeCliente(@RequestParam @NotBlank String mail) {
        this.clienteService.checkNotExistsCliente(mail);
        return new ResponseEntity<>("El cliente no se encuentra registrado", HttpStatus.OK);
    }

    @PutMapping("/cambiarAuto")
    public ResponseEntity<String> cambiarAuto(@Valid @RequestBody AlquilerDTOCambiarAuto alquilerDTOCambiarAuto){
        this.cambiarAutoUseCase.cambiarAuto(alquilerDTOCambiarAuto);
        return ResponseEntity.ok("Auto cambiado exitosamente");
    }
}
