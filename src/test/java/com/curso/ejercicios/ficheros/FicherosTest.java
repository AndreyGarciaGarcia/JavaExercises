package com.curso.ejercicios.ficheros;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 11 · Ficheros")
class FicherosTest {

    @TempDir
    Path carpetaTemporal;

    @Test
    @DisplayName("Escribir y leer líneas conserva el contenido")
    void escribirYLeer() throws IOException {
        Path fichero = carpetaTemporal.resolve("sub/carolina.txt");   // la carpeta aún no existe
        List<String> lineas = List.of("uno", "dos", "tres");

        Ejercicio11_Ficheros.guardarLineas(fichero, lineas);

        assertTrue(Files.exists(fichero));
        assertEquals(lineas, Ejercicio11_Ficheros.leerLineas(fichero));

        // readString devuelve el separador de la plataforma y Files.write añade
        // un salto de línea tras cada línea → normalizamos y dejamos el final
        String texto = Ejercicio11_Ficheros.leerTexto(fichero).replace("\r\n", "\n");
        assertEquals("uno\ndos\ntres\n", texto);
    }

    @Test
    @DisplayName("Leer un fichero inexistente lanza IOException")
    void leerInexistente() {
        Path noExiste = carpetaTemporal.resolve("no-existe.txt");
        assertThrows(IOException.class, () -> Ejercicio11_Ficheros.leerLineas(noExiste));
        assertThrows(IOException.class, () -> Ejercicio11_Ficheros.leerTexto(noExiste));
    }

    @Test
    @DisplayName("Conteo de palabras ignora mayúsculas y signos")
    void contarPalabras() {
        Map<String, Integer> conteo = Ejercicio11_Ficheros.contarPalabras("Java, java JAVA; es genial");

        assertEquals(3, conteo.get("java"));
        assertEquals(1, conteo.get("genial"));
        assertEquals(1, conteo.get("es"));
        assertEquals(3, conteo.size());
    }

    @Test
    @DisplayName("Texto vacío o nulo devuelve un mapa vacío")
    void contarPalabrasVacias() {
        assertTrue(Ejercicio11_Ficheros.contarPalabras("").isEmpty());
        assertTrue(Ejercicio11_Ficheros.contarPalabras(null).isEmpty());
        assertTrue(Ejercicio11_Ficheros.contarPalabras("  ...  ").isEmpty());
    }
}
