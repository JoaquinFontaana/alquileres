package inge2.com.alquileres.backend.dto.user;

import inge2.com.alquileres.backend.model.Persona;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Ingresa un email valido")
    private String email;
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 9, message = "El dni debe tener 8 o 9 caracteres")
    private String dni;


    public PersonaDTO(Persona persona) {
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.email = persona.getEmail();
        this.dni = persona.getDni();
    }
    public PersonaDTO(){

    }
}
