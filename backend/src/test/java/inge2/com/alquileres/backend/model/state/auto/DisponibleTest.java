package inge2.com.alquileres.backend.model.state.auto;

import inge2.com.alquileres.backend.model.Auto;
import inge2.com.alquileres.backend.service.AlquilerService;
import inge2.com.alquileres.backend.service.AutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DisponibleTest {
    @Mock
    private Auto autoMock;
    @Mock
    private AutoService autoServiceMock;
    @Mock
    private AlquilerService alquilerServiceMock;
    private final Disponible disponibleState = new Disponible();

    @BeforeEach
    public void setUp(){

    }
    @Test
    @DisplayName("Baja: Debe ser exitosa y cambiar el estado del auto")
    public void darDeBaja(){
        when(autoMock.getReservas()).thenReturn(new ArrayList<>());
        disponibleState.darDeBaja(autoMock,alquilerServiceMock,autoServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);

        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(DeBaja.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @DisplayName("IniciarAlquiler: Debe ser exitoso, sin excepciones")
    public void iniciarAlquiler(){
        disponibleState.iniciarAlquiler(autoMock,autoServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Alquilado.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @DisplayName("IniciarMantenimiento: Debe ser exitoso, sin excepciones")
    public void iniciarMantenimiento(){
        when(autoMock.getReservas()).thenReturn(new ArrayList<>());
        disponibleState.iniciarMantenimiento(autoMock,autoServiceMock,alquilerServiceMock);

        ArgumentCaptor<EstadoAuto> captor = ArgumentCaptor.forClass(EstadoAuto.class);
        verify(autoMock).cambiarEstado(captor.capture());

        assertInstanceOf(Mantenimiento.class,captor.getValue());
        verify(autoServiceMock).saveAuto(autoMock);
    }
    @Test
    @DisplayName("FinalizarAlquiler: Debe fallar con una excepcion")
    public void finalizarAlquiler(){
        assertThrows(IllegalStateException.class,() -> disponibleState.finalizarAlquiler(autoMock,autoServiceMock));
    }
    @Test
    @DisplayName("FinalizarMantenimiento: Debe fallar con una excepcion")
    public void finalizarMantenimiento(){
        assertThrows(IllegalStateException.class,() -> disponibleState.finalizarMantenimiento(autoMock,autoServiceMock));
    }
}
