package com.curso.ejercicios.colecciones.practicas;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Repaso2 · mayores de 5")
class Repaso2Test {

    @Test
    @DisplayName("[4,7] -> [7]")
    void filtra() {
        ArrayList<Integer> entrada = new ArrayList<>(Arrays.asList(4, 7));
        ArrayList<Integer> esperado = new ArrayList<>(Arrays.asList(7));
        assertEquals(esperado, Repaso2.mayoresDe5(entrada));
    }
}
