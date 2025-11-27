package inge2.com.alquileres.backend.model.state.auto;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlquiladoTest extends EstadoAutoBaseTest{
    private final EstadoAuto alquiladoState = new Alquilado();

    @Test
    @Override
    void darDeBaja_Test() {
        assertThrows(IllegalStateException.class,() -> this.alquiladoState.darDeBaja(autoMock,alquilerServiceMock,autoServiceMock));
    }
    @Test
    @Override
    void finalizarAlquiler_Test() {
        alquiladoState.finalizarAlquiler(autoMock,autoServiceMock);
        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);

        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Disponible.class,captor.getValue());

        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @Override
    void finalizarMantenimiento_Test() {
        assertThrows(IllegalStateException.class,() -> this.alquiladoState.finalizarMantenimiento(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void iniciarAlquiler_Test() {
        assertThrows(IllegalStateException.class,() -> this.alquiladoState.finalizarMantenimiento(autoMock,autoServiceMock));
    }
    @Test
    @Override
    void iniciarMantenimiento_Test() {
        when(autoMock.getReservas()).thenReturn(new ArrayList<>());

        alquiladoState.iniciarMantenimiento(autoMock,autoServiceMock,alquilerServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Mantenimiento.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
}
