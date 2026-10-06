package com.curso.ejercicios.poo;

import com.curso.util.Teclado;

/**
 * EJERCICIO 05 · POO — CuentaBancaria
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Crea dos cuentas y muestra sus datos con {@code toString()}.</li>
 *   <li>Realiza ingresos y retiros leyendo las cantidades por teclado.</li>
 *   <li>Controla el error al intentar retirar más de lo que hay.</li>
 *   <li>Comprueba que dos cuentas con el mismo IBAN son {@code equals()}.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * Clases, constructores, encapsulamiento, excepciones, {@code equals}/{@code toString}.
 *
 * @see CuentaBancaria
 * @see SaldoInsuficienteException
 */
public final class Ejercicio05_CuentaBancaria {

    private Ejercicio05_CuentaBancaria() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 05 · POO: CuentaBancaria ────────────");

        CuentaBancaria cuenta = new CuentaBancaria("ES91 2100 0418 4502 0005 1332", "Ana Pérez", 1000);

        System.out.println("Cuenta creada → " + cuenta);

        boolean seguir = true;
        while (seguir) {
            System.out.println("\n  1 · Ingresar   2 · Retirar   3 · Ver saldo   0 · Volver");
            int op = Teclado.enteroRango("  Opción: ", 0, 3);
            try {
                switch (op) {
                    case 1 -> {
                        double importe = pedirImporte("  Importe a ingresar: ");
                        cuenta.ingresar(importe);
                        System.out.println("  ✓ Nuevo saldo: " + cuenta.getSaldo() + " €");
                    }
                    case 2 -> {
                        double importe = pedirImporte("  Importe a retirar: ");
                        cuenta.retirar(importe);
                        System.out.println("  ✓ Nuevo saldo: " + cuenta.getSaldo() + " €");
                    }
                    case 3 -> System.out.println("  Saldo actual: " + cuenta.getSaldo() + " €");
                    case 0 -> seguir = false;
                    default -> {
                    }
                }
            } catch (SaldoInsuficienteException e) {
                System.out.println("  ✗ " + e.getMessage() + " (faltan " + e.getFaltante() + " €)");
            } catch (IllegalArgumentException e) {
                System.out.println("  ✗ " + e.getMessage());
            }
        }

        // equals() por IBAN
        CuentaBancaria clon = new CuentaBancaria(cuenta.getIban(), "Otro Titular");
        System.out.println("\n¿Mismo IBAN? → " + cuenta.equals(clon));
    }

    private static double pedirImporte(String mensaje) {
        double importe = Teclado.decimal(mensaje);
        if (importe <= 0) {
            throw new IllegalArgumentException("El importe debe ser positivo");
        }
        return importe;
    }
}
