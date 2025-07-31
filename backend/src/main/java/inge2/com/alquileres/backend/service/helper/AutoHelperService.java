package inge2.com.alquileres.backend.service.helper;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import inge2.com.alquileres.backend.repository.IAutoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoHelperService {

    private final IAutoRepository autoRepository;

    public AutoHelperService(IAutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public void verificarDisponibilidad(Auto auto, RangoFecha rangoFecha){
        if(!auto.esAlquilable()){
            throw new IllegalStateException("El auto no se encuentra en condiciones de ser alquilado, se encuentra en estado: " + auto.getEstado());
        }
        if(!auto.disponibleEnRangoFechas( rangoFecha)) {
            throw new IllegalStateException("El auto no se encuentra disponible en el rango de fechas solicitado: " + rangoFecha);
        }
    }

    public void checkPatenteNotExists(String patente) {
        if (autoRepository.existsByPatente(patente)) {
            throw new EntityExistsException("La patente " + patente + " ya se encuentra registrada");
        }
    }

    public Auto findAutoByPatente(String patente) {
        return autoRepository.findByPatente(patente)
                .orElseThrow(() -> new EntityNotFoundException("La patente " + patente + " no existe"));
    }

    public void checkAutoNoDisponible(Auto auto) {
        if (auto.estaDisponible()) {
            throw new RuntimeException("El auto se encuentra disponible no hace falta cambiarlo");
        }
    }
    public List<Auto> findSimilaresPorPrecioOCategoria(Auto auto) {
        return autoRepository.findSimilaresPorPrecioOCategoria(auto.getSucursal().getId(), auto.getPrecioPorDia(), 2000, auto.getCategoria(), auto.getId());
    }
}
