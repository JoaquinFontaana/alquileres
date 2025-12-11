package inge2.com.alquileres.backend.model.state.alquiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConfirmacionPendienteTest extends EstadoAlquilerBaseTest{
    private final ConfirmacionPendiente confirmacionPendiente = new ConfirmacionPendiente();
    @Test
    @Override
    void cancelar_test() {
        confirmacionPendiente.cancelar(alquilerMock,alquilerServiceMock);
        verify(alquilerServiceMock).eliminarAlquiler(alquilerMock);
    }

    @Test
    @Override
    void iniciar_test() {
        assertThrows(IllegalStateException.class,()-> confirmacionPendiente.iniciar(alquilerMock,alquilerServiceMock,autoServiceMock));
    }
    @Test
    @Override
    void bajaAuto_test() {
        confirmacionPendiente.bajaAuto(alquilerMock,alquilerServiceMock);
        verify(alquilerServiceMock).sendEmailBajaAuto(eq(alquilerMock),any());
        verify(alquilerServiceMock).eliminarAlquiler(alquilerMock);
    }
    @Test
    @Override
    void finalizar_test() {
        assertThrows(IllegalStateException.class,() -> confirmacionPendiente.finalizar(alquilerServiceMock,autoServiceMock,alquilerMock));
    }
    @Test
    @Override
    void finalizarVencido_test() {
        assertThrows(IllegalStateException.class,() -> confirmacionPendiente.finalizarVencido(alquilerMock,alquilerServiceMock));
    }
    @Test
    @Override
    void finalizarConMantenimiento_test() {
        assertThrows(IllegalStateException.class,()-> confirmacionPendiente.finalizarConMantenimiento(alquilerMock,alquilerServiceMock,autoServiceMock,10));
    }
    @Test
    @Override
    void procesarPago_test() {
        confirmacionPendiente.procesarPago(alquilerMock,alquilerServiceMock);
        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);
        verify(alquilerMock).cambiarEstado(captor.capture());
        assertInstanceOf(RetiroPendiente.class,captor.getValue());
        verify(alquilerServiceMock).saveAlquiler(alquilerMock);
    }
}
