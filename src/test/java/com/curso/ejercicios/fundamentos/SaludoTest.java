package com.curso.ejercicios.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Ejercicio 01 · Saludo")
class SaludoTest {

    @Test
    @DisplayName("Saluda con el nombre indicado")
    void saludaConNombre() {
        assertEquals("Hola, Ana! Bienvenido a Java.", Ejercicio01_Saludo.saludar("Ana"));
        assertEquals("Hola, Luis! Bienvenido a Java.", Ejercicio01_Saludo.saludar("Luis"));
    }

    @Test
    @DisplayName("Los espacios sobrantes se recortan")
    void recortaEspacios() {
        assertEquals("Hola, Ana! Bienvenido a Java.", Ejercicio01_Saludo.saludar("   Ana   "));
    }

    @ParameterizedTest(name = "«{0}» → error")
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Nombre nulo, vacío o en blanco → IllegalArgumentException")
    void nombreInvalido(String nombre) {
        assertThrows(IllegalArgumentException.class, () -> Ejercicio01_Saludo.saludar(nombre));
    }

    @ParameterizedTest(name = "{0} → «{1}»")
    @CsvSource(value = {
            "Ana|Hola, Ana! Bienvenido a Java.",
            "Álvaro|Hola, Álvaro! Bienvenido a Java.",
            "Ana María|Hola, Ana María! Bienvenido a Java."
    }, delimiter = '|')
    @DisplayName("Casos variados de saludo (incluye espacios y tildes)")
    void saludosVariados(String entrada, String esperado) {
        assertEquals(esperado, Ejercicio01_Saludo.saludar(entrada));
    }
}
