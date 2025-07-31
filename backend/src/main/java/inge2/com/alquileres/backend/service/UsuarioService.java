package inge2.com.alquileres.backend.service;

import inge2.com.alquileres.backend.model.Usuario;
import inge2.com.alquileres.backend.repository.IUsuarioRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UsuarioService {
    private final IUsuarioRepository usuarioRepository;
    private final RolService rolService;
    private final EncryptService encryptService;
    private final PasswordGenerator passwordGenerator;
    private final EmailService emailService;
    private final Random random = new Random();
    private final List<Integer> codigos = List.of(1234, 5678, 9012, 3456, 7890);

    @Autowired
    public UsuarioService(RolService rolService, IUsuarioRepository usuarioRepository, EncryptService encryptService,
                          PasswordGenerator passwordGenerator, EmailService emailService) {
        this.rolService = rolService;
        this.usuarioRepository = usuarioRepository;
        this.encryptService = encryptService;
        this.passwordGenerator = passwordGenerator;
        this.emailService = emailService;
    }

    @Transactional
    public void crearAdmin(String mail,String password){
        String encryptedPassword = encryptService.encryptPassword(password);
        this.usuarioRepository.save(new Usuario(encryptedPassword,mail,this.rolService.findRolByNombre("ADMIN")));
    }

    public boolean checkNotExistsAdmin(){
        return this.usuarioRepository.findUsuarioByRolNombre("ADMIN").isEmpty();
    }

    public Usuario findByEmail(String mail) {
        return usuarioRepository.findByMail(mail)
                .orElseThrow(()-> new EntityNotFoundException("El usuario con mail "+ mail + " no se encontro"));
    }

    public void checkNotExistsMail(String mail){
        if(usuarioRepository.existsByMail(mail)){
            throw new EntityExistsException("El mail "+ mail + " ya se encuentra registrado");
        }
    }

    public void recuperarPassword(String mail){
        Usuario user = this.findByEmail(mail);
        String password = passwordGenerator.generatePassword();
        emailService.sendNewPassword(password, mail);
        user.modificarPassword(encryptService.encryptPassword(password));
        usuarioRepository.save(user);
    }

    public void sendDobleAutenticacion(String mail){
        if (this.findByEmail(mail).isAdmin()){
            this.emailService.sendDobleAutenticacionAdmin(mail, codigos.get(random.nextInt(codigos.size())));
        }
    }

    public boolean confirmarCodigo(int cod){
        if(this.codigos.contains(cod)){
            return true;
        }
        else{
            throw new RuntimeException("Codigo incorrecto");
        }
    }
}
