package inge2.com.alquileres.backend.security;

import inge2.com.alquileres.backend.model.Rol;
import inge2.com.alquileres.backend.model.Usuario;
import inge2.com.alquileres.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@Primary
public class CustomDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    public UserDetails loadUserByUsername (String mail){
         Usuario user = this.usuarioService.findByEmail(mail);
         return new UserDetailsImpl(user.getId(), user.getMail(),
                    user.getPassword(), this.mapRolesToAuthorities(user.getRol()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(Rol rol){
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
    }
}
