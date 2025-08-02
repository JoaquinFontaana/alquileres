package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.AuthResponseDTO;
import inge2.com.alquileres.backend.dto.LoginDTO;
import inge2.com.alquileres.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        AuthResponseDTO authResponse = authService.login(loginDto);
        return ResponseEntity.ok().body(authResponse);
    }


}
