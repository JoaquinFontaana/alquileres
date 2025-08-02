package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.AuthResponseDTO;
import inge2.com.alquileres.backend.dto.LoginDTO;
import inge2.com.alquileres.backend.security.JWTService;
import inge2.com.alquileres.backend.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class AuthService {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDTO login (LoginDTO loginDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return new AuthResponseDTO( jwtService.generateToken(authentication));
    }

}
