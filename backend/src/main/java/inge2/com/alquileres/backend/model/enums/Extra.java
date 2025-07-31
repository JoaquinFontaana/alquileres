package inge2.com.alquileres.backend.model.enums;

public enum Extra {
    SEGURO(3500),
    CADENA_NIEVE(2500),
    SILLA_BEBE(1500),
    COMBUSTIBLE_COMPLETO(5000);


    private final double precio;

    Extra(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}