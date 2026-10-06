package com.curso.ejercicios.arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Ejercicio 04 · Arrays y String")
class ArraysYStringsTest {

    @Test
    @DisplayName("Invertir un array no modifica el original")
    void invertirArray() {
        int[] original = {1, 2, 3, 4};
        int[] invertido = Ejercicio04_ArraysYStrings.invertir(original);

        assertArrayEquals(new int[]{4, 3, 2, 1}, invertido);
        assertArrayEquals(new int[]{1, 2, 3, 4}, original);
    }

    @Test
    @DisplayName("Invertir un array de un solo elemento")
    void invertirArrayUnitario() {
        assertArrayEquals(new int[]{7}, Ejercicio04_ArraysYStrings.invertir(new int[]{7}));
    }

    @Test
    @DisplayName("Invertir las palabras de una frase")
    void invertirPalabras() {
        assertEquals("tal que hola", Ejercicio04_ArraysYStrings.invertirPalabras("hola que tal"));
        assertEquals("única", Ejercicio04_ArraysYStrings.invertirPalabras("única"));
    }

    @Test
    @DisplayName("Máximo y mínimo")
    void maximoYMinimo() {
        int[] datos = {5, 8, 2, 9, 1};
        assertEquals(9, Ejercicio04_ArraysYStrings.maximo(datos));
        assertEquals(1, Ejercicio04_ArraysYStrings.minimo(datos));
    }

    @Test
    @DisplayName("Array vacío → IllegalArgumentException")
    void arrayVacioLanzaError() {
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio04_ArraysYStrings.maximo(new int[]{}));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio04_ArraysYStrings.minimo(null));
    }

    @Test
    @DisplayName("Palabra más larga")
    void palabraMasLarga() {
        assertEquals("genial", Ejercicio04_ArraysYStrings.palabraMasLarga("Java es genial"));
    }
}
