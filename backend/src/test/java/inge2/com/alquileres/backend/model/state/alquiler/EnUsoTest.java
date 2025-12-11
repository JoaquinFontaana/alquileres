package inge2.com.alquileres.backend.model.state.alquiler;

import inge2.com.alquileres.backend.model.Auto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnUsoTest extends EstadoAlquilerBaseTest{
    private final EnUso enUso = new EnUso();
    @Test
    @Override
    void bajaAuto_test() {
        assertThrows(IllegalStateException.class,()->enUso.bajaAuto(alquilerMock,alquilerServiceMock));
    }
    @Test
    @Override
    void iniciar_test() {
        assertThrows(IllegalStateException.class,()-> enUso.iniciar(alquilerMock,alquilerServiceMock,autoServiceMock));
    }
    @Test
    @Override
    void cancelar_test() {
        assertThrows(IllegalStateException.class,()-> enUso.cancelar(alquilerMock,alquilerServiceMock));
    }
    @Test
    @Override
    void finalizar_test() {
        Auto autoMock = mock(Auto.class);
        when(alquilerMock.getAuto()).thenReturn(autoMock);
        enUso.finalizar(alquilerServiceMock,autoServiceMock,alquilerMock);

        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);
        verify(alquilerMock).cambiarEstado(captor.capture());
        assertInstanceOf(Finalizado.class,captor.getValue());
        verify(autoMock).finalizarAlquiler(autoServiceMock);
        verify(alquilerServiceMock).saveAlquiler(alquilerMock);
    }
    @Test
    @Override
    void finalizarVencido_test() {
        assertThrows(IllegalStateException.class,()-> enUso.finalizarVencido(alquilerMock,alquilerServiceMock));
    }
    @Test
    @Override
    void finalizarConMantenimiento_test() {
        Auto autoMock = mock(Auto.class);
        when(alquilerMock.getAuto()).thenReturn(autoMock);
        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);
        int multa = 500;
        enUso.finalizarConMantenimiento(alquilerMock,alquilerServiceMock,autoServiceMock,multa);
        verify(alquilerMock).setClienteMulta(multa);
        verify(alquilerMock).cambiarEstado(captor.capture());
        assertInstanceOf(Finalizado.class,captor.getValue());
        verify(autoMock).iniciarMantenimiento(autoServiceMock,alquilerServiceMock);
    }
    @Test
    @Override
    void procesarPago_test() {
        assertThrows(IllegalStateException.class,()-> enUso.procesarPago(alquilerMock,alquilerServiceMock));
    }
}
