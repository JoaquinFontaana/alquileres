package inge2.com.alquileres.backend.dto.user;

import inge2.com.alquileres.backend.model.Persona;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDTOPassword extends PersonaDTO{
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public PersonaDTOPassword(Persona persona, String password) {
        super(persona);
        this.password = password;
    }

    public PersonaDTOPassword() {
        super();
    }
}
