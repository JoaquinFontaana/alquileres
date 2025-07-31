package inge2.com.alquileres.backend.service.helper;

import inge2.com.alquileres.backend.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthHelperService {
     public String getMailOfContext(){
         Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
         if(!authContext.isAuthenticated()){
             throw new AuthenticationCredentialsNotFoundException("El usuario no esta autenticado no se puede obtener el mail del context");
         }
         Object principal = authContext.getPrincipal();

         if (principal instanceof UserDetailsImpl) {
             return ((UserDetailsImpl) principal).getUsername();
         } else {
             throw new AuthenticationCredentialsNotFoundException("Principal no válido. No se puede obtener el mail.");
         }
     }
}
