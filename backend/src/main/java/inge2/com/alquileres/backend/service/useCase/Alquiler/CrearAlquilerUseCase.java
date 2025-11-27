package inge2.com.alquileres.backend.service.useCase.Alquiler;

import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOCrear;
import inge2.com.alquileres.backend.mapper.AlquilerMapper;
import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Cliente;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.SucursalService;
import inge2.com.alquileres.backend.service.helper.AlquilerHelperService;
import inge2.com.alquileres.backend.service.helper.AutoHelperService;
import inge2.com.alquileres.backend.service.helper.ClienteHelperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class CrearAlquilerUseCase {

    private final AlquilerHelperService alquilerHelperService;
    private final AutoHelperService autoHelperService;
    private final SucursalService sucursalService;
    private final ClienteHelperService clienteHelperService;
    private final AlquilerService alquilerService;
    private final AlquilerMapper alquilerMapper;

    @Transactional
    public Alquiler crearAlquiler(AlquilerDTOCrear alquilerDTO, String email){
        this.alquilerHelperService.checkDuracionAlquiler(alquilerDTO.getRangoFecha());
        this.alquilerHelperService.checkDisponibilidadConductor(alquilerDTO.getRangoFecha(),alquilerDTO.getLicenciaConductor());

        Auto auto = this.autoHelperService.findAutoByPatente(alquilerDTO.getPatenteAuto());
        this.autoHelperService.verificarDisponibilidad(auto,alquilerDTO.getRangoFecha());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(alquilerDTO.getSucursal());

        Cliente cliente = this.clienteHelperService.findClienteByEmail(email);

        Alquiler alquiler = alquilerMapper.toEntity(alquilerDTO,auto,cliente,sucursal);

        return this.alquilerService.saveAlquiler(alquiler);
    }

}
