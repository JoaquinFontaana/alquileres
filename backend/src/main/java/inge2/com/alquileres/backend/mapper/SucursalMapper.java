package inge2.com.alquileres.backend.mapper;

import inge2.com.alquileres.backend.dto.SucursalDTO;
import inge2.com.alquileres.backend.model.Sucursal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SucursalMapper {

    SucursalDTO toDto(Sucursal sucursal);

    List<SucursalDTO> toDtoList (List<Sucursal> sucursales);

    @Mapping(target = "id", ignore = true)
    Sucursal toEntity(SucursalDTO sucursalDTO);

}
