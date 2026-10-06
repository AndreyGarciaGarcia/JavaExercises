package com.curso.ejercicios.herencia;

/** Perro: hereda de {@link Animal} e implementa {@link Domesticable}. */
public class Perro extends Animal implements Domesticable {

    private final String raza;

    public Perro(String nombre, String raza) {
        super(nombre);          // DEBE ser la primera sentencia
        this.raza = raza;
    }

    @Override
    public String sonar() {
        return "Guau";
    }

    @Override
    public String truco() {
        return "trae la pelota";
    }

    public String getRaza() {
        return raza;
    }

    /** Método propio que no existe en la clase padre: solo se ve desde un Perro. */
    public String pasear() {
        return nombre + " sale a pasear";
    }
}
