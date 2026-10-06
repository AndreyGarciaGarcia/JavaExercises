package com.curso.ejercicios.retos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 20 · Ahorcado")
class AhorcadoTest {

    @Test
    @DisplayName("Estado inicial: nada descubierto y 6 intentos")
    void inicial() {
        Ejercicio20_Ahorcado.Partida partida = new Ejercicio20_Ahorcado.Partida("java");

        assertEquals("_ _ _ _", partida.mascara());
        assertEquals(0, partida.getFallos());
        assertEquals(6, partida.intentosRestantes());
        assertTrue(partida.quedanLetras());
        assertFalse(partida.ganada());
        assertFalse(partida.perdida());
        assertFalse(partida.terminada());
        assertTrue(partida.getUsadas().isEmpty());
        assertEquals("java", partida.getPalabra());
    }

    @Test
    @DisplayName("Acierto: la letra aparece y NO consume intento")
    void acierto() {
        Ejercicio20_Ahorcado.Partida partida = new Ejercicio20_Ahorcado.Partida("java");

        assertEquals(1, partida.intentar('j'));
        assertEquals("j _ _ _", partida.mascara());
        assertEquals(0, partida.getFallos());
        assertEquals(6, partida.intentosRestantes());

        assertEquals(1, partida.intentar('A'));      // mayúsculas → misma letra
        assertEquals("j a _ a", partida.mascara());
        assertEquals(0, partida.getFallos());
    }

    @Test
    @DisplayName("Fallo: suma intento y la máscara no cambia")
    void fallo() {
        Ejercicio20_Ahorcado.Partida partida = new Ejercicio20_Ahorcado.Partida("java");

        assertEquals(0, partida.intentar('z'));
        assertEquals(1, partida.getFallos());
        assertEquals(5, partida.intentosRestantes());
        assertEquals("_ _ _ _", partida.mascara());
        assertEquals(1, partida.getUsadas().size());
    }

    @Test
    @DisplayName("Repetir una letra devuelve -1 y no cuenta")
    void letraRepetida() {
        Ejercicio20_Ahorcado.Partida partida = new Ejercicio20_Ahorcado.Partida("java");

        assertEquals(1, partida.intentar('j'));
        assertEquals(-1, partida.intentar('j'), "ya usada → no se puntúa");
        assertEquals(0, partida.getFallos());

        assertEquals(0, partida.intentar('z'));
        assertEquals(-1, partida.intentar('z'), "el fallo tampoco se repite");
        assertEquals(1, partida.getFallos());
    }

    @Test
    @DisplayName("Ganar: todas las letras descubiertas")
    void victoria() {
        Ejercicio20_Ahorcado.Partida partida = new Ejercicio20_Ahorcado.Partida("hola");

        for (char c : "hloa".toCharArray()) {
            assertEquals(1, partida.intentar(c), "falló en " + c);
        }

        assertTrue(partida.ganada());
        assertTrue(partida.terminada());
        assertFalse(partida.perdida());
        assertEquals("h o l a", partida.mascara());
        assertTrue(partida.letrasPendientes().isEmpty());
        assertTrue(partida.veredicto().startsWith("¡GANASTE!"));
        assertTrue(partida.veredicto().contains("hola"));
    }

    @Test
    @DisplayName("Perder: 6 fallos y se descubre la palabra")
    void derrota() {
        Ejercicio20_Ahorcado.Partida partida = new Ejercicio20_Ahorcado.Partida("sol");

        for (char c : "bmnpqrtvwxyzk".toCharArray()) {
            if (partida.perdida()) {
                break;
            }
            partida.intentar(c);
        }

        assertTrue(partida.perdida());
        assertTrue(partida.terminada());
        assertFalse(partida.ganada());
        assertEquals(0, partida.intentosRestantes());
        assertEquals(List.of('s', 'o', 'l'), partida.letrasPendientes());   // ninguna se descubrió
        assertTrue(partida.veredicto().startsWith("PERDISTE"));
        assertTrue(partida.veredicto().contains("sol"));
    }

    @ParameterizedTest(name = "«{0}» no es una letra")
    @ValueSource(strings = {"1", " ", "?"})
    @DisplayName("Solo se admiten letras")
    void letras(String entrada) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ejercicio20_Ahorcado.Partida("java").intentar(entrada.charAt(0)));
    }

    @Test
    @DisplayName("La palabra no puede estar vacía y el diccionario es fijo")
    void validarYDiccionario() {
        assertThrows(IllegalArgumentException.class, () -> new Ejercicio20_Ahorcado.Partida(""));
        assertThrows(IllegalArgumentException.class, () -> new Ejercicio20_Ahorcado.Partida("   "));
        assertThrows(IllegalArgumentException.class, () -> new Ejercicio20_Ahorcado.Partida(null));

        assertFalse(Ejercicio20_Ahorcado.diccionario().isEmpty());
        assertTrue(Ejercicio20_Ahorcado.diccionario().contains("polimorfismo"));
        // La lista es inmutable
        assertThrows(UnsupportedOperationException.class,
                () -> Ejercicio20_Ahorcado.diccionario().add("nueva"));

        // La semilla fija reproduce siempre la misma palabra
        String una = Ejercicio20_Ahorcado.palabraConSemilla(42);
        String dos = Ejercicio20_Ahorcado.palabraConSemilla(42);
        assertEquals(Ejercicio20_Ahorcado.palabraConSemilla(42), una);
        assertTrue(Ejercicio20_Ahorcado.diccionario().contains(una));
        assertTrue(Ejercicio20_Ahorcado.diccionario().size() >= Ejercicio20_Ahorcado.MAX_INTENTOS);
    }

    @Test
    @DisplayName("El diccionario está limpio: todo minúsculas, letras y sin repetidos")
    void diccionarioCoherente() {
        java.util.Set<String> unicos = new java.util.HashSet<>();
        for (String palabra : Ejercicio20_Ahorcado.diccionario()) {
            assertFalse(palabra.isBlank(), "palabra en blanco");
            assertEquals(palabra.toLowerCase(java.util.Locale.ROOT), palabra,
                    "'" + palabra + "' no está en minúsculas");
            assertTrue(palabra.chars().allMatch(Character::isLetter),
                    "'" + palabra + "' contiene no-letras");
            assertTrue(palabra.length() >= 4, "'" + palabra + "' es demasiado corta");
            assertTrue(unicos.add(palabra), "'" + palabra + "' está repetida");
        }
        assertEquals(Ejercicio20_Ahorcado.diccionario().size(), unicos.size());
    }
}
