package com.curso.ejercicios.colecciones;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 06 · Conteo de palabras")
class ConteoPalabrasTest {

    @Test
    @DisplayName("Cuenta las repeticiones ignorando mayúsculas")
    void contarPalabras() {
        Map<String, Integer> conteo =
                Ejercicio06_ConteoPalabras.contarPalabras("Java es fácil y Java es potente");

        assertEquals(2, conteo.get("java"));
        assertEquals(2, conteo.get("es"));
        assertEquals(1, conteo.get("fácil"));     // no se parte en "f" + "cil"
        assertEquals(1, conteo.get("potente"));
        assertEquals(5, conteo.size());           // java, es, fácil, y, potente
    }

    @Test
    @DisplayName("Conserva el orden de primera aparición")
    void ordenDeAparicion() {
        Map<String, Integer> conteo =
                Ejercicio06_ConteoPalabras.contarPalabras("b a b a c");

        assertEquals(List.of("b", "a", "c"), List.copyOf(conteo.keySet()));
    }

    @Test
    @DisplayName("Texto vacío, nulo o solo signos → mapa vacío")
    void entradasVacias() {
        assertTrue(Ejercicio06_ConteoPalabras.contarPalabras(null).isEmpty());
        assertTrue(Ejercicio06_ConteoPalabras.contarPalabras("").isEmpty());
        assertTrue(Ejercicio06_ConteoPalabras.contarPalabras("  ¡! ¿? ...  ").isEmpty());
    }

    @Test
    @DisplayName("Filtra por longitud mínima de palabra")
    void filtrarPorLongitud() {
        Map<String, Integer> filtrado =
                Ejercicio06_ConteoPalabras.contarPalabrasConLongitudMinima("hola que tal Java", 4);

        assertEquals(2, filtrado.size());                 // hola y Java
        assertTrue(filtrado.containsKey("hola"));
        assertTrue(filtrado.containsKey("java"));
        assertTrue(!filtrado.containsKey("que"));
    }

    @Test
    @DisplayName("porFrecuencia ordena de mayor a menor")
    void porFrecuencia() {
        Map<String, Integer> conteo = Map.of("uno", 1, "tres", 5, "dos", 3);
        List<Map.Entry<String, Integer>> ordenado = Ejercicio06_ConteoPalabras.porFrecuencia(conteo);

        assertEquals(3, ordenado.size());
        assertEquals("tres", ordenado.get(0).getKey());
        assertEquals(5, ordenado.get(0).getValue());
        assertEquals("uno", ordenado.get(2).getKey());
        assertTrue(ordenado.get(0).getValue() >= ordenado.get(1).getValue());
        assertTrue(ordenado.get(1).getValue() >= ordenado.get(2).getValue());
    }
}
