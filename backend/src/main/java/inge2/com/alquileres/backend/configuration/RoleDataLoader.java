package inge2.com.alquileres.backend.configuration;

import inge2.com.alquileres.backend.model.Rol;
import inge2.com.alquileres.backend.repository.IRolRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader {
    @Autowired
    private IRolRepository rolRepository;

    @PostConstruct
    @Transactional
    public void loadRoles() {
        createIfNotExists("ADMIN");
        createIfNotExists("EMPLEADO");
        createIfNotExists("CLIENT");
    }

    private void createIfNotExists(String rolName) {
        rolRepository.findByNombre(rolName)
                .orElseGet(() -> rolRepository.save(new Rol(rolName)));
    }
}
