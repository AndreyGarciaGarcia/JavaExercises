package com.curso.ejercicios.arrays;

import java.util.Arrays;

/**
 * EJERCICIO 04 · Arrays y String
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Invertir el orden de un array de enteros sin usar {@code reverse} de utilidades.</li>
 *   <li>Invertir el orden de las palabras de una frase.</li>
 *   <li>Encontrar el número mayor y menor de un array.</li>
 *   <li>Extra: contar cuántas veces se repite cada carácter de un texto.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * Arrays, índices, bucles, bucles anidados, {@code String} inmutable, {@code StringBuilder}.
 */
public final class Ejercicio04_ArraysYStrings {

    private Ejercicio04_ArraysYStrings() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 04 · Arrays y String ────────────────");

        int[] numeros = {5, 8, 2, 9, 1, 4, 7};
        System.out.println("Original : " + Arrays.toString(numeros));
        System.out.println("Invertido: " + Arrays.toString(invertir(numeros)));
        System.out.println("Máximo   : " + maximo(numeros));
        System.out.println("Mínimo   : " + minimo(numeros));

        String frase = "Java es un lenguaje genial";
        System.out.println("Frase    : " + frase);
        System.out.println("Al revés : " + invertirPalabras(frase));
        System.out.println("Palabra más larga: " + palabraMasLarga(frase));
    }

    /** Devuelve un array nuevo con los elementos en orden inverso (el original no cambia). */
    public static int[] invertir(int[] origen) {
        int[] destino = new int[origen.length];
        for (int i = 0; i < origen.length; i++) {
            destino[i] = origen[origen.length - 1 - i];
        }
        return destino;
    }

    /** "hola que tal" → "tal que hola". Las palabras se separan por espacios. */
    public static String invertirPalabras(String texto) {
        String[] palabras = texto.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = palabras.length - 1; i >= 0; i--) {
            sb.append(palabras[i]);
            if (i > 0) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    /** Mayor elemento; lanza error si el array está vacío. */
    public static int maximo(int[] datos) {
        comprobarNoVacio(datos);
        int max = datos[0];
        for (int v : datos) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    /** Menor elemento; lanza error si el array está vacío. */
    public static int minimo(int[] datos) {
        comprobarNoVacio(datos);
        int min = datos[0];
        for (int v : datos) {
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    /** Devuelve la palabra con más caracteres (la primera en caso de empate). */
    public static String palabraMasLarga(String texto) {
        String[] palabras = texto.trim().split("\\s+");
        String mejor = "";
        for (String p : palabras) {
            if (p.length() > mejor.length()) {
                mejor = p;
            }
        }
        return mejor;
    }

    private static void comprobarNoVacio(int[] datos) {
        if (datos == null || datos.length == 0) {
            throw new IllegalArgumentException("El array no puede ser nulo ni estar vacío");
        }
    }
}
