package inge2.com.alquileres.backend.service.useCase.Auto;

import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.dto.auto.AutoFilterDTO;
import inge2.com.alquileres.backend.service.builder.AutoFilterBuilder;
import inge2.com.alquileres.backend.service.filter.auto.IAutoFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarAutosUseCase {

    private final AutoFilterBuilder autoFilterBuilder;

    public ListarAutosUseCase(AutoFilterBuilder autoFilterBuilder) {
        this.autoFilterBuilder = autoFilterBuilder;
    }

    public List<AutoDTOListar> listarAutos(AutoFilterDTO opcionesFiltrado){
        IAutoFilter filter = this.autoFilterBuilder.buildFilter(opcionesFiltrado);
        return filter
                .listar()
                .stream()
                .map(AutoDTOListar::new)
                .toList();
    }

}
