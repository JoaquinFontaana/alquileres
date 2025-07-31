package inge2.com.alquileres.backend.service.helper;

import inge2.com.alquileres.backend.dto.user.EmpleadoDTOActualizar;
import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import inge2.com.alquileres.backend.model.Empleado;
import inge2.com.alquileres.backend.repository.IEmpleadoRepository;
import inge2.com.alquileres.backend.service.UsuarioService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoHelperService {
    private final IEmpleadoRepository empleadoRepository;
    private final UsuarioService usuarioService;

    public EmpleadoHelperService(IEmpleadoRepository empleadoRepository, UsuarioService usuarioService) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioService = usuarioService;
    }

    public void checkNotExistsDNI(String dni){
        if(empleadoRepository.existsByDni(dni)){
            throw new EntityExistsException("El dni " + dni + " ya existe");
        }
    }

    public void checkNotExistMail(String mail){
        this.usuarioService.checkNotExistsMail(mail);
    }

    public void checkNotExistsEmpleado(PersonaDTO dto){
        this.checkNotExistsDNI(dto.getDni());
        this.checkNotExistMail(dto.getMail());
    }

    public Empleado findByMail(String mail){
        return this.empleadoRepository.findByMail(mail)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con mail " + mail + " no existe"));
    }

    public void checkDTO(EmpleadoDTOActualizar empleadoDTO){
        if(empleadoDTO.getNuevoMail() != null){
            this.checkNotExistMail(empleadoDTO.getNuevoMail());
        }
        if(empleadoDTO.getNuevoDni() != null){
            this.checkNotExistsDNI(empleadoDTO.getNuevoDni());
        }
    }

}
