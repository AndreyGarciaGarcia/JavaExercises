package com.curso.ejercicios.enums;

/**
 * Interfaz <strong>sellada</strong> (Java 17): solo puede ser implementada por
 * los tipos que aparecen en {@code permits}, y todos están en este fichero.
 *
 * <p>Esto permite al compilador saber el conjunto completo de variantes y pedirte
 * un {@code default} o el {@code switch} exhaustivo.</p>
 */
public sealed interface Forma permits Forma.Circulo, Forma.Rectangulo {

    double area();

    /** Circulo es un record: inmutable, con equals/hashCode/toString gratis. */
    record Circulo(double radio) implements Forma {

        public Circulo {
            if (radio <= 0) {
                throw new IllegalArgumentException("El radio debe ser positivo");
            }
        }

        @Override
        public double area() {
            return Math.PI * radio * radio;
        }
    }

    /** Rectangulo con validación en el compact constructor. */
    record Rectangulo(double ancho, double alto) implements Forma {

        public Rectangulo {
            if (ancho <= 0 || alto <= 0) {
                throw new IllegalArgumentException("Las dimensiones deben ser positivas");
            }
        }

        @Override
        public double area() {
            return ancho * alto;
        }
    }

    /** Suma las áreas de una lista de formas: sin instanceof por ningún lado. */
    static double areaTotal(java.util.List<Forma> formas) {
        return formas.stream().mapToDouble(Forma::area).sum();
    }
}
