package inge2.com.alquileres.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class LoginDTO {

    @Email(message = "Ingresa un email valido")
    @NotBlank (message = "El mail no puede estar vacio")
    private String mail;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;

    public LoginDTO(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}
