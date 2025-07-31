package inge2.com.alquileres.backend.repository;

import inge2.com.alquileres.backend.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmpleadoRepository extends JpaRepository<Empleado,Long>{
    boolean existsByMail(String mail);
    boolean existsByDni(String dni);
    Optional<Empleado> findByMail(String mail);
}
