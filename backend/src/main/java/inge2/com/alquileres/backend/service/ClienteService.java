package inge2.com.alquileres.backend.service;


import inge2.com.alquileres.backend.dto.alquiler.RembolsoDTO;
import inge2.com.alquileres.backend.dto.alquiler.AlquilerDTOListar;
import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import inge2.com.alquileres.backend.dto.user.PersonaDTOPassword;
import inge2.com.alquileres.backend.model.Cliente;
import inge2.com.alquileres.backend.repository.IClienteRepository;
import inge2.com.alquileres.backend.service.helper.ClienteHelperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class ClienteService {

    private final EncryptService encryptService;
    private final IClienteRepository clienteRepository;
    private final RolService rolService ;
    private final ClienteHelperService clienteHelperService;
    private final EmailService emailService;

    @Transactional
    public void crearCliente(PersonaDTOPassword clienteDTO){
        this.clienteHelperService.checkNotExistsCliente(clienteDTO.getDni(),clienteDTO.getMail());

        clienteDTO.setPassword(encryptService.encryptPassword(clienteDTO.getPassword()));
        Cliente cliente = new Cliente(clienteDTO, rolService.findRolByNombre("CLIENT"));

        clienteRepository.save(cliente);
    }

    @Transactional
    public void registrarClientePresencial(PersonaDTO clienteDTO){
        this.clienteHelperService.checkNotExistsCliente(clienteDTO.getDni(),clienteDTO.getMail());

        Cliente cliente = new Cliente(
                clienteDTO,
                rolService.findRolByNombre("CLIENT"),
                encryptService.encryptPassword(this.emailService.sendContraseñaAutoGenerada(clienteDTO.getMail()))
        );
        this.clienteRepository.save(cliente);
    }

    @Transactional
    public void registrarPagoMulta(String idCliente){
        Cliente cliente = this.clienteHelperService.findClienteById(Long.parseLong(idCliente));
        cliente.setMontoMulta(0);
        this.clienteRepository.save(cliente);
    }

    public List<AlquilerDTOListar> listarAlquileres(String mail){
        return this.clienteHelperService.findClienteByEmail(mail)
                .getAlquileres()
                .stream()
                .map(AlquilerDTOListar::new)
                .toList();
    }

    public List<PersonaDTO> listarClientes(){
        return this.clienteRepository.findAll().stream().map(PersonaDTO::new).toList();
    }

    public double getMulta(String mail){
        return this.clienteHelperService.findClienteByEmail(mail).getMontoMulta();
    }

    public List<RembolsoDTO> listarRembolsos(String mail){
        return this.clienteHelperService.findClienteByEmail(mail)
                .getRembolsos()
                .stream()
                .map(RembolsoDTO::new)
                .toList();
    }
    public boolean existsCliente(String mail) {
        return this.clienteHelperService.existByMail(mail);
    }

}
