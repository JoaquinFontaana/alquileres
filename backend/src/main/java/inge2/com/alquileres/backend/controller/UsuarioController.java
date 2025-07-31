package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/recuperarPassword")
    public ResponseEntity<String> recuperarPassword(@RequestParam String mail){
        this.service.recuperarPassword(mail);
        return ResponseEntity.ok("Contraseña autogenerada enviada al mail");
    }
}
