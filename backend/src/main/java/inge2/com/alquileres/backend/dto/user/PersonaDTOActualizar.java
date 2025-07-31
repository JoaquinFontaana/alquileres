package inge2.com.alquileres.backend.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDTOActualizar {
    private String nuevoNombre;
    private String nuevoApellido;
    @Email(message = "Ingresa un email valido")
    @NotBlank(message = "El mail actual es obligatorio")
    private String mail;
    @Size(min = 8, max = 9, message = "El dni debe tener 8 o 9 caracteres")
    private String nuevoDni;
    @Email(message = "Ingresa un email valido")
    private String nuevoMail;

    public PersonaDTOActualizar(){

    }
}
