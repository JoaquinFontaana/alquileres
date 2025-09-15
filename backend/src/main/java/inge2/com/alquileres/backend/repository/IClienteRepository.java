package inge2.com.alquileres.backend.repository;

import inge2.com.alquileres.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente,Long> {
    boolean existsByDni(String dni);
    Optional<Cliente> findByEmail(String email);
}
