package inge2.com.alquileres.backend.controller;

import inge2.com.alquileres.backend.dto.AuthResponseDTO;
import inge2.com.alquileres.backend.dto.LoginDTO;
import inge2.com.alquileres.backend.security.JWTGenerator;
import inge2.com.alquileres.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UsuarioService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator tokenGenerator;

    public AuthController(UsuarioService userService, AuthenticationManager authenticationManager, JWTGenerator tokenGenerator) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getMail(),
                        loginDto.getPassword()
                )
        );
        this.userService.sendDobleAutenticacion(loginDto.getMail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =  tokenGenerator.generateToken(authentication);
        return ResponseEntity.ok().body(new AuthResponseDTO(token));
    }

    @GetMapping("/dobleAutenticacion")
    public boolean confimarCodigo(@RequestParam int cod){
        return this.userService.confirmarCodigo(cod);
    }
}
