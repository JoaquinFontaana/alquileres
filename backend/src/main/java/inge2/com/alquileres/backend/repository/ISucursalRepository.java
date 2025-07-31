package inge2.com.alquileres.backend.repository;

import inge2.com.alquileres.backend.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISucursalRepository extends JpaRepository<Sucursal,Long> {
    public boolean existsByCiudad(String ciudad);
    public Optional<Sucursal> findByCiudad(String ciudad);
}
