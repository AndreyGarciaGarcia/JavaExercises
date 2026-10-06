package com.curso.ejercicios.poo;

/**
 * Excepción <em>checked</em> personalizada: se lanza cuando no hay saldo suficiente.
 *
 * <p>Hereda de {@link Exception}, por lo que el compilador obliga a capturarla
 * o a declararla con {@code throws} en la firma del método.</p>
 */
public class SaldoInsuficienteException extends Exception {

    private static final long serialVersionUID = 1L;

    private final double faltante;

    public SaldoInsuficienteException(double faltante) {
        super("Saldo insuficiente: faltan " + faltante + " €");
        this.faltante = faltante;
    }

    /** Cantidad que falta para completar la operación. */
    public double getFaltante() {
        return faltante;
    }
}
