package com.curso.ejercicios.poo;

import java.util.Objects;

/**
 * Modelo de una cuenta bancaria sencilla.
 *
 * <p>Demuestra <strong>encapsulamiento</strong> (atributos {@code private} + métodos públicos),
 * <strong>constructor</strong>, <strong>validación</strong> y la redefinición de
 * {@code toString()}, {@code equals()} y {@code hashCode()}.</p>
 */
public class CuentaBancaria {

    private final String iban;      // inmutable: solo se asigna en el constructor
    private final String titular;
    private double saldo;

    public CuentaBancaria(String iban, String titular, double saldoInicial) {
        if (iban == null || iban.isBlank()) {
            throw new IllegalArgumentException("El IBAN es obligatorio");
        }
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("El titular es obligatorio");
        }
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.iban = iban;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public CuentaBancaria(String iban, String titular) {
        this(iban, titular, 0.0);   // delega en el constructor principal
    }

    // ---------------- Operaciones ----------------

    /** Ingresa dinero. Devuelve el nuevo saldo. */
    public double ingresar(double cantidad) {
        validarCantidad(cantidad);
        saldo += cantidad;
        return saldo;
    }

    /** Retira dinero. Lanza {@link SaldoInsuficienteException} si no hay fondos. */
    public double retirar(double cantidad) throws SaldoInsuficienteException {
        validarCantidad(cantidad);
        if (cantidad > saldo) {
            throw new SaldoInsuficienteException(cantidad - saldo);
        }
        saldo -= cantidad;
        return saldo;
    }

    private void validarCantidad(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
    }

    // ---------------- Accesores ----------------

    public String getIban() {
        return iban;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean tieneFondos() {
        return saldo > 0;
    }

    // ---------------- Object ----------------

    @Override
    public String toString() {
        return "CuentaBancaria{iban='" + iban + "', titular='" + titular + "', saldo=" + saldo + "}";
    }

    /** Dos cuentas son iguales si comparten IBAN. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentaBancaria otra)) {
            return false;
        }
        return iban.equals(otra.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }
}
