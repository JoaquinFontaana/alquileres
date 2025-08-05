package inge2.com.alquileres.backend.security;

import inge2.com.alquileres.backend.model.Empleado;
import inge2.com.alquileres.backend.service.EmpleadoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Value("${JWT_SECRET_KEY}")
    private String jwtSecret;
    @Value("${TOKEN_EXPIRATION}")
    private Long tokenExpiration;

    private final EmpleadoService empleadoService;

    public JWTService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    public String generateToken(Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + this.tokenExpiration);

        JwtBuilder builder = this.getBuilderToken(userPrincipal.getUsername(), expireDate, getRoles(userPrincipal));

        agregarSucursalEmpleado(getRoles(userPrincipal), userPrincipal.getId(), builder);

        return this.getCompacted(builder);

    }

    private String getCompacted(JwtBuilder builder) {
        return builder.signWith(this.getSigningKey()).compact();
    }


    private static String getRoles(UserDetailsImpl userPrincipal) {
        return userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private JwtBuilder getBuilderToken(String mail, Date expireDate, String roles) {
        return Jwts.builder()
                .setSubject(mail)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles);
    }

    private void agregarSucursalEmpleado(String roles, Long userId, JwtBuilder builder) {
        if (roles.contains("EMPLEADO")) {
            Empleado empleado = this.empleadoService.findEmpleadoById(userId);
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
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
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
