package com.curso.ejercicios.colecciones;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * EJERCICIO 06 · Colecciones — Conteo de palabras
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Dada una frase, cuenta cuántas veces aparece cada palabra.</li>
 *   <li>Muestra las palabras ordenadas por frecuencia (de más a menos).</li>
 *   <li>Extra: elimina las palabras de menos de 3 letras ("stop words").</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code List}, {@code Map}, {@code getOrDefault}, {@code merge}, iteración con {@code entrySet()}.
 */
public final class Ejercicio06_ConteoPalabras {

    private Ejercicio06_ConteoPalabras() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 06 · Colecciones: conteo de palabras ─");

        String frase = "Java es fácil y Java es potente y Java permite aprender rápido";

        Map<String, Integer> conteo = contarPalabras(frase);
        System.out.println("Frase: " + frase);
        System.out.println("Palabras distintas: " + conteo.size());
        System.out.println("Conteo (orden de aparición):");
        conteo.forEach((palabra, veces) -> System.out.println("   " + palabra + " → " + veces));

        System.out.println("Top 3 por frecuencia:");
        porFrecuencia(conteo).stream()
                .limit(3)
                .forEach(e -> System.out.println("   " + e.getKey() + " (" + e.getValue() + ")"));

        Map<String, Integer> filtrado = contarPalabrasConLongitudMinima(frase, 4);
        System.out.println("Sin palabras cortas (<4 letras): " + filtrado);
    }

    /**
     * Cuenta las apariciones de cada palabra (sin distinguir mayúsculas).
     *
     * @param texto frase de entrada
     * @return mapa palabra → número de apariciones, en orden de primera aparición
     */
    public static Map<String, Integer> contarPalabras(String texto) {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        if (texto == null || texto.isBlank()) {
            return conteo;
        }
        for (String palabra : texto.toLowerCase().split("\\W+")) {
            if (!palabra.isBlank()) {
                conteo.merge(palabra, 1, Integer::sum);   // atómico y limpio
            }
        }
        return conteo;
    }

    /** Como {@link #contarPalabras(String)} pero descartando palabras cortas. */
    public static Map<String, Integer> contarPalabrasConLongitudMinima(String texto, int minima) {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        contarPalabras(texto).forEach((palabra, veces) -> {
            if (palabra.length() >= minima) {
                resultado.put(palabra, veces);
            }
        });
        return resultado;
    }

    /** Devuelve las entradas ordenadas de mayor a menor frecuencia. */
    public static List<Map.Entry<String, Integer>> porFrecuencia(Map<String, Integer> conteo) {
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(conteo.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));   // descendente
        return lista;
    }
}
