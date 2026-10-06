package com.curso.util;

import java.util.Scanner;

/**
 * Ayudante de lectura por teclado (envuelve {@link Scanner}).
 *
 * <p>Uso: siempre se lee línea completa con {@code nextLine()}, lo que evita el
 * clásico bug de mezclar {@code nextInt()} con {@code nextLine()}.</p>
 *
 * <pre>
 *     String nombre = Teclado.texto("¿Cómo te llamas? ");
 *     int edad       = Teclado.entero("¿Cuántos años tienes? ");
 * </pre>
 */
public final class Teclado {

    private static final Scanner ENTRADA = new Scanner(System.in);

    /** Constructor privado: es una clase de utilidad, no se instancia. */
    private Teclado() {
    }

    /** Lee una línea de texto. No devuelve nunca {@code null} (vacío si no escribe nada). */
    public static String texto(String mensaje) {
        System.out.print(mensaje);
        String linea = ENTRADA.hasNextLine() ? ENTRADA.nextLine() : "";
        return linea.trim();
    }

    /** Lee un entero repitiendo la petición mientras el valor no sea válido. */
    public static int entero(String mensaje) {
        while (true) {
            String entrada = texto(mensaje);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("  ✗ '" + entrada + "' no es un número entero. Inténtalo otra vez.");
            }
        }
    }

    /** Lee un entero comprendido entre {@code min} y {@code max} (inclusive). */
    public static int enteroRango(String mensaje, int min, int max) {
        while (true) {
            int valor = entero(mensaje);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println("  ✗ El valor debe estar entre " + min + " y " + max + ".");
        }
    }

    /** Lee un número decimal (coma o punto según la locale del sistema). */
    public static double decimal(String mensaje) {
        while (true) {
            String entrada = texto(mensaje);
            try {
                return Double.parseDouble(entrada.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("  ✗ '" + entrada + "' no es un número válido. Inténtalo otra vez.");
            }
        }
    }

    /** Pregunta sí/no. Repite hasta obtener una respuesta clara. */
    public static boolean siNo(String mensaje) {
        while (true) {
            String entrada = texto(mensaje + " (s/n): ").toLowerCase();
            if (entrada.equals("s") || entrada.equals("si") || entrada.equals("sí") || entrada.equals("y")) {
                return true;
            }
            if (entrada.equals("n") || entrada.equals("no")) {
                return false;
            }
            System.out.println("  ✗ Responde 's' o 'n'.");
        }
    }

    /** Salta una línea en blanco (separación visual entre secciones). */
    public static void pausa() {
        texto("\nPulsa [Enter] para continuar...");
    }

    /** Cierra el scanner. Útil al terminar el programa. */
    public static void cerrar() {
        ENTRADA.close();
    }
}
