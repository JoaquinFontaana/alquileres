package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.dto.user.PersonaDTOPassword;
import inge2.com.alquileres.backend.service.AuthService;
import inge2.com.alquileres.backend.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@RequestMapping(path = "/cliente")
public class ClienteController {
    private final AuthService authService;
    private final ClienteService service;

    @PostMapping(path = "/registrar")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody PersonaDTOPassword cliente){
        service.crearCliente(cliente);
        return ResponseEntity.ok().body("Cliente creado con exito");

    }
    @GetMapping("/listar/alquileres")
    public List<AlquilerDTOListar> listarAlquileres() {
        return this.service.listarAlquileres(authService.getMailOfContext());
    }

    @GetMapping("/multa")
    public double multa(){
        return this.service.getMulta(this.authService.getMailOfContext());
    }
}
