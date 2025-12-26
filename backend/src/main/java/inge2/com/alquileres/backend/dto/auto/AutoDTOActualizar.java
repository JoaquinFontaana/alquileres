package inge2.com.alquileres.backend.dto.auto;

import inge2.com.alquileres.backend.model.enums.CategoriaAuto;
import inge2.com.alquileres.backend.model.enums.EstadoAutoEnum;
import inge2.com.alquileres.backend.model.enums.TiposRembolso;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class AutoDTOActualizar {
    @Positive(message = "El precio por dia debe ser positivo")
    private Double precioPorDia;
    @Positive(message = "La capacidad debe ser positiva")
    private int capacidad;
    @Enumerated(EnumType.STRING)
    private CategoriaAuto categoria;
    @Enumerated(EnumType.STRING)
    private TiposRembolso rembolso;
    @NotBlank(message = "La sucursal es obligatoria")
    private String sucursal;
    private MultipartFile imagen;
}

