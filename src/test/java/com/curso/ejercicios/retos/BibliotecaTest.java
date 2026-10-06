package com.curso.ejercicios.retos;

import com.curso.ejercicios.retos.Ejercicio19_GestionBiblioteca.Biblioteca;
import com.curso.ejercicios.retos.Ejercicio19_GestionBiblioteca.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 19 · Gestión de biblioteca")
class BibliotecaTest {

    @TempDir
    Path temporal;

    private Biblioteca biblioteca;

    @BeforeEach
    void preparar() {
        biblioteca = new Biblioteca();
        biblioteca.darDeAlta(new Libro("978-1", "El Quijote", "Cervantes", 3));
        biblioteca.darDeAlta(new Libro("978-2", "Clean Code", "Martin", 1));
        biblioteca.darDeAlta(new Libro("978-3", "Effective Java", "Bloch", 1));
    }

    @Test
    @DisplayName("El alta rechaza los ISBN duplicados")
    void alta() {
        assertEquals(3, biblioteca.total());
        assertThrows(IllegalArgumentException.class,
                () -> biblioteca.darDeAlta(new Libro("978-1", "Otro título", "X", 1)));
        assertThrows(IllegalArgumentException.class,
                () -> biblioteca.darDeAlta(new Libro("", "Sin ISBN", "X", 1)));
        assertEquals(3, biblioteca.total());
    }

    @Test
    @DisplayName("No se presta más de lo que hay: stock y control de devoluciones")
    void prestarYDevolver() {
        assertEquals(3, biblioteca.disponibles("978-1"));
        assertTrue(biblioteca.prestar("978-1"));
        assertTrue(biblioteca.prestar("978-1"));
        assertTrue(biblioteca.prestar("978-1"));
        assertEquals(0, biblioteca.disponibles("978-1"));

        assertFalse(biblioteca.prestar("978-1"), "no quedan ejemplares");
        assertEquals(3, biblioteca.prestados("978-1"));

        assertTrue(biblioteca.devolver("978-1"));
        assertEquals(1, biblioteca.disponibles("978-1"));
        assertFalse(biblioteca.devolver("978-2"), "no había ninguno prestado");

        assertThrows(IllegalArgumentException.class, () -> biblioteca.prestar("desconocido"));
        assertThrows(IllegalArgumentException.class, () -> biblioteca.disponibles("desconocido"));
        assertThrows(IllegalArgumentException.class, () -> biblioteca.devolver("desconocido"));
    }

    @Test
    @DisplayName("Disponibles y búsqueda por autor (ignorando mayúsculas)")
    void consultas() {
        biblioteca.prestar("978-2");           // se agota Clean Code

        List<Libro> conEjemplares = biblioteca.disponibles();
        assertEquals(2, conEjemplares.size());
        assertEquals(List.of("Effective Java", "El Quijote"),
                conEjemplares.stream().map(Libro::titulo).toList());

        assertEquals(1, biblioteca.porAutor("MARTIN").size());
        assertEquals(2, biblioteca.porAutor("c").size());      // Cervantes + Clean Code
        assertTrue(biblioteca.porAutor("desconocido").isEmpty());
        assertTrue(biblioteca.porAutor("  ").isEmpty());
        assertEquals("El Quijote", biblioteca.porAutor("cervantes").get(0).titulo());
        assertTrue(biblioteca.buscarPorIsbn("978-3").isPresent());
        assertTrue(biblioteca.buscarPorIsbn("000-0").isEmpty());
    }

    @Test
    @DisplayName("Guardar y cargar reconstruye el inventario igual")
    void guardarYCargar() throws IOException {
        Path fichero = temporal.resolve("inventario").resolve("biblioteca.txt");
        biblioteca.prestar("978-1");
        biblioteca.guardarEn(fichero);

        assertTrue(Files.exists(fichero));
        List<String> lineas = Files.readAllLines(fichero, StandardCharsets.UTF_8);
        assertEquals(3, lineas.size());
        assertEquals("978-1|El Quijote|Cervantes|3", lineas.get(0));

        Biblioteca recargada = Biblioteca.cargarDe(fichero);
        assertEquals(3, recargada.total());
        assertEquals(3, recargada.disponibles("978-1"));      // los préstamos NO se guardan
        assertEquals("El Quijote", recargada.buscarPorIsbn("978-1").orElseThrow().titulo());
    }

    @Test
    @DisplayName("Cargar un fichero inexistente o roto no rompe nada")
    void cargarRaro() throws IOException {
        Path inexistente = temporal.resolve("no-existe.txt");
        assertEquals(0, Biblioteca.cargarDe(inexistente).total());

        Path roto = temporal.resolve("roto.txt");
        Files.write(roto, List.of(
                "978-9|Libro bueno|Autor|2",
                "esto no es una línea válida",
                "",
                "|sin isbn|otro|1"),
                StandardCharsets.UTF_8);

        Biblioteca parcial = Biblioteca.cargarDe(roto);
        assertEquals(1, parcial.total());
        assertEquals("Libro bueno", parcial.buscarPorIsbn("978-9").orElseThrow().titulo());
    }

    @Test
    @DisplayName("El objeto Libro valida sus datos y se serializa en una línea")
    void libro() {
        Libro libro = new Libro("978-84", "Clean Code", "Martin", 2);
        assertEquals("978-84|Clean Code|Martin|2", libro.aLinea());
        assertEquals(libro, Libro.desdeLinea(libro.aLinea()).orElseThrow());
        assertTrue(Libro.desdeLinea("línea rota").isEmpty());
        assertTrue(Libro.desdeLinea(null).isEmpty());

        assertThrows(IllegalArgumentException.class, () -> new Libro("i", null, "a", 1));
        assertThrows(IllegalArgumentException.class, () -> new Libro("i", "t", "a", -1));
        assertTrue(libro.toString().contains("Clean Code"));
        assertFalse(biblioteca.listar().isBlank());
    }
}
