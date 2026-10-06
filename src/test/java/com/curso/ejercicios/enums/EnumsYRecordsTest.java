package com.curso.ejercicios.enums;

import com.curso.ejercicios.enums.Ejercicio14_EnumsYRecords.Punto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 14 · Enums, records y sealed")
class EnumsYRecordsTest {

    @Test
    @DisplayName("Los laborables son exactamente lunes a viernes")
    void laborables() {
        assertEquals(5, DiaSemana.laborables().size());
        assertTrue(DiaSemana.LUNES.esLaborable());
        assertTrue(DiaSemana.VIERNES.esLaborable());
        assertFalse(DiaSemana.SABADO.esLaborable());
        assertFalse(DiaSemana.DOMINGO.esLaborable());
    }

    @ParameterizedTest(name = "{0} → siguiente {1}")
    @org.junit.jupiter.params.provider.CsvSource({
            "LUNES, MARTES",
            "VIERNES, SABADO",
            "DOMINGO, LUNES"          // da la vuelta a la semana
    })
    @DisplayName("siguiente() recorre la semana con vuelta atrás")
    void siguiente(DiaSemana actual, DiaSemana esperado) {
        assertEquals(esperado, actual.siguiente());
    }

    @Test
    @DisplayName("deNumero(): 1 = lunes … 7 = domingo, fuera de rango → error")
    void deNumero() {
        assertEquals(DiaSemana.LUNES, DiaSemana.deNumero(1));
        assertEquals(DiaSemana.MIERCOLES, DiaSemana.deNumero(3));
        assertEquals(DiaSemana.DOMINGO, DiaSemana.deNumero(7));
        assertThrows(IllegalArgumentException.class, () -> DiaSemana.deNumero(0));
        assertThrows(IllegalArgumentException.class, () -> DiaSemana.deNumero(8));
    }

    @ParameterizedTest(name = "{0} entre LUNES y VIERNES → {1}")
    @EnumSource(DiaSemana.class)
    @DisplayName("estaEntre() respeta la posición ordinal")
    void estaEntre(DiaSemana dia) {
        boolean esperado = dia.ordinal() >= DiaSemana.LUNES.ordinal()
                && dia.ordinal() <= DiaSemana.VIERNES.ordinal();
        assertEquals(esperado, dia.estaEntre(DiaSemana.LUNES, DiaSemana.VIERNES));
    }

    @Test
    @DisplayName("Los enums no se comparan con equals sino con ==")
    void identidadDeEnums() {
        assertEquals(DiaSemana.LUNES, DiaSemana.deNumero(1));
        assertTrue(DiaSemana.LUNES == DiaSemana.values()[0]);
        assertEquals("Miércoles", DiaSemana.MIERCOLES.toString());
        assertEquals("LMMJVSD", Ejercicio14_EnumsYRecords.iniciales());
    }

    @Test
    @DisplayName("El record es inmutable y calcula bien la distancia")
    void recordsPunto() {
        Punto a = new Punto(0, 0);
        Punto b = new Punto(3, 4);
        assertEquals(5.0, a.distanciaA(b), 0.0001);
        assertEquals(new Punto(1, 1), a.desplazar(1, 1));
        assertEquals(new Punto(0, 0), a);                    // no se ha mutado
        assertNotEquals(a, b);
        assertEquals(new Punto(1, 2), new Punto(1, 2));      // equals gratis
        assertThrows(IllegalArgumentException.class, () -> new Punto(Double.NaN, 0));
    }

    @Test
    @DisplayName("Interfaces selladas: todas las variantes son conocidas")
    void interfacesSelladas() {
        List<Forma> formas = List.of(new Forma.Circulo(1.0), new Forma.Rectangulo(2, 3));
        assertEquals(Math.PI, new Forma.Circulo(1.0).area(), 0.0001);
        assertEquals(6.0, new Forma.Rectangulo(2, 3).area(), 0.0001);
        assertEquals(Math.PI + 6.0, Forma.areaTotal(formas), 0.0001);

        // El switch exhaustivo no necesita 'default'
        String descripcion = switch (formas.get(1)) {
            case Forma.Circulo c -> "circulo";
            case Forma.Rectangulo r -> "rectangulo " + r.ancho() + "x" + r.alto();
        };
        assertEquals("rectangulo 2.0x3.0", descripcion);

        assertThrows(IllegalArgumentException.class, () -> new Forma.Circulo(0));
        assertThrows(IllegalArgumentException.class, () -> new Forma.Rectangulo(-1, 1));
    }

    @Test
    @DisplayName("distancias() empareja las listas y valida tamaños")
    void distancias() {
        List<Double> medidas = Ejercicio14_EnumsYRecords.distancias(
                List.of(new Punto(0, 0), new Punto(1, 1)),
                List.of(new Punto(3, 4), new Punto(1, 1)));
        assertEquals(2, medidas.size());
        assertEquals(5.0, medidas.get(0), 0.0001);
        assertEquals(0.0, medidas.get(1), 0.0001);

        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio14_EnumsYRecords.distancias(List.of(new Punto(0, 0)), List.of()));
    }
}
