package inge2.com.alquileres.backend.model.state.alquiler;

import inge2.com.alquileres.backend.model.Alquiler;
import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;
import org.mockito.Mock;

public abstract class EstadoAlquilerBaseTest {
    @Mock
    protected Alquiler alquilerMock;
    @Mock
    protected AlquilerService alquilerServiceMock;
    @Mock
    protected AutoService autoServiceMock;
    @Mock
    protected Auto autoMock;
    abstract void finalizar_test();

    abstract void finalizarConMantenimiento_test();

    abstract void finalizarVencido_test();

    abstract void cancelar_test();

    abstract void iniciar_test();

    abstract void bajaAuto_test();

    abstract void procesarPago_test();
}
