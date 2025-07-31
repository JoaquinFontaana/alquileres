package inge2.com.alquileres.backend.repository;

import inge2.com.alquileres.backend.model.Pago;
import inge2.com.alquileres.backend.model.enums.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IPagoRepository extends JpaRepository<Pago,Long> {
    Optional<Pago> findByAlquiler_id(Long alquilerId);

    @Query("""
    SELECT p
    FROM Pago p
    WHERE p.fechaExpiracion <= :time
    AND p.estadoPago = :estadoPago
    """)
    List<Pago> findPagosExpirados(
            @Param("time") OffsetDateTime time,
            @Param("estadoPago") EstadoPago estadoPago
    );
}
