package inge2.com.alquileres.backend.service.useCase;

import inge2.com.alquileres.backend.dto.alquiler.MultaAlquilerDTO;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;
import inge2.com.alquileres.backend.service.helper.AlquilerHelperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class GestionAlquilerAutoUseCase {

    private final AlquilerHelperService alquilerHelperService;
    private final AutoService autoService;
    private final AlquilerService alquilerService;

    @Transactional
    public void iniciarAlquiler(Long codigoAlquiler) {
        this.alquilerHelperService.findById(codigoAlquiler).iniciar(alquilerService,autoService);
    }

    @Transactional
    public void finalizarAlquilerCorrecto(Long codigoAlquiler) {
        this.alquilerHelperService.findById(codigoAlquiler).finalizar(autoService,alquilerService);
    }

    @Transactional
    public void finalizarAlquilerMantenimiento(MultaAlquilerDTO multaAlquilerDTO) {
        this.alquilerHelperService.findById(multaAlquilerDTO.getCodigoAlquiler())
                .finalizarConMantenimiento(alquilerService,autoService,multaAlquilerDTO.getMontoMulta());
    }

}
