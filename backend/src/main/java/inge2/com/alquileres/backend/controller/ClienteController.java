package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.DatosPagoDTO;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import inge2.com.alquileres.backend.dto.user.PersonaDTOPassword;
import inge2.com.alquileres.backend.service.AuthService;
import inge2.com.alquileres.backend.service.ClienteService;
import inge2.com.alquileres.backend.service.checkOut.CheckOutMultaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@RequestMapping(path = "/clientes")
public class ClienteController {
    private final AuthService authService;
    private final ClienteService service;
    private final CheckOutMultaService checkOutMultaService;
    private final ClienteService clienteService;

    @PostMapping()
    public ResponseEntity<String> registrar(@Valid @RequestBody PersonaDTOPassword cliente){
        service.crearCliente(cliente);
        return ResponseEntity.ok().body("Cliente creado con exito");
    }

    @PostMapping("/registrar-presencial")
    @PreAuthorize( "hasAuthority('EMPLEADO')")
    public ResponseEntity<String> registrarPresencial(@RequestBody @Valid PersonaDTO clienteDTO){
        this.clienteService.registrarClientePresencial(clienteDTO);
        return new ResponseEntity<>("Cliente registrado con exito", HttpStatus.CREATED);
    }

    @GetMapping()
    @PreAuthorize( "hasAuthority('EMPLEADO')")
    public List<PersonaDTO> listarClientes(){
        return this.clienteService.listarClientes();
    }

    @GetMapping("/multa")
    @PreAuthorize( "hasAuthority('CLIENT')")
    public double multa(){
        return this.service.getMulta(this.authService.getMailOfContext());
    }

    @PutMapping("/multa-pagada")
    @PreAuthorize( "hasAuthority('CLIENT')")
    public String pagarMulta(@Valid @RequestBody DatosPagoDTO datosPagoDTO) {
        return this.checkOutMultaService.pagarMulta(datosPagoDTO);
    }

    @GetMapping("/{email}/existe")
    @PreAuthorize( "hasAuthority('EMPLEADO')")
    public boolean existeCliente(@PathVariable @NotBlank String email) {
        return this.clienteService.existsCliente(email);
    }

}
