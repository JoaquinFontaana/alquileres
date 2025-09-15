package inge2.com.alquileres.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final PasswordGenerator passwordGenerator;

    public EmailService(JavaMailSender mailSender, PasswordGenerator passwordGenerator) {
        this.mailSender = mailSender;
        this.passwordGenerator = passwordGenerator;
    }

    @Async
    public void sendEmail(String to, String subject, String body){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("AlquileresMariaSupport@gmail.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Async
    public void sendNewPassword(String password, String email){
        String subject = "Recuperar Contraseña";
        String body = "Su nueva contraseña es: "+ password;
        this.sendEmail(email, subject, body);
    }

    @Async
    public void sendDobleAutenticacionAdmin(String email, int cod){
        String subject = "Doble autenticación";
        String body = "El código es: " + cod;
        this.sendEmail(email, subject, body);
    }
    public String sendContraseñaAutoGenerada(String email){
        String password = this.passwordGenerator.generatePassword();
        this.sendEmail(email,"Contraseña de su nueva cuenta en Alquileres maria","Contraseña: "+ password);
        return password;
    }
}
