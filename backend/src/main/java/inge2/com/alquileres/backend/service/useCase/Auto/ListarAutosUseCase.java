package inge2.com.alquileres.backend.service.useCase.Auto;

import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.dto.auto.AutoFilterDTO;
import inge2.com.alquileres.backend.mapper.AutoMapper;
import inge2.com.alquileres.backend.service.builder.AutoFilterBuilder;
import inge2.com.alquileres.backend.service.filter.auto.IAutoFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListarAutosUseCase {

    private final AutoFilterBuilder autoFilterBuilder;
    private final AutoMapper autoMapper;

    public List<AutoDTOListar> listarAutos(AutoFilterDTO opcionesFiltrado){
        IAutoFilter filter = this.autoFilterBuilder.buildFilter(opcionesFiltrado);
        return this.autoMapper.toDtoListListar(filter.listar());
    }

}
