package inge2.com.alquileres.backend.security;

import inge2.com.alquileres.backend.model.Rol;
import inge2.com.alquileres.backend.model.Usuario;
import inge2.com.alquileres.backend.service.EmpleadoService;
import inge2.com.alquileres.backend.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class CustomDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;
    private final EmpleadoService empleadoService;

    public CustomDetailsService(UsuarioService usuarioService, EmpleadoService empleadoService) {
        this.usuarioService = usuarioService;
        this.empleadoService = empleadoService;
    }

    @Override
    public UserDetails loadUserByUsername (String mail) throws UsernameNotFoundException {
        boolean cuentaActiva = true;
        Usuario user = this.usuarioService.findByEmail(mail);

        if(user.getRol().equals("EMPLEADO")){
            cuentaActiva = this.empleadoService.findEmpleadoById(user.getId()).isActivo();
        }

         return new UserDetailsImpl(user.getId(), user.getMail(), user.getPassword(), cuentaActiva,this.mapRolesToAuthorities(user.getRol()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Rol rol){
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
    }
}
