package inge2.com.alquileres.backend.configuration;

import inge2.com.alquileres.backend.model.Rol;
import inge2.com.alquileres.backend.repository.IRolRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component @AllArgsConstructor
public class RoleDataLoader {

    private final IRolRepository rolRepository;

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
