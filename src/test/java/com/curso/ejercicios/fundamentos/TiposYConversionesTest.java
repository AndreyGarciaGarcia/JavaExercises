package com.curso.ejercicios.fundamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 02 · Tipos y conversiones")
class TiposYConversionesTest {

    @ParameterizedTest(name = "{0} °C → {1} °F")
    @CsvSource({"0, 32", "100, 212", "-40, -40", "37, 98.6"})
    @DisplayName("Celsius → Fahrenheit")
    void celsiusAFahrenheit(double celsius, double fahrenheit) {
        assertEquals(fahrenheit, Ejercicio02_TiposYConversiones.celsiusAFahrenheit(celsius), 0.0001);
    }

    @ParameterizedTest(name = "{0} °F → {1} °C")
    @CsvSource({"32, 0", "212, 100", "-40, -40"})
    @DisplayName("Fahrenheit → Celsius")
    void fahrenheitACelsius(double fahrenheit, double celsius) {
        assertEquals(celsius, Ejercicio02_TiposYConversiones.fahrenheitACelsius(fahrenheit), 0.0001);
    }

    @Test
    @DisplayName("La conversión es inversible (idA → idB → idA)")
    void conversionInversible() {
        double original = 21.7;
        double ida = Ejercicio02_TiposYConversiones.celsiusAFahrenheit(original);
        double vuelta = Ejercicio02_TiposYConversiones.fahrenheitACelsius(ida);
        assertEquals(original, vuelta, 0.000001);
    }

    @Test
    @DisplayName("Años a días usa long para no desbordar")
    void aniosADias() {
        assertEquals(365L, Ejercicio02_TiposYConversiones.aniosADias(1));
        assertEquals(36500L, Ejercicio02_TiposYConversiones.aniosADias(100));
        assertEquals(3_650_000L, Ejercicio02_TiposYConversiones.aniosADias(10_000));
        assertTrue(Ejercicio02_TiposYConversiones.aniosADias(10_000_000) > 0);
    }

    @Test
    @DisplayName("Truncación del casting: (int) 3.99 == 3")
    void castingTrunca() {
        assertEquals(3, (int) 3.99);
        assertEquals(3, (int) 3.000001);
        assertEquals(-3, (int) -3.99);   // hacia cero, no hacia -infinito
    }
}
