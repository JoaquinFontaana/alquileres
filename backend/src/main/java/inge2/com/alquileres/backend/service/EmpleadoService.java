package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.dto.user.EmpleadoDTO;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileres.backend.dto.user.EmpleadoDTOListar;
import inge2.com.alquileres.backend.model.Empleado;
import inge2.com.alquileres.backend.repository.IEmpleadoRepository;
import inge2.com.alquileres.backend.service.helper.EmpleadoHelperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class EmpleadoService {

    private final IEmpleadoRepository repository;
    private final EmpleadoHelperService empleadoHelper;
    private final SucursalService sucursalService;
    private final RolService rolService;
    private final EncryptService encryptService;
    private final EmailService emailService;


    @Transactional
    public void crearEmpleado(EmpleadoDTO dto){
        this.empleadoHelper.checkNotExistsEmpleado(dto);

        Empleado empleado = new Empleado(
                dto,
                this.sucursalService.findSucursalByCiudad(dto.getTrabajaEnSucursal()),
                rolService.findRolByNombre("EMPLEADO"),
                encryptService.encryptPassword(this.emailService.sendContraseñaAutoGenerada(dto.getEmail()))
        );
        this.repository.save(empleado);
    }

    @Transactional
    public void actualizarEmpleado(EmpleadoDTOActualizar empleadoDTO){
        Empleado empleado = this.empleadoHelper.findByMail(empleadoDTO.getEmail());

        this.empleadoHelper.checkDTO(empleadoDTO);

        if(empleadoDTO.getNuevaSucursal() != null){
            empleado.actualizarDatos(empleadoDTO,this.sucursalService.findSucursalByCiudad(empleadoDTO.getNuevaSucursal()));
        }
        else empleado.actualizarDatos(empleadoDTO);
        this.repository.save(empleado);
    }

    public List<EmpleadoDTOListar> listarEmpleados(){
        return this.repository.findAll().stream().map(EmpleadoDTOListar::new).toList();
    }

    public void eliminarEmpleado(String email) {
        Empleado empleado = this.empleadoHelper.findByMail(email);
        empleado.eliminar();
        this.repository.save(empleado);
    }

    public Empleado findEmpleadoById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));
    }
}
