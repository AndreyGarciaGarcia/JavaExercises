package com.curso.ejercicios.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EJERCICIO 07 · Lambdas y Streams
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Filtrar las personas mayores de edad y mostrar sus nombres ordenados.</li>
 *   <li>Calcular la media de edades.</li>
 *   <li>Agrupar las personas por ciudad.</li>
 *   <li>Extra: unir los nombres en un único String separado por comas.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code filter}, {@code map}, {@code sorted}, {@code collect}, {@code groupingBy},
 * method references, {@code record}.
 */
public final class Ejercicio07_Streams {

    /** Tipo de dato inmutable de ejemplo (Java 16+). */
    public record Persona(String nombre, int edad, String ciudad) {
    }

    private static final List<Persona> PERSONAS = List.of(
            new Persona("Ana", 31, "Madrid"),
            new Persona("Luis", 17, "Sevilla"),
            new Persona("Marta", 24, "Madrid"),
            new Persona("Iván", 45, "Bilbao"),
            new Persona("Sara", 19, "Sevilla"),
            new Persona("Pablo", 38, "Bilbao"));

    private Ejercicio07_Streams() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 07 · Streams ────────────────────────");

        List<String> mayores = nombresMayores(PERSONAS, 18);
        System.out.println("Mayores de edad (alfabéticamente): " + mayores);

        System.out.println("Media de edades: " + String.format("%.1f", mediaEdades(PERSONAS)));

        System.out.println("Por ciudad: " + agruparPorCiudad(PERSONAS));

        System.out.println("Todos los nombres: " + listarNombres(PERSONAS));

        System.out.println("Mayor edad: " + PERSONAS.stream()
                .max(Comparator.comparingInt(Persona::edad))
                .map(Persona::nombre)
                .orElse("(nadie)"));
    }

    /** Nombres de las personas con edad ≥ {@code edadMinima}, ordenados alfabéticamente. */
    public static List<String> nombresMayores(List<Persona> personas, int edadMinima) {
        return personas.stream()
                .filter(p -> p.edad() >= edadMinima)
                .map(Persona::nombre)
                .sorted()
                .toList();
    }

    /** Media de edades; devuelve 0 si la lista está vacía. */
    public static double mediaEdades(List<Persona> personas) {
        return personas.stream()
                .mapToInt(Persona::edad)
                .average()
                .orElse(0.0);
    }

    /** Ciudad → número de personas que viven en ella. */
    public static Map<String, Long> agruparPorCiudad(List<Persona> personas) {
        return personas.stream()
                .collect(Collectors.groupingBy(Persona::ciudad, Collectors.counting()));
    }

    /** "Ana, Luis, Marta" */
    public static String listarNombres(List<Persona> personas) {
        return personas.stream()
                .map(Persona::nombre)
                .collect(Collectors.joining(", "));
    }
}
