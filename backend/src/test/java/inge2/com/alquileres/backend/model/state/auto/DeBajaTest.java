package inge2.com.alquileres.backend.model.state.auto;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class DeBajaTest extends EstadoAutoBaseTest{
    private final EstadoAuto deBajaState = new DeBaja();
    @Test
    @Override
    void darDeBaja_Test() {
        assertThrows(IllegalStateException.class,() -> this.deBajaState.darDeBaja(autoMock,alquilerServiceMock,autoServiceMock));
    }
    @Test
    @Override
    void finalizarAlquiler_Test() {
        assertThrows(IllegalStateException.class,() -> this.deBajaState.finalizarAlquiler(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void finalizarMantenimiento_Test() {
        assertThrows(IllegalStateException.class,() -> this.deBajaState.finalizarMantenimiento(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void iniciarAlquiler_Test() {
        assertThrows(IllegalStateException.class,() -> this.deBajaState.iniciarAlquiler(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void iniciarMantenimiento_Test() {
        deBajaState.iniciarMantenimiento(autoMock,autoServiceMock,alquilerServiceMock);
        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);

        verify(autoMock).cambiarEstado(captor.capture());
        assertInstanceOf(Mantenimiento.class,captor.getValue());
    }
}
