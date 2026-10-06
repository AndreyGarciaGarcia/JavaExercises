package com.curso.ejercicios.retos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * EJERCICIO 19 (reto) · Gestión de biblioteca
 *
 * <h2>Enunciado</h2>
 * Un programa de préstamo de libros que integra <strong>POO + colecciones +
 * ficheros</strong>:
 * <ol>
 *   <li>Dar de alta libros (sin duplicar por ISBN).</li>
 *   <li>Prestar y devolver controlando que no haya más préstamos que ejemplares.</li>
 *   <li>Consultar disponibles y buscar por autor (sin distinguir mayúsculas).</li>
 *   <li>Guardar el inventario en un fichero y leerlo de vuelta.</li>
 * </ol>
 *
 * <p>Toda la lógica está en {@link Biblioteca} (métodos puros y verificables);
 * el {@code main} solo orquesta.</p>
 */
public final class Ejercicio19_GestionBiblioteca {

    private Ejercicio19_GestionBiblioteca() {
    }

    /** Libro inmutable. */
    public record Libro(String isbn, String titulo, String autor, int ejemplares) {

        public Libro {
            if (isbn == null || isbn.isBlank()) {
                throw new IllegalArgumentException("El ISBN es obligatorio");
            }
            if (titulo == null || titulo.isBlank()) {
                throw new IllegalArgumentException("El título es obligatorio");
            }
            if (autor == null || autor.isBlank()) {
                throw new IllegalArgumentException("El autor es obligatorio");
            }
            if (ejemplares < 0) {
                throw new IllegalArgumentException("Ejemplares no negativos: " + ejemplares);
            }
        }

        /** Formato de una línea del fichero: ISBN|Título|Autor|Ejemplares */
        public String aLinea() {
            return String.join("|", isbn, titulo, autor, String.valueOf(ejemplares));
        }

        /** Lee un libro de su línea; devuelve empty si la línea está rota. */
        public static Optional<Libro> desdeLinea(String linea) {
            if (linea == null || linea.isBlank()) {
                return Optional.empty();
            }
            String[] partes = linea.split("\\|", -1);
            if (partes.length != 4) {
                return Optional.empty();
            }
            try {
                return Optional.of(new Libro(partes[0].trim(), partes[1].trim(),
                        partes[2].trim(), Integer.parseInt(partes[3].trim())));
            } catch (IllegalArgumentException e) {   // incluye NumberFormatException
                return Optional.empty();
            }
        }

        @Override
        public String toString() {
            return titulo + " — " + autor + " [" + ejemplares + " ej.]";
        }
    }

    /** Inventario de libros con préstamos. */
    public static class Biblioteca {

        private final Map<String, Libro> libros = new LinkedHashMap<>();
        private final Map<String, Integer> prestamos = new LinkedHashMap<>();

        /** Alta de un libro. Lanza error si el ISBN ya existe. */
        public void darDeAlta(Libro libro) {
            if (libros.containsKey(libro.isbn())) {
                throw new IllegalArgumentException("ISBN duplicado: " + libro.isbn());
            }
            libros.put(libro.isbn(), libro);
            prestamos.put(libro.isbn(), 0);
        }

        /** @return nº de libros dados de alta */
        public int total() {
            return libros.size();
        }

        /** Préstamo de un ejemplar. */
        public boolean prestar(String isbn) {
            Libro libro = libros.get(isbn);
            if (libro == null) {
                throw new IllegalArgumentException("ISBN desconocido: " + isbn);
            }
            int yaPrestados = prestamos.getOrDefault(isbn, 0);
            if (yaPrestados >= libro.ejemplares()) {
                return false;                      // no quedan ejemplares
            }
            prestamos.put(isbn, yaPrestados + 1);
            return true;
        }

        /** Devolución de un ejemplar. @return false si no había ninguno prestado */
        public boolean devolver(String isbn) {
            if (!libros.containsKey(isbn)) {
                throw new IllegalArgumentException("ISBN desconocido: " + isbn);
            }
            int yaPrestados = prestamos.getOrDefault(isbn, 0);
            if (yaPrestados <= 0) {
                return false;
            }
            prestamos.put(isbn, yaPrestados - 1);
            return true;
        }

        /** Cuántos ejemplares hay ahora mismo libres. */
        public int disponibles(String isbn) {
            Libro libro = libros.get(isbn);
            if (libro == null) {
                throw new IllegalArgumentException("ISBN desconocido: " + isbn);
            }
            return libro.ejemplares() - prestamos.getOrDefault(isbn, 0);
        }

        public int prestados(String isbn) {
            if (!libros.containsKey(isbn)) {
                throw new IllegalArgumentException("ISBN desconocido: " + isbn);
            }
            return prestamos.getOrDefault(isbn, 0);
        }

        /** Libros con algún ejemplar libre, ordenados por título. */
        public List<Libro> disponibles() {
            return libros.values().stream()
                    .filter(l -> disponibles(l.isbn()) > 0)
                    .sorted(Comparator.comparing(Libro::titulo))
                    .toList();
        }

        /** Búsqueda por autor (ignorando mayúsculas y tildes de espacio). */
        public List<Libro> porAutor(String autor) {
            if (autor == null || autor.isBlank()) {
                return List.of();
            }
            String busqueda = autor.trim().toLowerCase(java.util.Locale.ROOT);
            return libros.values().stream()
                    .filter(l -> l.autor().toLowerCase(java.util.Locale.ROOT).contains(busqueda))
                    .sorted(Comparator.comparing(Libro::titulo))
                    .toList();
        }

        public Optional<Libro> buscarPorIsbn(String isbn) {
            return Optional.ofNullable(libros.get(isbn));
        }

        /** Guarda el inventario en un fichero (una línea por libro). */
        public void guardarEn(Path fichero) throws IOException {
            List<String> lineas = new ArrayList<>();
            libros.values().forEach(l -> lineas.add(l.aLinea()));
            Path directorio = fichero.toAbsolutePath().getParent();
            if (directorio != null) {
                Files.createDirectories(directorio);
            }
            Files.write(fichero, lineas, StandardCharsets.UTF_8);
        }

        /** Carga el inventario desde un fichero; ignora las líneas rotas. */
        public static Biblioteca cargarDe(Path fichero) throws IOException {
            Biblioteca biblioteca = new Biblioteca();
            if (!Files.exists(fichero)) {
                return biblioteca;
            }
            for (String linea : Files.readAllLines(fichero, StandardCharsets.UTF_8)) {
                Libro.desdeLinea(linea).ifPresent(biblioteca::darDeAlta);
            }
            return biblioteca;
        }

        /** Inventario formateado para mostrar. */
        public String listar() {
            if (libros.isEmpty()) {
                return "  (sin libros)";
            }
            StringBuilder sb = new StringBuilder();
            libros.values().forEach(l -> sb.append(String.format("  %-40s libres %d/%d%n",
                    l.titulo() + " — " + l.autor(), disponibles(l.isbn()), l.ejemplares())));
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 19 · Gestión de biblioteca ──────────");
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.darDeAlta(new Libro("978-84", "El Quijote", "Cervantes", 3));
        biblioteca.darDeAlta(new Libro("978-85", "Clean Code", "Martin", 2));
        biblioteca.darDeAlta(new Libro("978-86", "Effective Java", "Bloch", 1));

        System.out.println(biblioteca.listar());

        System.out.println("  Prestar Clean Code      → " + biblioteca.prestar("978-85"));
        System.out.println("  Prestar Clean Code      → " + biblioteca.prestar("978-85"));
        System.out.println("  Prestar Clean Code otra → " + biblioteca.prestar("978-85") + " (sin ejemplares)");
        System.out.println("  Disponibles Clean Code  → " + biblioteca.disponibles("978-85"));

        System.out.println("  Búsqueda por autor 'martin': " + biblioteca.porAutor("martin"));
        System.out.println("  Disponibles: ");
        biblioteca.disponibles().forEach(l -> System.out.println("    " + l.titulo()));

        biblioteca.devolver("978-85");
        System.out.println("  Tras devolver, libres de Clean Code → " + biblioteca.disponibles("978-85"));

        try {
            Path fichero = Path.of("data", "biblioteca.txt");
            biblioteca.guardarEn(fichero);
            Biblioteca recargada = Biblioteca.cargarDe(fichero);
            System.out.println("  Guardado en " + fichero + " y recargado con " + recargada.total() + " libros");
        } catch (IOException e) {
            System.out.println("  ✗ Error de ficheros: " + e.getMessage());
        }
    }
}
