package com.curso.ejercicios.enums;

/**
 * Enum con estado: cada constante lleva su propio comportamiento.
 *
 * <p>Los enums son <strong>clases finales</strong> cuyas instancias son las
 * constantes declaradas (singletons), por eso se usan sin {@code new}.</p>
 */
public enum DiaSemana {

    LUNES("Lunes", true),
    MARTES("Martes", true),
    MIERCOLES("Miércoles", true),
    JUEVES("Jueves", true),
    VIERNES("Viernes", true),
    SABADO("Sábado", false),
    DOMINGO("Domingo", false);

    private final String nombreLargo;
    private final boolean laborable;

    DiaSemana(String nombreLargo, boolean laborable) {
        this.nombreLargo = nombreLargo;
        this.laborable = laborable;
    }

    /** ¿Es día de trabajo (lunes a viernes)? */
    public boolean esLaborable() {
        return laborable;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    /** Siguiente día de la semana, con vuelta al lunes. */
    public DiaSemana siguiente() {
        DiaSemana[] dias = values();
        return dias[(ordinal() + 1) % dias.length];
    }

    /** ¿Está entre dos días (ambos incluidos), respetando el ciclo semanal? */
    public boolean estaEntre(DiaSemana desde, DiaSemana hasta) {
        int pos = ordinal();
        return desde.ordinal() <= pos && pos <= hasta.ordinal();
    }

    /** De un número ISO (1 = lunes … 7 = domingo); fuera de rango → error. */
    public static DiaSemana deNumero(int numero) {
        if (numero < 1 || numero > 7) {
            throw new IllegalArgumentException("El número de día debe estar entre 1 y 7: " + numero);
        }
        return values()[numero - 1];
    }

    /** Todas las que son laborables. */
    public static java.util.List<DiaSemana> laborables() {
        return java.util.stream.Stream.of(values())
                .filter(DiaSemana::esLaborable)
                .toList();
    }

    @Override
    public String toString() {
        return nombreLargo;
    }
}
