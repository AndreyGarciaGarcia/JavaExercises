package com.curso.ejercicios.examenes;

import com.curso.ejercicios.examenes.Ejercicio18_Cuestionario.Pregunta;
import com.curso.ejercicios.examenes.Ejercicio18_Cuestionario.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 18 · Cuestionario autocorregible")
class CuestionarioTest {

    private final List<Pregunta> banco = Ejercicio18_Cuestionario.banco();

    @Test
    @DisplayName("El banco tiene 10 preguntas bien formadas")
    void banco() {
        assertEquals(10, banco.size());
        banco.forEach(p -> {
            assertTrue(p.opciones().size() >= 2, "mínimo 2 opciones");
            assertTrue(p.correcta() >= 0 && p.correcta() < p.opciones().size());
            assertFalse(p.enunciado().isBlank());
            assertFalse(p.explicacion().isBlank());
        });
        assertEquals(Ejercicio18_Cuestionario.primeras(3).size(), 3);
        assertEquals(Ejercicio18_Cuestionario.primeras(3).get(0), banco.get(0));
    }

    @Test
    @DisplayName("Todo correcto: 10/10, nota 10, aprobado")
    void todoCorrecto() {
        Resultado r = Ejercicio18_Cuestionario.corregir(banco,
                Ejercicio18_Cuestionario.todasCorrectas(banco));

        assertEquals(10, r.aciertos());
        assertEquals(0, r.totalFallos());
        assertEquals(100.0, r.porcentaje(), 0.001);
        assertEquals(10.0, r.nota(), 0.001);
        assertTrue(r.aprobado());
        assertTrue(r.fallos().isEmpty());
        assertTrue(r.resumen().contains("APROBADO"));
        assertTrue(r.resumen().contains("10/10"));
    }

    @Test
    @DisplayName("Todo mal: 0/10, nota 0, suspenso, y falla todas")
    void todoMal() {
        Resultado r = Ejercicio18_Cuestionario.corregir(banco,
                Ejercicio18_Cuestionario.todasIncorrectas(banco));

        assertEquals(0, r.aciertos());
        assertEquals(10, r.totalFallos());
        assertEquals(0.0, r.porcentaje(), 0.001);
        assertEquals(0.0, r.nota(), 0.001);
        assertFalse(r.aprobado());
        assertEquals(10, r.fallos().size());
        assertTrue(r.resumen().contains("SUSPENSO"));
    }

    @Test
    @DisplayName("Mezcla de aciertos y fallos: 7/10 → 7,0 de nota")
    void mezcla() {
        List<Integer> respuestas = List.of(0, 3, 1, 0, 2, 0, 3, 3, 2, 0);
        Resultado r = Ejercicio18_Cuestionario.corregir(banco, respuestas);

        assertEquals(7, r.aciertos());
        assertEquals(3, r.totalFallos());
        assertEquals(70.0, r.porcentaje(), 0.001);
        assertEquals(7.0, r.nota(), 0.001);
        assertTrue(r.aprobado());

        // Los índices de los fallos apuntan a las preguntas equivocadas
        assertEquals(List.of(1, 5, 7), r.fallos());
        assertEquals(3, Ejercicio18_Cuestionario.falladas(banco, r).size());
        assertEquals(banco.get(1), Ejercicio18_Cuestionario.falladas(banco, r).get(0));
    }

    @Test
    @DisplayName("El umbral de aprobado es exactamente 5,0")
    void limiteDeAprobado() {
        // Umbral: 5/10 = 5,0 → aprobado; 4/10 = 4,0 → suspenso
        Resultado justo = new Resultado(5, 10, List.of(0, 1, 2, 3, 4));
        assertEquals(5.0, justo.nota(), 0.001);
        assertTrue(justo.aprobado());

        Resultado suspendido = new Resultado(4, 10, List.of(0, 1, 2, 3, 4, 5));
        assertEquals(4.0, suspendido.nota(), 0.001);
        assertEquals(6, suspendido.totalFallos());
        assertFalse(suspendido.aprobado());

        // Solo una pregunta mal → 9/10
        List<Integer> unaSoloFallada = new java.util.ArrayList<>(
                Ejercicio18_Cuestionario.todasCorrectas(banco));
        unaSoloFallada.set(0, (banco.get(0).correcta() + 1) % banco.get(0).opciones().size());
        Resultado casiPerfecto = Ejercicio18_Cuestionario.corregir(banco, unaSoloFallada);

        assertEquals(9, casiPerfecto.aciertos());
        assertEquals(List.of(0), casiPerfecto.fallos());
        assertTrue(casiPerfecto.aprobado());
    }

    @Test
    @DisplayName("Las respuestas incompletas o fuera de rango se rechazan")
    void validarRespuestas() {
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio18_Cuestionario.corregir(banco, List.of(0)));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio18_Cuestionario.corregir(banco, null));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio18_Cuestionario.corregir(List.of(), List.of()));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio18_Cuestionario.corregir(banco,
                        List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 99)));
    }

    @Test
    @DisplayName("Una pregunta mal construida revienta en el constructor")
    void validarPregunta() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta("", List.of("a", "b"), 0, "exp"));
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta("Enunciado", List.of("única"), 0, "exp"));
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta("Enunciado", List.of("a", "b"), 5, "exp"));
        assertThrows(IllegalArgumentException.class,
                () -> new Pregunta("Enunciado", List.of("a", "b"), -1, "exp"));
    }

    @Test
    @DisplayName("esCorrecta()/letraCorrecta() funcionan como esperas")
    void detallesDePregunta() {
        Pregunta p = banco.get(0);
        assertTrue(p.esCorrecta(p.correcta()));
        assertFalse(p.esCorrecta((p.correcta() + 1) % p.opciones().size()));
        assertEquals('A', p.letraCorrecta());
        assertEquals('D', new Pregunta("x", List.of("a", "b", "c", "d"), 3, "e").letraCorrecta());
        // Las opciones son inmutables: no se pueden tocar desde fuera
        assertThrows(UnsupportedOperationException.class,
                () -> p.opciones().add("inventada"));
    }
}
