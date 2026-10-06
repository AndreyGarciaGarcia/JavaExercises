package com.curso.ejercicios.ficheros;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * EJERCICIO 11 · Entrada/Salida de ficheros (java.nio)
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Crea la carpeta {@code data/} si no existe.</li>
 *   <li>Escribe un informe de líneas y luego léelo.</li>
 *   <li>Cuenta cuántas veces aparece cada palabra en un fichero de texto.</li>
 *   <li>Extra: recorre el árbol de {@code src} contando los ficheros {@code .java}.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code Path}, {@code Files}, try-with-resources, {@code Stream&lt;Path&gt;}, UTF-8.
 */
public final class Ejercicio11_Ficheros {

    private Ejercicio11_Ficheros() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("\n── Ejercicio 11 · Ficheros ───────────────────────");

        Path carpeta = Path.of("data");
        Files.createDirectories(carpeta);

        Path informe = carpeta.resolve("informe.txt");
        guardarLineas(informe, List.of(
                "Java es un lenguaje orientado a objetos",
                "La JVM ejecuta bytecode en cualquier sistema",
                "Las colecciones son la parte más usada de la API",
                "Java es portable y Java es robusto"));

        System.out.println("Fichero escrito → " + informe.toAbsolutePath());
        List<String> lineas = leerLineas(informe);
        System.out.println("Líneas leídas   → " + lineas.size());
        lineas.forEach(l -> System.out.println("   · " + l));

        Map<String, Integer> conteo = contarPalabras(String.join(" ", lineas));
        System.out.println("Palabras distintas → " + conteo.size());
        conteo.forEach((p, n) -> System.out.println("   " + p + " → " + n));

        Path raiz = Path.of("src");
        if (Files.isDirectory(raiz)) {
            try (Stream<Path> arbol = Files.walk(raiz)) {
                long java = arbol.filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".java"))
                        .count();
                System.out.println("Ficheros .java en " + raiz.toAbsolutePath() + " → " + java);
            }
        }
    }

    /** Escribe una línea por elemento (crea o sobreescribe el fichero). */
    public static void guardarLineas(Path destino, List<String> lineas) throws IOException {
        if (destino.getParent() != null) {
            Files.createDirectories(destino.getParent());
        }
        Files.write(destino, lineas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /** Lee todo el fichero como lista de líneas (UTF-8). */
    public static List<String> leerLineas(Path origen) throws IOException {
        if (!Files.exists(origen)) {
            throw new IOException("el fichero no existe: " + origen);
        }
        return Files.readAllLines(origen, StandardCharsets.UTF_8);
    }

    /** Lee el fichero completo a un único String (Java 11+). */
    public static String leerTexto(Path origen) throws IOException {
        return Files.readString(origen, StandardCharsets.UTF_8);
    }

    /** Cuenta apariciones de cada palabra ignorando mayúsculas y signos. */
    public static Map<String, Integer> contarPalabras(String texto) {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        if (texto == null || texto.isBlank()) {
            return conteo;
        }
        for (String palabra : texto.toLowerCase(java.util.Locale.ROOT).split("[^a-záéíóúñü]+")) {
            if (!palabra.isBlank()) {
                conteo.merge(palabra, 1, Integer::sum);
            }
        }
        return conteo;
    }
}
