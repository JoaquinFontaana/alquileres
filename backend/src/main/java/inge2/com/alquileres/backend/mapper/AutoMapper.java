package inge2.com.alquileres.backend.mapper;

import inge2.com.alquileres.backend.dto.auto.AutoDTO;
import inge2.com.alquileres.backend.dto.auto.AutoDTOActualizar;
import inge2.com.alquileres.backend.dto.auto.AutoDTOListar;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", imports = {EstadoAutoEnum.class})
public interface AutoMapper {
    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(target = "rutaImagen", ignore = true)
    @Mapping(target = "patente", ignore = true)
    void updateFromDtoActualizar(AutoDTOActualizar dto, Sucursal sucursal, @MappingTarget Auto auto);

    @Mapping(source = "rutaImagen", target = "imgUrl")
    @Mapping(source = "sucursal.ciudad", target = "sucursal")
    AutoDTOListar toDtoListar(Auto auto);

    List<AutoDTOListar> toDtoListListar(List<Auto> autos);

    @Mapping(source = "sucursal", target = "sucursal")
    @Mapping(source = "rutaImagen",target = "rutaImagen")
    @Mapping(target = "estado", expression = "java(EstadoAutoEnum.DISPONIBLE)")
    Auto toEntity(AutoDTO dto,Sucursal sucursal, String rutaImagen);

}
