package inge2.com.alquileres.backend.model.state.alquiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class FinalizadoTest extends EstadoAlquilerBaseTest{
    private final Finalizado finalizado = new Finalizado();

    @Test
    @Override
    void bajaAuto_test() {
        // En estado finalizado, bajaAuto no hace nada
        finalizado.bajaAuto(alquilerMock, alquilerServiceMock);
        verifyNoInteractions(alquilerMock, alquilerServiceMock);
    }

    @Test
    @Override
    void iniciar_test() {
        assertThrows(IllegalStateException.class,
            () -> finalizado.iniciar(alquilerMock, alquilerServiceMock, autoServiceMock));
    }

    @Test
    @Override
    void cancelar_test() {
        assertThrows(IllegalStateException.class,
            () -> finalizado.cancelar(alquilerMock, alquilerServiceMock));
    }

    @Test
    @Override
    void finalizar_test() {
        assertThrows(IllegalStateException.class,
            () -> finalizado.finalizar(alquilerServiceMock, autoServiceMock, alquilerMock));
    }

    @Test
    @Override
    void finalizarVencido_test() {
        assertThrows(IllegalStateException.class,
            () -> finalizado.finalizarVencido(alquilerMock, alquilerServiceMock));
    }

    @Test
    @Override
    void finalizarConMantenimiento_test() {
        assertThrows(IllegalStateException.class,
            () -> finalizado.finalizarConMantenimiento(alquilerMock, alquilerServiceMock, autoServiceMock, 500));
    }

    @Test
    @Override
    void procesarPago_test() {
        assertThrows(IllegalStateException.class,
            () -> finalizado.procesarPago(alquilerMock, alquilerServiceMock));
    }
}

