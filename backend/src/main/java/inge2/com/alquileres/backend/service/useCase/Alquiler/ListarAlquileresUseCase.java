package inge2.com.alquileres.backend.service.useCase.Alquiler;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.SucursalService;
import inge2.com.alquileres.backend.service.builder.AlquilerFilterBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class ListarAlquileresUseCase {
    private final AlquilerFilterBuilder filterBuilder;
    private final SucursalService sucursalService;
    private final AlquilerService alquilerService;

    public List<AlquilerDTOListar> listarAlquileres(AlquilerDTOFilter filtros) {
        return filterBuilder.buildFilter(filtros)
                .getAlquileres()
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }
    
    public List<AlquilerDTOListar> listarPendientesEntrega(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.alquilerService.findRetiroPendienteByCiudad(ciudad)
                .stream()
                .filter(Alquiler::retiroDisponible)
                .map(AlquilerDTOListar::new)
                .toList();
    }


    public List<AlquilerDTOListar> listarPendientesDevolucion(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.alquilerService.findEnUsoByCiudad(ciudad)
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }
}
