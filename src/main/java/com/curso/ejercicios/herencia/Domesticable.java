package com.curso.ejercicios.herencia;

/**
 * Interfaz de capacidad: algo que <em>puede</em> ser domesticado.
 *
 * <p>Las clases pueden <strong>heredar de una clase</strong> y <strong>implementar
 * varias interfaces</strong> a la vez (herencia múltiple de contratos).</p>
 */
public interface Domesticable {

    /** Truco que sabe hacer el animal doméstico. */
    String truco();

    /** Método con cuerpo: lo heredan todas las implementaciones sin obligar a nada. */
    default String presentarse() {
        return "Soy domesticable y " + truco();
    }
}
