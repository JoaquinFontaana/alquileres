package inge2.com.alquileres.backend.dto.auto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AutoDTOCrear extends AutoDTO{
    @NotNull(message = "La imagen es obligatoria")
    private MultipartFile imagen;

}
