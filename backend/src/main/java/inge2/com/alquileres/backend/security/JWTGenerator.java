package inge2.com.alquileres.backend.security;

import inge2.com.alquileres.backend.configuration.JWTConfig;
import inge2.com.alquileres.backend.model.Empleado;
import inge2.com.alquileres.backend.service.EmpleadoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {

    private final JWTConfig jwtConfig;
    private final EmpleadoService empleadoService;

    public JWTGenerator(JWTConfig JWTConfig, EmpleadoService empleadoService) {
        this.jwtConfig = JWTConfig;
        this.empleadoService = empleadoService;
    }

    public String generateToken(Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtConfig.getTOKEN_EXPIRATION());

        String roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Generar el token
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .claim("roles", roles);

        // Agregar sucursal si el rol es EMPLEADO
        agregarSucursalEmpleado(roles, userPrincipal, builder);

        return builder.signWith(this.getSigningKey()).compact();
    }

    private void agregarSucursalEmpleado(String roles, UserDetailsImpl userPrincipal, JwtBuilder builder) {
        if (roles.contains("EMPLEADO")) {
            Empleado empleado = this.empleadoService.findEmpleadoById(userPrincipal.getId());
                builder.claim("sucursal", empleado.getTrabajaEnSucursal().getCiudad());
            }
    }

    public String getUsernameJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getJWT_SECRET_KEY());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token);
        return true;
    }
}
