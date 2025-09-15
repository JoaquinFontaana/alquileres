package inge2.com.alquileres.backend.model;

import inge2.com.alquileres.backend.dto.user.PersonaDTO;
import inge2.com.alquileres.backend.dto.user.PersonaDTOActualizar;
import inge2.com.alquileres.backend.dto.user.PersonaDTOPassword;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass @Getter @Setter
public abstract class Persona extends Usuario {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 11, message = "El dni debe tener 8 o 9 caracteres")
    private String dni;

    public void actualizarDatos(PersonaDTOActualizar personaDTO){
        if(personaDTO.getNuevoDni() != null){
            this.setDni(personaDTO.getNuevoDni());
        }
        if(personaDTO.getNuevoApellido() != null){
            this.setApellido(personaDTO.getNuevoApellido());
        }
        if(personaDTO.getNuevoNombre() != null){
            this.setNombre(personaDTO.getNuevoNombre());
        }
    }

    public Persona(PersonaDTOPassword dto, Rol rol) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.dni = dto.getDni();
        this.setEmail(dto.getEmail());
        this.setRol(rol);
        this.setPassword(dto.getPassword());
    }
    public Persona(PersonaDTO dto, Rol rol, String password) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.dni = dto.getDni();
        this.setEmail(dto.getEmail());
        this.setRol(rol);
        this.setPassword(password);
    }

    public Persona(){
        super();
    }

    public void borrarDni() {
        this.setDni("*" + this.getDni() + "*");
    }

}
