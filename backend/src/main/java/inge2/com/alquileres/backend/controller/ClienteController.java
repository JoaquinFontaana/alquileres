package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.dto.user.PersonaDTOPassword;
import inge2.com.alquileres.backend.service.ClienteService;
import inge2.com.alquileres.backend.service.helper.AuthHelperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cliente")
public class ClienteController {
    private final AuthHelperService authHelperService;
    private final ClienteService service;
    @Autowired
    public ClienteController(AuthHelperService authHelperService, ClienteService service) {
        this.authHelperService = authHelperService;
        this.service = service;
    }

    @PostMapping(path = "/registrar")
    public ResponseEntity<String> registrarCliente(@Valid @RequestBody PersonaDTOPassword cliente){
        service.crearCliente(cliente);
        return ResponseEntity.ok().body("Cliente creado con exito");

    }
    @GetMapping("/listar/alquileres")
    public List<AlquilerDTOListar> listarAlquileres() {
        return this.service.listarAlquileres(authHelperService.getMailOfContext());
    }
    @GetMapping("/multa")
    public double multa(){
        return this.service.getMulta(this.authHelperService.getMailOfContext());
    }
}
