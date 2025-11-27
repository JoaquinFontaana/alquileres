package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.AuthResponseDTO;
import inge2.com.alquileres.backend.dto.LoginDTO;
import inge2.com.alquileres.backend.security.JWTService;
import inge2.com.alquileres.backend.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service @AllArgsConstructor
public class    AuthService {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDTO login (LoginDTO loginDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthResponseDTO( jwtService.generateToken(authentication));
    }

    public String getMailOfContext(){
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        if(!authContext.isAuthenticated()){
            throw new AuthenticationCredentialsNotFoundException("El usuario no esta autenticado no se puede obtener el email del context");
        }
        Object principal = authContext.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getUsername();
        } else {
            throw new AuthenticationCredentialsNotFoundException("Principal no válido. No se puede obtener el email.");
        }
    }
}
