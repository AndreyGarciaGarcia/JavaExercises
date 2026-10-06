package com.curso.ejercicios.streams;

import com.curso.ejercicios.streams.Ejercicio07_Streams.Persona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 07 · Streams")
class StreamsTest {

    private final List<Persona> personas = List.of(
            new Persona("Ana", 31, "Madrid"),
            new Persona("Luis", 17, "Sevilla"),
            new Persona("Marta", 24, "Madrid"));

    @Test
    @DisplayName("Filtra mayores de edad y ordena alfabéticamente")
    void nombresMayores() {
        assertEquals(List.of("Ana", "Marta"), Ejercicio07_Streams.nombresMayores(personas, 18));
        assertEquals(List.of("Ana", "Luis", "Marta"), Ejercicio07_Streams.nombresMayores(personas, 0));
    }

    @Test
    @DisplayName("Media de edades")
    void mediaEdades() {
        // (31 + 17 + 24) / 3 = 24.0
        assertEquals(24.0, Ejercicio07_Streams.mediaEdades(personas));
    }

    @Test
    @DisplayName("Lista vacía → media 0")
    void mediaListaVacia() {
        assertEquals(0.0, Ejercicio07_Streams.mediaEdades(List.of()));
    }

    @Test
    @DisplayName("Agrupa por ciudad")
    void agruparPorCiudad() {
        Map<String, Long> porCiudad = Ejercicio07_Streams.agruparPorCiudad(personas);
        assertEquals(2, porCiudad.get("Madrid"));
        assertEquals(1, porCiudad.get("Sevilla"));
        assertEquals(2, porCiudad.size());
    }

    @Test
    @DisplayName("Une los nombres con comas")
    void listarNombres() {
        String resultado = Ejercicio07_Streams.listarNombres(personas);
        assertEquals("Ana, Luis, Marta", resultado);
        assertTrue(resultado.startsWith("Ana"));
    }
}
