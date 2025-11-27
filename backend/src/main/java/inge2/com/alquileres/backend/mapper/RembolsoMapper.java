package inge2.com.alquileres.backend.mapper;

import inge2.com.alquileres.backend.dto.alquiler.RembolsoDTO;
import inge2.com.alquileres.backend.model.Rembolso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RembolsoMapper {

    RembolsoDTO toDto(Rembolso rembolso);
}
