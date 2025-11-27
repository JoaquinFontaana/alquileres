package inge2.com.alquileres.backend.model.state.auto;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class MantenimientoTest extends EstadoAutoBaseTest{
    private final EstadoAuto mantenimientoState = new Mantenimiento();
    @Override
    @Test
    void darDeBaja_Test() {
        mantenimientoState.darDeBaja(autoMock,alquilerServiceMock,autoServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(DeBaja.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @Override
    void finalizarAlquiler_Test() {
        assertThrows(IllegalStateException.class,() -> mantenimientoState.finalizarAlquiler(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void finalizarMantenimiento_Test() {
        mantenimientoState.finalizarMantenimiento(autoMock,autoServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Disponible.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @Override
    void iniciarAlquiler_Test() {
        assertThrows(IllegalStateException.class,() -> mantenimientoState.iniciarAlquiler(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void iniciarMantenimiento_Test() {
        assertThrows(IllegalStateException.class,() -> mantenimientoState.iniciarMantenimiento(autoMock,autoServiceMock,alquilerServiceMock));
    }
}
