package com.curso.ejercicios.herencia;

/** Gato: otra rama de la jerarquía. */
public class Gato extends Animal implements Domesticable {

    public Gato(String nombre) {
        super(nombre);
    }

    @Override
    public String sonar() {
        return "Miau";
    }

    @Override
    public String truco() {
        return "da la pata";
    }

    /** Sobrescribe el método del padre: el polimorfismo cambia el comportamiento. */
    @Override
    public String comer() {
        return nombre + " come muy despacio";
    }
}
