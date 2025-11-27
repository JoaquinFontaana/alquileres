package inge2.com.alquileres.backend.model.state.auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class EstadoAutoBaseTest {
    @Mock
    protected Auto autoMock;
    @Mock
    protected AutoService autoServiceMock;
    @Mock
    protected AlquilerService alquilerServiceMock;

    abstract void iniciarAlquiler_Test();

    abstract void finalizarAlquiler_Test();

    abstract void darDeBaja_Test();

    abstract void iniciarMantenimiento_Test();

    abstract void finalizarMantenimiento_Test();
}
