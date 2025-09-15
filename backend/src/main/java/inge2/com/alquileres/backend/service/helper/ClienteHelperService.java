package inge2.com.alquileres.backend.service.helper;

import inge2.com.alquileres.backend.model.Cliente;
import inge2.com.alquileres.backend.repository.IClienteRepository;
import inge2.com.alquileres.backend.service.UsuarioService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteHelperService {

    private final IClienteRepository clienteRepository;
    private final UsuarioService usuarioService;

    public ClienteHelperService(IClienteRepository clienteRepository, UsuarioService usuarioService) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
    }

    public void checkNotExistDni(String dni){
        if(clienteRepository.existsByDni(dni)){
            throw new EntityExistsException("El dni " + dni + " ya existe");
        }
    }

    public void checkNotExistMail(String email){
        this.usuarioService.checkNotExistsEmail(email);
    }
    public boolean existByMail(String email){
        return this.usuarioService.existsByEmail(email);
    }
    public void checkNotExistsCliente(String dni,String email){
        this.checkNotExistDni(dni);
        this.checkNotExistMail(email);
    }

    public Cliente findClienteByEmail(String email){
        return this.clienteRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el email " + email + " no existe"));
    }
    public Cliente findClienteById(Long id){
        return this.clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el id " + id + " no existe"));
    }


}
