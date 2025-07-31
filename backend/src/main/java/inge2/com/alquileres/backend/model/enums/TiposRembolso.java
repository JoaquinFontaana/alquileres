package inge2.com.alquileres.backend.model.enums;

public enum TiposRembolso {
    SIN_REMBOLSO(0.0),
    REMBOLSO_PARCIAL(0.25),
    REMBOLSO_MEDIO(0.50),
    REMBOLSO_TOTAL(1.0);

    private final double porcentaje;

    TiposRembolso(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double calcularRembolso(double montoTotal) {
        return montoTotal * porcentaje;
    }
}
