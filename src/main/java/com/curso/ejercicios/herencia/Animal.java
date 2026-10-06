package com.curso.ejercicios.herencia;

/**
 * Abstracción de todo ser vivo del ejercicio 13.
 *
 * <p>Clase <strong>abstracta</strong>: define el contrato común (nombre + sonar)
 * pero obliga a cada subclase a dar su propia versión de {@link #sonar()}.</p>
 */
public abstract class Animal {

    protected final String nombre;      // protected: accesible para las subclases

    protected Animal(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        this.nombre = nombre;
    }

    /** Método abstracto: cada animal suena a su manera. */
    public abstract String sonar();

    /** Método concreto: se hereda tal cual (pero puede sobrescribirse). */
    public String comer() {
        return nombre + " está comiendo";
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + nombre + ")";
    }
}
