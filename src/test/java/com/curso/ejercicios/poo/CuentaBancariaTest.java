package com.curso.ejercicios.poo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 05 · CuentaBancaria")
class CuentaBancariaTest {

    private CuentaBancaria cuenta;

    @BeforeEach
    void preparar() {
        cuenta = new CuentaBancaria("ES9121000418450200051332", "Ana Pérez", 1000);
    }

    @Test
    @DisplayName("El constructor fija saldo y titular")
    void constructorCorrecto() {
        assertEquals(1000, cuenta.getSaldo());
        assertEquals("Ana Pérez", cuenta.getTitular());
        assertTrue(cuenta.tieneFondos());
    }

    @Test
    @DisplayName("Ingresar aumenta el saldo")
    void ingresarSumaSaldo() {
        cuenta.ingresar(250.5);
        assertEquals(1250.5, cuenta.getSaldo());
    }

    @Test
    @DisplayName("Retirar descuenta del saldo")
    void retirarRestaSaldo() throws Exception {
        cuenta.retirar(400);
        assertEquals(600, cuenta.getSaldo());
    }

    @Test
    @DisplayName("Retirar más de lo que hay lanza SaldoInsuficienteException")
    void retirarSinFondosLanzaExcepcion() {
        SaldoInsuficienteException e =
                assertThrows(SaldoInsuficienteException.class, () -> cuenta.retirar(5000));
        assertEquals(4000, e.getFaltante());
    }

    @Test
    @DisplayName("Cantidades negativas o cero no están permitidas")
    void cantidadesInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> cuenta.ingresar(-10));
        assertThrows(IllegalArgumentException.class, () -> cuenta.ingresar(0));
    }

    @Test
    @DisplayName("Dos cuentas con el mismo IBAN son iguales")
    void equalsPorIban() {
        CuentaBancaria otra = new CuentaBancaria(cuenta.getIban(), "Distinta", 999);
        assertEquals(cuenta, otra);
        assertEquals(cuenta.hashCode(), otra.hashCode());
        assertFalse(cuenta.equals(new CuentaBancaria("ES9999", "Otra", 1)));
    }

    @Test
    @DisplayName("El constructor rechaza datos vacíos")
    void constructorValido() {
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria("", "Ana", 10));
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria("ES1", "  ", 10));
        assertThrows(IllegalArgumentException.class, () -> new CuentaBancaria("ES1", "Ana", -5));
    }
}
