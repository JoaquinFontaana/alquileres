package inge2.com.alquileres.backend.repository;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAutoRepository extends JpaRepository<Auto,Long> {
    public boolean existsByPatente(String patente);
    public Optional<Auto> findByPatente(String patente);

    @Query("""
    SELECT a
    FROM Auto a
    WHERE a.sucursal.id = :sucursalId
      AND (ABS(a.precioPorDia - :precio) <= :rangoPrecio OR a.categoria = :categoria)
      AND a.estado = inge2.com.alquileres.backend.model.enums.EstadoAutoEnum.DISPONIBLE
      AND a.id <> :autoId
    """)
    List<Auto> findSimilaresPorPrecioOCategoria(
            @Param("sucursalId") Long sucursalId,
            @Param("precio") double precio,
            @Param("rangoPrecio") double rangoPrecio,
            @Param("categoria") CategoriaAuto categoria,
            @Param("autoId") Long autoId
    );
}
