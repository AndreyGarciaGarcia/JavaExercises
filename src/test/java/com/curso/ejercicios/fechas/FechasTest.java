package com.curso.ejercicios.fechas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 10 · Fecha y hora")
class FechasTest {

    @Test
    @DisplayName("Edad cumplida entre dos fechas")
    void edadEnAnios() {
        assertEquals(25, Ejercicio10_FechasYTiempo.edadEnAnios(LocalDate.of(2000, 1, 1), LocalDate.of(2025, 1, 1)));
        // Aún no ha cumplido años
        assertEquals(24, Ejercicio10_FechasYTiempo.edadEnAnios(LocalDate.of(2000, 6, 15), LocalDate.of(2025, 6, 14)));
    }

    @Test
    @DisplayName("Nacimiento posterior a la referencia → error")
    void edadInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
                Ejercicio10_FechasYTiempo.edadEnAnios(LocalDate.of(2030, 1, 1), LocalDate.of(2025, 1, 1)));
    }

    @Test
    @DisplayName("Días entre dos fechas")
    void diasEntre() {
        assertEquals(0, Ejercicio10_FechasYTiempo.diasEntre(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1)));
        assertEquals(365, Ejercicio10_FechasYTiempo.diasEntre(LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1)));
        assertEquals(-10, Ejercicio10_FechasYTiempo.diasEntre(LocalDate.of(2025, 1, 11), LocalDate.of(2025, 1, 1)));
    }

    @Test
    @DisplayName("Años bisiestos según la regla oficial")
    void esBisiesto() {
        assertTrue(Ejercicio10_FechasYTiempo.esBisiesto(2024));
        assertTrue(Ejercicio10_FechasYTiempo.esBisiesto(2000));   // múltiplo de 400
        assertFalse(Ejercicio10_FechasYTiempo.esBisiesto(2023));
        assertFalse(Ejercicio10_FechasYTiempo.esBisiesto(1900));  // siglo no múltiplo de 400
    }

    @Test
    @DisplayName("El próximo lunes es realmente lunes")
    void proximoLunes() {
        LocalDate lunes = Ejercicio10_FechasYTiempo.proximoLunes(LocalDate.of(2025, 1, 6)); // lunes
        assertEquals(java.time.DayOfWeek.MONDAY, lunes.getDayOfWeek());
        assertEquals(LocalDate.of(2025, 1, 13), lunes);

        LocalDate domingo = Ejercicio10_FechasYTiempo.proximoLunes(LocalDate.of(2025, 1, 12));
        assertEquals(LocalDate.of(2025, 1, 13), domingo);
    }

    @Test
    @DisplayName("Formato legible de la fecha")
    void fechaLegible() {
        assertEquals("15 de junio de 2000",
                Ejercicio10_FechasYTiempo.fechaLegible(LocalDate.of(2000, 6, 15)));
    }

    @Test
    @DisplayName("No depende del locale de la JVM (regresión: en inglés salía «June»)")
    void fechaLegibleIndependienteDelLocale() {
        java.util.Locale original = java.util.Locale.getDefault();
        try {
            java.util.Locale.setDefault(java.util.Locale.of("en", "US"));
            assertEquals("15 de junio de 2000",
                    Ejercicio10_FechasYTiempo.fechaLegible(LocalDate.of(2000, 6, 15)));
            assertEquals("1 de enero de 2025",
                    Ejercicio10_FechasYTiempo.fechaLegible(LocalDate.of(2025, 1, 1)));
        } finally {
            java.util.Locale.setDefault(original);   // siempre se restaura
        }
    }
}
