package inge2.com.alquileres.backend.service.helper;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.valueObject.RangoFecha;
import inge2.com.alquileres.backend.repository.IAlquilerRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

@Service
public class AlquilerHelperService {

    private final IAlquilerRepository repository;

    public AlquilerHelperService(IAlquilerRepository repository) {
        this.repository = repository;
    }

    public void checkDisponibilidadConductor(RangoFecha rangoFecha,String licencia){
        this.repository.findAlquilerByLicenciaConductorAndRangoFecha(licencia,rangoFecha.getFechaDesde(),rangoFecha.getFechaHasta())
                .ifPresent(a ->{
                    throw new EntityExistsException("El conductor con licencia " + licencia +  " ya tiene un alquiler en el rango de fecha solicitado");
                });
    }

    public void checkDuracionAlquiler(RangoFecha rangoFecha){
        if(rangoFecha.cantidadDeDias() > 14){
            throw new IllegalArgumentException("La duracion maxima de un alquiler son 14 dias");
        }
    }

    public Alquiler findById(Long codigoAlquiler) {
        return this.repository.findById(codigoAlquiler)
                .orElseThrow(() -> new IllegalArgumentException("El alquiler con codigo " + codigoAlquiler + " no existe"));
    }
}

