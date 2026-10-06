package com.curso.ejercicios.herencia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 13 · Jerarquía de animales")
class JerarquiaAnimalesTest {

    private final Perro rex = new Perro("Rex", "Pastor alemán");
    private final Gato mishi = new Gato("Mishi");

    @Test
    @DisplayName("Polimorfismo: mismo método, sonido distinto")
    void sonidosDistintos() {
        assertEquals("Guau", rex.sonar());
        assertEquals("Miau", mishi.sonar());
    }

    @Test
    @DisplayName("Desde una referencia Animal se ejecuta el método de la subclase")
    void despachoDinamico() {
        Animal animal = new Perro("Toby", "Beagle");
        assertEquals("Guau", animal.sonar());        // el tipo real manda

        List<Animal> zoo = List.of(rex, mishi);
        assertEquals(List.of("Guau", "Miau"), Ejercicio13_JerarquiaAnimales.sonidos(zoo));
    }

    @Test
    @DisplayName("Sobrescritura: el gato no come igual que su padre")
    void sobrescritura() {
        assertEquals("Rex está comiendo", rex.comer());            // heredado
        assertEquals("Mishi come muy despacio", mishi.comer());    // sobrescrito
    }

    @Test
    @DisplayName("Pattern matching describe cada subtipo")
    void describir() {
        assertEquals("Perro Rex (Pastor alemán), suena: Guau",
                Ejercicio13_JerarquiaAnimales.describir(rex));
        assertEquals("Gato Mishi, suena: Miau",
                Ejercicio13_JerarquiaAnimales.describir(mishi));
        assertEquals("nada", Ejercicio13_JerarquiaAnimales.describir(null));
    }

    @Test
    @DisplayName("Interfaces: trucos solo de los domesticables")
    void interfaces() {
        List<Domesticable> mascotas = List.of(rex, mishi);
        assertEquals(List.of("trae la pelota", "da la pata"),
                Ejercicio13_JerarquiaAnimales.trucos(mascotas));
        assertTrue(rex.presentarse().startsWith("Soy domesticable"));
    }

    @Test
    @DisplayName("contarDeTipo cuenta por clase exacta")
    void contarDeTipo() {
        List<Animal> zoo = List.of(rex, mishi, new Gato("Felix"), new Perro("Luna", "Caniche"));
        assertEquals(2, Ejercicio13_JerarquiaAnimales.contarDeTipo(zoo, Perro.class));
        assertEquals(2, Ejercicio13_JerarquiaAnimales.contarDeTipo(zoo, Gato.class));
        assertEquals(4, Ejercicio13_JerarquiaAnimales.contarDeTipo(zoo, Animal.class));
        assertEquals(0, Ejercicio13_JerarquiaAnimales.contarDeTipo(zoo, String.class));
    }

    @Test
    @DisplayName("Un Animal sin nombre no puede existir")
    void validarNombre() {
        assertThrows(IllegalArgumentException.class, () -> new Gato("  "));
        assertThrows(IllegalArgumentException.class, () -> new Perro(null, "x"));
        assertEquals("Rex", rex.getNombre());
        assertEquals("Perro", rex.getClass().getSimpleName());
    }
}
