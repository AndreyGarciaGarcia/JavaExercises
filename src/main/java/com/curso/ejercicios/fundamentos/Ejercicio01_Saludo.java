package com.curso.ejercicios.fundamentos;

import com.curso.util.Teclado;

/**
 * EJERCICIO 01 · Fundamentos — Saludo personalizado
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Pide al usuario su nombre por teclado.</li>
 *   <li>Muestra: "Hola, &lt;nombre&gt;! Bienvenido a Java."</li>
 *   <li>Si el nombre está vacío, muestra un aviso y vuelve a pedirlo.</li>
 *   <li>Extra: imprime cuántos caracteres tiene el nombre.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * Variables, {@code String}, concatenación, {@code System.out}, métodos estáticos.
 */
public final class Ejercicio01_Saludo {

    private Ejercicio01_Saludo() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 01 · Saludo ─────────────────────────");

        String nombre = pedirNombre();
        System.out.println(saludar(nombre));
        System.out.println("Tu nombre tiene " + nombre.length() + " caracteres.");
        System.out.println("En mayúsculas: " + nombre.toUpperCase());
    }

    /** Pide el nombre hasta que el usuario escribe algo. */
    private static String pedirNombre() {
        while (true) {
            String nombre = Teclado.texto("¿Cómo te llamas? ");
            if (!nombre.isEmpty()) {
                return nombre;
            }
            System.out.println("  ✗ El nombre no puede estar vacío.");
        }
    }

    /** Devuelve el mensaje de saludo. Es {@code public} para poder testearla. */
    public static String saludar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
        }
        return "Hola, " + nombre.trim() + "! Bienvenido a Java.";
    }
}
