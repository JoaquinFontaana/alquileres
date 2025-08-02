package inge2.com.alquileres.backend.model.enums;

public enum EstadoEmpleado {

    ACTIVO(true),
    INACTIVO(false);

    private final boolean activo;

    EstadoEmpleado(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }
}