package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.model.Usuario;
import inge2.com.alquileres.backend.repository.IUsuarioRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service @AllArgsConstructor
public class UsuarioService {
    private final IUsuarioRepository usuarioRepository;
    private final RolService rolService;
    private final EncryptService encryptService;
    private final PasswordGenerator passwordGenerator;
    private final EmailService emailService;


    @Transactional
    public void crearAdmin(String email,String password){
        String encryptedPassword = encryptService.encryptPassword(password);
        this.usuarioRepository.save(new Usuario(encryptedPassword,email,this.rolService.findRolByNombre("ADMIN")));
    }

    public boolean checkNotExistsAdmin(){
        return this.usuarioRepository.findUsuarioByRolNombre("ADMIN").isEmpty();
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con email "+ email + " no se encontro"));
    }

    public void checkNotExistsEmail(String email){
        if(usuarioRepository.existsByEmail(email)){
            throw new EntityExistsException("El email "+ email + " ya se encuentra registrado");
        }
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void recuperarPassword(String email){
        Usuario user = this.findByEmail(email);
        String password = passwordGenerator.generatePassword();
        emailService.sendNewPassword(password, email);
        user.modificarPassword(encryptService.encryptPassword(password));
        usuarioRepository.save(user);
    }

}
