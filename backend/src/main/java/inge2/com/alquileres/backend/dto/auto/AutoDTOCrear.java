package inge2.com.alquileres.backend.dto.auto;

import inge2.com.alquileres.backend.model.Auto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter @Setter
public class AutoDTOCrear extends AutoDTO{

    private MultipartFile imagen;

    public AutoDTOCrear(Auto auto, MultipartFile imagen) {
        super(auto);
        this.imagen = imagen;
    }
    public AutoDTOCrear(){
        super();
    }
}
