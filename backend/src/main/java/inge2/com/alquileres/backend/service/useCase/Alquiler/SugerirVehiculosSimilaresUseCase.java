package inge2.com.alquileres.backend.service.useCase.Alquiler;

import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.service.helper.AlquilerHelperService;
import inge2.com.alquileres.backend.service.helper.AutoHelperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class SugerirVehiculosSimilaresUseCase {
    private final AlquilerHelperService alquilerHelperService;
    private final AutoHelperService autoHelperService;

    public List<AutoDTOListar> sugerirSimilares(Long codigoAlquiler) {
        Alquiler alquiler = this.alquilerHelperService.findById(codigoAlquiler);
        this.autoHelperService.checkAutoNoDisponible(alquiler.getAuto());
        return this.autoHelperService.findSimilaresPorPrecioOCategoria(alquiler.getAuto())
                .stream()
                .filter(auto -> auto.disponibleEnRangoFechas(alquiler.getRangoFecha()))
                .map(AutoDTOListar::new)
                .toList();
    }
}
