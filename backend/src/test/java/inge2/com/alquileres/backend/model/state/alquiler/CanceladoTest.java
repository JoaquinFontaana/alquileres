package inge2.com.alquileres.backend.model.state.alquiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class CanceladoTest extends EstadoAlquilerBaseTest{
    private final Cancelado cancelado = new Cancelado();

    @Test
    @Override
    void bajaAuto_test() {
        // En estado cancelado, bajaAuto no hace nada
        cancelado.bajaAuto(alquilerMock, alquilerServiceMock);
        verifyNoInteractions(alquilerMock, alquilerServiceMock);
    }

    @Test
    @Override
    void iniciar_test() {
        assertThrows(IllegalStateException.class,
            () -> cancelado.iniciar(alquilerMock, alquilerServiceMock, autoServiceMock));
    }

    @Test
    @Override
    void cancelar_test() {
        assertThrows(IllegalStateException.class,
            () -> cancelado.cancelar(alquilerMock, alquilerServiceMock));
    }

    @Test
    @Override
    void finalizar_test() {
        assertThrows(IllegalStateException.class,
            () -> cancelado.finalizar(alquilerServiceMock, autoServiceMock, alquilerMock));
    }

    @Test
    @Override
    void finalizarVencido_test() {
        assertThrows(IllegalStateException.class,
            () -> cancelado.finalizarVencido(alquilerMock, alquilerServiceMock));
    }

    @Test
    @Override
    void finalizarConMantenimiento_test() {
        assertThrows(IllegalStateException.class,
            () -> cancelado.finalizarConMantenimiento(alquilerMock, alquilerServiceMock, autoServiceMock, 500));
    }

    @Test
    @Override
    void procesarPago_test() {
        assertThrows(IllegalStateException.class,
            () -> cancelado.procesarPago(alquilerMock, alquilerServiceMock));
    }
}

