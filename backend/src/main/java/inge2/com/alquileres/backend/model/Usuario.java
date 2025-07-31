package inge2.com.alquileres.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    @Email(message = "Ingresa un email valido")
    @Column(unique = true, nullable = false)
    private String mail;
    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Usuario(){

    }

    public Usuario(String password, String mail, Rol rol) {
        this.password = password;
        this.mail = mail;
        this.rol = rol;
    }

    public void modificarPassword(String password){
        this.password = password;
    }

    public boolean isAdmin(){
        return this.getRol().getNombre().equals("ADMIN");
    }

    public void borrarMail() {
        this.setMail("*" + this.getMail() + "*");
    }
}
