package inge2.com.alquileres.backend.configuration;

import inge2.com.alquileres.backend.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AdminCreation {
    private final UsuarioService usuarioService;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.mail}")
    private String adminMail;

    public AdminCreation(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void crearAdmin(){
        if(this.usuarioService.checkNotExistsAdmin()){
            this.usuarioService.crearAdmin(adminMail,adminPassword);
        }
    }

}
