package inge2.com.alquileres.backend.model.state.alquiler;

import inge2.com.alquileres.backend.service.AutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetiroPendienteTest extends EstadoAlquilerBaseTest{
    private final RetiroPendiente retiroPendienteState = new RetiroPendiente();

    @Test
    @Override
    void cancelar_test() {
        double montoRembolso = 10.0;
        when(alquilerMock.calcularRembolso()).thenReturn(montoRembolso);

        retiroPendienteState.cancelar(alquilerMock,alquilerServiceMock);
        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);
        verify(alquilerMock).cambiarEstado(captor.capture());

        assertInstanceOf(Cancelado.class,captor.getValue());

        verify(alquilerServiceMock).rembolsarAlquiler(alquilerMock,montoRembolso);
        verify(alquilerServiceMock).saveAlquiler(alquilerMock);
    }
    @Test
    @Override
    void bajaAuto_test() {
        double montoTotal = 10.0;
        when(alquilerMock.calcularTotal()).thenReturn(montoTotal);

        retiroPendienteState.bajaAuto(alquilerMock,alquilerServiceMock);
        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);
        verify(alquilerMock).cambiarEstado(captor.capture());

        assertInstanceOf(Cancelado.class,captor.getValue());

        verify(alquilerServiceMock).rembolsarAlquiler(alquilerMock,montoTotal);
        verify(alquilerServiceMock).saveAlquiler(alquilerMock);
        verify(alquilerServiceMock).sendEmailBajaAuto(eq(alquilerMock),any());
    }
    @Test
    @Override
    void finalizar_test() {
        assertThrows(IllegalStateException.class,() -> retiroPendienteState.finalizar(alquilerServiceMock,autoServiceMock,alquilerMock));
    }

    @Test
    void iniciar_noDisponible_test() {
        when(alquilerMock.retiroDisponible()).thenReturn(false);
        assertThrows(IllegalStateException.class,() -> retiroPendienteState.iniciar(alquilerMock,alquilerServiceMock,autoServiceMock));
    }
    @Test
    @Override
    void iniciar_test() {
        when(alquilerMock.getAuto()).thenReturn(autoMock);
        when(alquilerMock.retiroDisponible()).thenReturn(true);

        retiroPendienteState.iniciar(alquilerMock,alquilerServiceMock,autoServiceMock);
        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);

        verify(alquilerMock).cambiarEstado(captor.capture());
        assertInstanceOf(EnUso.class,captor.getValue());
        verify(autoMock).iniciarAlquiler(any(AutoService.class));
        verify(alquilerServiceMock).saveAlquiler(alquilerMock);
    }
    @Test
    @Override
    void finalizarConMantenimiento_test() {
        assertThrows(IllegalStateException.class,() -> retiroPendienteState.finalizarConMantenimiento(alquilerMock,alquilerServiceMock,autoServiceMock,20));
    }
    @Test
    @Override
    void finalizarVencido_test() {
        retiroPendienteState.finalizarVencido(alquilerMock,alquilerServiceMock);
        ArgumentCaptor<EstadoAlquiler> captor = ArgumentCaptor.forClass(EstadoAlquiler.class);
        verify(alquilerMock).cambiarEstado(captor.capture());
        assertInstanceOf(Finalizado.class,captor.getValue());
        verify(alquilerServiceMock).saveAlquiler(any());
    }
    @Test
    @Override
    void procesarPago_test() {
        assertThrows(IllegalStateException.class,() -> retiroPendienteState.procesarPago(alquilerMock,alquilerServiceMock));
    }
}
