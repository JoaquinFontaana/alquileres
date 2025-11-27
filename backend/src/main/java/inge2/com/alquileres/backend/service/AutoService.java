package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.auto.AutoDTOActualizar;
import inge2.com.alquileres.backend.dto.auto.AutoDTOCrear;
import inge2.com.alquileres.backend.mapper.AutoMapper;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.repository.IAutoRepository;
import inge2.com.alquileres.backend.service.helper.AutoHelperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class AutoService {
    private final IAutoRepository autoRepository;
    private final SucursalService sucursalService;
    private final AutoHelperService autoHelperService;
    private final FileStorageService fileStorageService;
    private final AutoMapper autoMapper;

    @Transactional
    public void crearAuto(AutoDTOCrear autoDto){
        this.fileStorageService.checkImagen(autoDto.getImagen());
        this.autoHelperService.checkPatenteNotExists(autoDto.getPatente());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoDto.getSucursal());

        String rutaImagen = fileStorageService.guardarImagen(autoDto.getImagen());

        Auto auto = this.autoMapper.toEntiity(autoDto,sucursal,rutaImagen);
        autoRepository.save(auto);
    }

    @Transactional
    public void saveAuto(Auto auto){
        this.autoRepository.save(auto);
    }

    @Transactional
    public void actualizarAuto(AutoDTOActualizar autoActualizado){
        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoActualizado.getSucursal());
        Auto auto = this.autoHelperService.findAutoByPatente(autoActualizado.getPatente());
        if(autoActualizado.getImagen() != null){
            fileStorageService.deleteImage(auto.getRutaImagen());
            auto.setRutaImagen(fileStorageService.guardarImagen(autoActualizado.getImagen()));
        }
        autoMapper.updateFromDtoActualizar(autoActualizado,sucursal,auto);
        this.autoRepository.save(auto);
    }

    @Transactional
    public void finalizarMantenimiento(String patente) {
        this.autoHelperService.findAutoByPatente(patente).finalizarMantenimiento(this);
    }
}
