package com.curso.ejercicios.excepciones;

import com.curso.util.Teclado;

/**
 * EJERCICIO 08 · Manejo de excepciones
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Pide una edad y válida que esté entre 0 y 120: si no, muestra un mensaje claro.</li>
 *   <li>Usa una excepción propia ({@link EdadInvalidaException}) en la lógica de negocio.</li>
 *   <li>Prueba el caso de una división por cero controlada con {@code try/catch}.</li>
 *   <li>Extra: lee un número con {@code Integer.parseInt} y captura el error de formato.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code try/catch/finally}, excepciones <em>checked</em> vs <em>unchecked</em>,
 * excepciones personalizadas, {@code Optional}.
 */
public final class Ejercicio08_Excepciones {

    /** Excepción propia (checked) para edades fuera de rango. */
    public static class EdadInvalidaException extends Exception {
        private static final long serialVersionUID = 1L;

        public EdadInvalidaException(int edad) {
            super("Edad no válida: " + edad + " (debe estar entre 0 y 120)");
        }
    }

    private Ejercicio08_Excepciones() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 08 · Excepciones ────────────────────");

        // 1. Excepción propia
        int edad = Teclado.entero("Introduce tu edad: ");
        try {
            validarEdad(edad);
            System.out.println("  ✓ Edad válida: " + edad);
        } catch (EdadInvalidaException e) {
            System.out.println("  ✗ " + e.getMessage());
        }

        // 2. División controlada
        int a = Teclado.entero("Dividendo: ");
        int b = Teclado.entero("Divisor: ");
        try {
            System.out.println("  Resultado: " + dividir(a, b));
        } catch (ArithmeticException e) {
            System.out.println("  ✗ No se puede dividir entre cero");
        } finally {
            System.out.println("  (finally: se ejecuta siempre)");
        }

        // 3. Error de formato al convertir texto a número
        String entrada = Teclado.texto("Escribe un número entero: ");
        try {
            int numero = Integer.parseInt(entrada.trim());
            System.out.println("  ✓ Número correcto: " + numero);
        } catch (NumberFormatException e) {
            System.out.println("  ✗ '" + entrada + "' no es un número entero");
        }

        // 4. Optional: forma segura de devolver "o nada"
        Integer resultado = buscarEdad(edad).orElse(null);
        System.out.println("  Optional → " + (resultado == null ? "sin valor" : resultado + " años"));
    }

    /** Lanza la excepción propia si la edad está fuera de rango. */
    public static void validarEdad(int edad) throws EdadInvalidaException {
        if (edad < 0 || edad > 120) {
            throw new EdadInvalidaException(edad);
        }
    }

    /** División entera con control explícito de la división por cero. */
    public static int dividir(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("división por cero");
        }
        return a / b;
    }

    /** Devuelve la edad si es válida; {@code Optional.empty()} si no lo es. */
    public static java.util.Optional<Integer> buscarEdad(int edad) {
        try {
            validarEdad(edad);
            return java.util.Optional.of(edad);
        } catch (EdadInvalidaException e) {
            return java.util.Optional.empty();
        }
    }
}
