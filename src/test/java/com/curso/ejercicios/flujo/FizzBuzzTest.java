package com.curso.ejercicios.flujo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Ejercicio 03 · FizzBuzz")
class FizzBuzzTest {

    @ParameterizedTest(name = "{0} → {1}")
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, Fizz",
            "5, Buzz",
            "6, Fizz",
            "7, 7",
            "9, Fizz",
            "10, Buzz",
            "15, FizzBuzz",
            "30, FizzBuzz",
            "45, FizzBuzz",
            "98, 98",
            "99, Fizz",
            "100, Buzz"
    })
    @DisplayName("Reglas del juego")
    void reglas(int numero, String esperado) {
        assertEquals(esperado, Ejercicio03_FizzBuzz.resolver(numero));
    }

    @Test
    @DisplayName("Las dos implementaciones dan el mismo resultado (1..100)")
    void implementacionesEquivalentes() {
        for (int n = 1; n <= 100; n++) {
            assertEquals(Ejercicio03_FizzBuzz.resolver(n),
                    Ejercicio03_FizzBuzz.resolverConSwitch(n),
                    "Diferencia en el número " + n);
        }
    }

    @Test
    @DisplayName("De 1 a 100: 27 solo Fizz, 14 solo Buzz y 6 FizzBuzz")
    void estadisticas() {
        int fizz = 0;
        int buzz = 0;
        int fizzBuzz = 0;
        int numeros = 0;   // los que no son múltiplos ni de 3 ni de 5
        for (int n = 1; n <= 100; n++) {
            switch (Ejercicio03_FizzBuzz.resolver(n)) {
                case "FizzBuzz" -> fizzBuzz++;
                case "Fizz" -> fizz++;
                case "Buzz" -> buzz++;
                default -> numeros++;
            }
        }
        assertEquals(27, fizz);      // 33 múltiplos de 3 − 6 de 15
        assertEquals(14, buzz);      // 20 múltiplos de 5 − 6 de 15
        assertEquals(6, fizzBuzz);   // múltiplos de 15
        assertEquals(53, numeros);   // 100 − 47
        assertEquals(100, fizz + buzz + fizzBuzz + numeros);   // todos cubiertos
    }
}
