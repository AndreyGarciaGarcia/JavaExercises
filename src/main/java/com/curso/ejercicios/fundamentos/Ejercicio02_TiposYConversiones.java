package com.curso.ejercicios.fundamentos;

import com.curso.util.Teclado;

/**
 * EJERCICIO 02 · Fundamentos — Tipos de datos y conversiones
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Pide la temperatura en grados Celsius y muéstrala en Fahrenheit.</li>
 *   <li>Muestra el valor de cada tipo primitivo por defecto.</li>
 *   <li>Comprueba la truncación al hacer casting: {@code (int) 3.99 == 3}.</li>
 *   <li>Extra: calcula la edad en días de una persona.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * Primitivos, literales, casting implícito/explicito, operadores aritméticos.
 */
public final class Ejercicio02_TiposYConversiones {

    private Ejercicio02_TiposYConversiones() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 02 · Tipos y conversiones ───────────");

        // 1. Valores por defecto de los 8 primitivos
        byte b = 0;
        short s = 0;
        int i = 0;
        long l = 0L;
        float f = 0.0f;
        double d = 0.0;
        char c = '\u0000';
        boolean bool = false;
        System.out.println("Defaults → byte:" + b + " short:" + s + " int:" + i + " long:" + l
                + " float:" + f + " double:" + d + " char:" + (int) c + " boolean:" + bool);

        // 2. Conversión de temperaturas
        double celsius = Teclado.decimal("Introduce grados Celsius: ");
        System.out.println(celsius + " °C = " + String.format("%.2f", celsiusAFahrenheit(celsius)) + " °F");

        // 3. Truncación del casting
        double pi = 3.99;
        System.out.println("Casting (int) 3.99 → " + (int) pi + "   (trunca, NO redondea)");

        // 4. Overflow
        int max = Integer.MAX_VALUE;
        System.out.println("Integer.MAX_VALUE + 1 = " + (max + 1) + "  ← desbordamiento");

        // 5. Resumen de rangos
        System.out.println("Rango int: " + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE);
        System.out.println("Rango long: " + Long.MIN_VALUE + " .. " + Long.MAX_VALUE);
    }

    /** Celsius → Fahrenheit: F = C × 9/5 + 32 */
    public static double celsiusAFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }

    /** Fahrenheit → Celsius: C = (F − 32) × 5/9 */
    public static double fahrenheitACelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    /** Conversión de años a días (usa long para no desbordar). */
    public static long aniosADias(int anios) {
        return (long) anios * 365;
    }
}
