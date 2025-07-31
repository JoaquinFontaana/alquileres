package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.auto.AutoDTOActualizar;
import inge2.com.alquileres.backend.dto.auto.AutoDTOCrear;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.model.Sucursal;
import inge2.com.alquileres.backend.repository.IAutoRepository;
import inge2.com.alquileres.backend.service.helper.AutoHelperService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AutoService {
    private final IAutoRepository autoRepository;
    private final SucursalService sucursalService;
    private final AutoHelperService autoHelperService;
    private final FileStorageService fileStorageService;


    public AutoService(IAutoRepository autoRepository, SucursalService sucursalService, AutoHelperService autoHelperService, FileStorageService fileStorageService) {
        this.autoRepository = autoRepository;
        this.sucursalService = sucursalService;
        this.autoHelperService = autoHelperService;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void crearAuto(AutoDTOCrear autoDto){
        this.fileStorageService.checkImagen(autoDto.getImagen());
        this.autoHelperService.checkPatenteNotExists(autoDto.getPatente());

        Sucursal sucursal = this.sucursalService.findSucursalByCiudad(autoDto.getSucursal());

        String rutaImagen = fileStorageService.guardarImagen(autoDto.getImagen());

        Auto auto = new Auto(autoDto,sucursal,rutaImagen);
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
            fileStorageService.borrarArchivoSiExiste(auto.getRutaImagen());
            auto.actualizarAutoImagen(autoActualizado,sucursal,fileStorageService.guardarImagen(autoActualizado.getImagen()));
        }
        else{
            auto.actualizarAuto(autoActualizado,sucursal);
        }
        this.autoRepository.save(auto);
    }

    @Transactional
    public void finalizarMantenimiento(String patente) {
        this.autoHelperService.findAutoByPatente(patente).finalizarMantenimiento(this);
    }
}
