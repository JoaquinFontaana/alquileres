package inge2.com.alquileres.backend.mapper;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOCambiarAuto;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOCrear;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Cliente;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.model.enums.EstadoAlquilerEnum;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", imports = {EstadoAlquilerEnum.class, ArrayList.class} ,uses = {AutoMapper.class,RembolsoMapper.class})
public interface AlquilerMapper {
    @Mapping(source = "cliente.email", target = "clienteEmail")
    @Mapping(source ="sucursal.ciudad", target = "sucursal")
    @Mapping(source="pago.monto", target="monto")
    @Mapping(source="pago.estadoPago", target="estadoPago")
    @Mapping(source="pago.initPoint", target="urlPago")
    AlquilerDTOListar toDtoListar(Alquiler alquiler);

    List<AlquilerDTOListar> toDtoListListar(List<Alquiler> alquileres);

    @Mapping(source = "id", target = "codigoAlquiler")
    @Mapping(source = "auto.patente", target = "patenteAutoNuevo")
    AlquilerDTOCambiarAuto toDtoCambiarAuto(Alquiler alquiler);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "rembolso", ignore = true)
    @Mapping(target = "state", ignore = true) // Transient
    // 2. Mapeos directos desde los argumentos (Auto, Cliente, Sucursal)
    @Mapping(source = "auto", target = "auto")
    @Mapping(source = "cliente", target = "cliente")
    @Mapping(source = "sucursal", target = "sucursal")
    // 3. Mapeos desde el DTO
    @Mapping(source = "dto.licenciaConductor", target = "licenciaConductor")
    @Mapping(source = "dto.rangoFecha", target = "rangoFecha")
    @Mapping(source = "dto.extras", target = "extras") // MapStruct maneja la lista automáticamente
    // 4. Valores constantes/default
    @Mapping(target = "estadoAlquilerEnum", expression = "java(EstadoAlquilerEnum.CONFIRMACION_PENDIENTE)")
    Alquiler toEntity(AlquilerDTOCrear dto, Auto auto, Cliente cliente, Sucursal sucursal);

    @AfterMapping
    default  void handleAfterMapping(AlquilerDTOCrear dto, @MappingTarget Alquiler alquiler) {
        if(alquiler.getExtras() == null) {
            alquiler.setExtras(new ArrayList<>());
        }
    }
}
