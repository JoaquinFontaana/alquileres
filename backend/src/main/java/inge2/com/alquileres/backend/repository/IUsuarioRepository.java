package inge2.com.alquileres.backend.repository;

import inge2.com.alquileres.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findUsuarioByRolNombre(String rol);
    boolean existsByEmail(String email);
}
