package inge2.com.alquileres.backend.dto.auto;

import inge2.com.alquileres.backend.model.Auto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoDTOListar extends AutoDTO{
    private String imgUrl;
    private long id;
    public AutoDTOListar(Auto auto) {
        super(auto);
        this.imgUrl = auto.getRutaImagen();
    }

    public AutoDTOListar() {
        super();
    }
}
