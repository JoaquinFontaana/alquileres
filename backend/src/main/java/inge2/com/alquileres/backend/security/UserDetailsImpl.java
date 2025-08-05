package inge2.com.alquileres.backend.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter @Setter
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private boolean cuentaActiva;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, boolean cuentaActiva, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cuentaActiva = cuentaActiva;
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return cuentaActiva;
    }
}
