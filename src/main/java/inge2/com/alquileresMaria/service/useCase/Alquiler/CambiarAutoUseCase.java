package inge2.com.alquileresMaria.service.useCase.Alquiler;

import inge2.com.alquileresMaria.dto.alquiler.AlquilerDTOCambiarAuto;
import inge2.com.alquileresMaria.dto.auto.AutoDTOListar;
import inge2.com.alquileresMaria.model.Alquiler;
import inge2.com.alquileresMaria.model.Auto;
import inge2.com.alquileresMaria.service.AlquilerService;
import inge2.com.alquileresMaria.service.helper.AlquilerHelperService;
import inge2.com.alquileresMaria.service.helper.AutoHelperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CambiarAutoUseCase {
    private final AlquilerHelperService alquilerHelperService;
    private final AutoHelperService autoHelperService;
    private final AlquilerService alquilerService;
    private final SugerirVehiculosSimilaresUseCase sugerirVehiculosSimilares;
    
    public CambiarAutoUseCase(AlquilerHelperService alquilerHelperService, AutoHelperService autoHelperService, AlquilerService alquilerService, SugerirVehiculosSimilaresUseCase sugerirVehiculosSimilares) {
        this.alquilerHelperService = alquilerHelperService;
        this.autoHelperService = autoHelperService;
        this.alquilerService = alquilerService;
        this.sugerirVehiculosSimilares = sugerirVehiculosSimilares;
    }

    public void cambiarAuto(AlquilerDTOCambiarAuto alquilerDTOCambiarAuto){
        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTOCambiarAuto.getPatenteAutoNuevo());

        checkVehiculoIsSimiliar(auto.getPatente(), alquilerDTOCambiarAuto.getCodigoAlquiler());

        Alquiler alquiler = this.alquilerHelperService.findById(alquilerDTOCambiarAuto.getCodigoAlquiler());
        alquiler.setAuto(auto);
        alquilerService.saveAlquiler(alquiler);
    }


    private void checkVehiculoIsSimiliar(String patenteAutoNuevo,Long codigoAlquiler) {
        List<String> patentes = this.sugerirVehiculosSimilares.sugerirSimilares(codigoAlquiler)
                .stream()
                .map(AutoDTOListar::getPatente)
                .toList();

        if(!patentes.contains(patenteAutoNuevo)){
            throw new IllegalArgumentException("El auto seleccionado para el cambio no es similar al auto actual");
        }
    }
}
