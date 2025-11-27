package inge2.com.alquileres.backend.service.useCase.Alquiler;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOFilter;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.mapper.AlquilerMapper;
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
    private final AlquilerMapper alquilerMapper;
    public List<AlquilerDTOListar> listarAlquileres(AlquilerDTOFilter filtros) {
        return alquilerMapper.toDtoListListar(filterBuilder.buildFilter(filtros).getAlquileres());
    }
    
    public List<AlquilerDTOListar> listarPendientesEntrega(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        List<Alquiler> retiroDisponibles = this.alquilerService.findRetiroPendienteByCiudad(ciudad)
                .stream()
                .filter(Alquiler::retiroDisponible)
                .toList();
        return alquilerMapper.toDtoListListar(retiroDisponibles);
    }


    public List<AlquilerDTOListar> listarPendientesDevolucion(String ciudad) {
        this.sucursalService.findSucursalByCiudad(ciudad);
        return this.alquilerMapper.toDtoListListar(this.alquilerService.findEnUsoByCiudad(ciudad));
    }
}
