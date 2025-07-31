package inge2.com.alquileres.backend.model.valueObject;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Embeddable @Getter @Setter
public class RangoFecha {
    @FutureOrPresent(message = "La fecha debe ser en el presente o futuro")
    private LocalDate fechaDesde;
    @FutureOrPresent(message = "La fecha debe ser en el presente o futuro")
    private LocalDate fechaHasta;

    public RangoFecha(){

    }
    public RangoFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    /**
     * Devuelve true si este rango y el rango dado NO coinciden ni un solo día.
     * Es decir, están completamente separados:
     *   - este.fechaHasta < rango.fechaDesde  (termina antes de que empiece el otro)
     *   OR
     *   - rango.fechaHasta < this.fechaDesde  (el otro termina antes de que empiece éste)
     * Igualdades en los límites también cuentan como solapamiento,
     * por lo que usamos isBefore/isAfter estrictos.
     */
    public boolean sinSolapamiento(RangoFecha rango) {
        return this.fechaHasta.plusDays(1).isBefore(rango.getFechaDesde())
                || rango.getFechaHasta().isBefore(this.fechaDesde);
    }

    public boolean isTodayOrBefore(){
        return !this.fechaDesde.isAfter(LocalDate.now());
    }

    public int cantidadDeDias(){
        return Period.between(fechaDesde,fechaHasta).getDays();
    }
}
