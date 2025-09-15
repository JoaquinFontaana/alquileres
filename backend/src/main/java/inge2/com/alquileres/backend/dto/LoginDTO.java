package inge2.com.alquileres.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class LoginDTO {

    @Email(message = "Ingresa un email valido")
    @NotBlank (message = "El email no puede estar vacio")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
