package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.model.Rol;
import inge2.com.alquileres.backend.repository.IRolRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class RolService {

    private IRolRepository rolRepository;

    public Rol findRolByNombre(String nombre) {
        return  this.rolRepository.findByNombre(nombre).orElseThrow(() -> new EntityExistsException("El rol" + nombre + "no existe"));
    }
}
