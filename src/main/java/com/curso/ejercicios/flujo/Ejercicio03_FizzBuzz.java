package com.curso.ejercicios.flujo;

/**
 * EJERCICIO 03 · Control de flujo — FizzBuzz
 *
 * <h2>Enunciado</h2>
 * <p>Para los números del 1 al 100 imprime:</p>
 * <ul>
 *   <li>"Fizz" si es múltiplo de 3</li>
 *   <li>"Buzz" si es múltiplo de 5</li>
 *   <li>"FizzBuzz" si es múltiplo de ambos (3 y 5)</li>
 *   <li>En caso contrario, el propio número</li>
 * </ul>
 * <p>Extra: pide el límite por teclado y repite el juego.</p>
 *
 * <h2>Conceptos</h2>
 * {@code if / else if}, operador módulo {@code %}, bucle {@code for}, {@code switch} moderno.
 */
public final class Ejercicio03_FizzBuzz {

    private Ejercicio03_FizzBuzz() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 03 · FizzBuzz ───────────────────────");
        int limite = 100;

        for (int n = 1; n <= limite; n++) {
            System.out.printf("%-10s", resolver(n));
            if (n % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * Devuelve la representación de un número según las reglas FizzBuzz.
     *
     * @param n número a evaluar
     * @return "FizzBuzz", "Fizz", "Buzz" o el número convertido a texto
     */
    public static String resolver(int n) {
        boolean por3 = n % 3 == 0;
        boolean por5 = n % 5 == 0;

        if (por3 && por5) {
            return "FizzBuzz";
        }
        if (por3) {
            return "Fizz";
        }
        if (por5) {
            return "Buzz";
        }
        return String.valueOf(n);
    }

    /** Versión alternativa usando switch (Java 21+) sobre el patrón de múltiplos. */
    public static String resolverConSwitch(int n) {
        return switch (n % 15) {
            case 0 -> "FizzBuzz";
            case 3, 6, 9, 12 -> "Fizz";
            case 5, 10 -> "Buzz";
            default -> String.valueOf(n);
        };
    }
}
