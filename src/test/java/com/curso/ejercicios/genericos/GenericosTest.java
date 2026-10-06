package com.curso.ejercicios.genericos;

import com.curso.ejercicios.genericos.Ejercicio15_Genericos.Caja;
import com.curso.ejercicios.genericos.Ejercicio15_Genericos.Par;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 15 · Genéricos y PECS")
class GenericosTest {

    @Test
    @DisplayName("La caja guarda y devuelve el tipo que le metiste")
    void caja() {
        Caja<String> texto = new Caja<>();
        assertTrue(texto.estaVacia());
        texto.poner("hola");
        assertEquals("hola", texto.sacar());
        assertTrue(!texto.estaVacia());

        Caja<Integer> numero = new Caja<>();
        numero.poner(42);
        assertEquals(42, numero.sacar());
        // Si la caja fuera de Object, esto compilaría igual pero perdería seguridad
        assertEquals("Caja[42]", String.valueOf(numero));
    }

    @Test
    @DisplayName("maximo()/minimo() con límite superior Comparable")
    void maximoMinimo() {
        List<Integer> nums = List.of(3, 9, 4, 7, 1);
        assertEquals(9, Ejercicio15_Genericos.maximo(nums));
        assertEquals(1, Ejercicio15_Genericos.minimo(nums));

        List<String> palabras = List.of("pera", "melocotón", "banana");
        assertEquals("pera", Ejercicio15_Genericos.maximo(palabras));
        assertEquals("banana", Ejercicio15_Genericos.minimo(palabras));
        assertEquals(7.5, Ejercicio15_Genericos.maximo(List.of(1.0, 7.5, 3.0)));

        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio15_Genericos.maximo(List.<String>of()));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio15_Genericos.<String>minimo(null));
    }

    @Test
    @DisplayName("PECS: lee con ? extends y escribe con ? super")
    void pecs() {
        List<Integer> origen = List.of(1, 2, 3);

        // El destino es un consumidor: basta con que acepte Integer
        List<Number> destino = new ArrayList<>();
        Ejercicio15_Genericos.copiarNumeros(origen, destino);
        assertEquals(List.of(1, 2, 3), destino);
        assertEquals(6, destino.stream().mapToInt(Number::intValue).sum());

        // La fuente es un productor: un List<Integer> sirve para un origen de Integer
        List<Object> otroDestino = new ArrayList<>();
        Ejercicio15_Genericos.copiarNumeros(origen, otroDestino);
        assertEquals(3, otroDestino.size());
    }

    @Test
    @DisplayName("Los pares se convierten a mapa conservando el orden")
    void aMapa() {
        List<Par<String, Integer>> pares =
                List.of(new Par<>("uno", 1), new Par<>("dos", 2), new Par<>("tres", 3));
        Map<String, Integer> mapa = Ejercicio15_Genericos.aMapa(pares);

        assertEquals(3, mapa.size());
        assertEquals(List.of("uno", "dos", "tres"), List.copyOf(mapa.keySet()));
        assertEquals(2, mapa.get("dos"));
        assertTrue(Ejercicio15_Genericos.aMapa(List.of()).isEmpty());

        assertThrows(IllegalArgumentException.class, () -> new Par<>(null, 1));
    }

    @Test
    @DisplayName("intercambiar() funciona con cualquier tipo y valida índices")
    void intercambiar() {
        List<String> lista = new ArrayList<>(List.of("a", "b", "c"));
        Ejercicio15_Genericos.intercambiar(lista, 0, 2);
        assertEquals(List.of("c", "b", "a"), lista);

        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3));
        Ejercicio15_Genericos.intercambiar(nums, 1, 1);
        assertEquals(List.of(1, 2, 3), nums);

        assertThrows(IndexOutOfBoundsException.class,
                () -> Ejercicio15_Genericos.intercambiar(lista, 0, 99));
    }

    @Test
    @DisplayName("repetir() devuelve exactamente tantos elementos como pidas")
    void repetir() {
        assertEquals(List.of("java", "java", "java"), Ejercicio15_Genericos.repetir("java", 3));
        assertTrue(Ejercicio15_Genericos.repetir("nada", 0).isEmpty());
        assertThrows(IllegalArgumentException.class, () -> Ejercicio15_Genericos.repetir("x", -1));
    }
}
