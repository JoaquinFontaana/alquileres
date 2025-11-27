package inge2.com.alquileres.backend.model.state.auto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DisponibleTest extends EstadoAutoBaseTest{
    protected final Disponible disponibleState = new Disponible();

    @Test
    @Override
    @DisplayName("Baja: Debe ser exitosa y cambiar el estado del auto")
    protected void darDeBaja_Test(){
        when(autoMock.getReservas()).thenReturn(new ArrayList<>());
        disponibleState.darDeBaja(autoMock,alquilerServiceMock,autoServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);

        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(DeBaja.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @Override
    @DisplayName("IniciarAlquiler: Debe ser exitoso, sin excepciones")
    protected void iniciarAlquiler_Test(){
        disponibleState.iniciarAlquiler(autoMock,autoServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Alquilado.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @Override
    @DisplayName("IniciarMantenimiento: Debe ser exitoso, sin excepciones")
    protected void iniciarMantenimiento_Test(){
        when(autoMock.getReservas()).thenReturn(new ArrayList<>());
        disponibleState.iniciarMantenimiento(autoMock,autoServiceMock,alquilerServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Mantenimiento.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @Override
    @DisplayName("FinalizarAlquiler: Debe fallar con una excepcion")
    protected void finalizarAlquiler_Test(){
        assertThrows(IllegalStateException.class,() -> disponibleState.finalizarAlquiler(autoMock,autoServiceMock));
    }
    @Test
    @Override
    @DisplayName("FinalizarMantenimiento: Debe fallar con una excepcion")
    protected void finalizarMantenimiento_Test(){
        assertThrows(IllegalStateException.class,() -> disponibleState.finalizarMantenimiento(autoMock,autoServiceMock));
    }
}
