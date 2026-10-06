package com.curso.ejercicios.enums;

import java.util.List;

/**
 * EJERCICIO 14 · Enums, records y tipos sellados
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Muestra los días laborables y el día siguiente a viernes (sale sábado)
 *       y el de después del domingo (vuelve al lunes).</li>
 *   <li>Crea puntos en 2D y calcula la distancia entre ellos con un {@code record}.</li>
 *   <li>Suma las áreas de una lista de formas con {@code switch} sobre tipos sellados.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code enum} con atributos y métodos, {@code record} con validación,
 * {@code sealed}/{@code permits}, pattern matching en {@code switch}.
 */
public final class Ejercicio14_EnumsYRecords {

    /** Punto inmutable de 2D con validación. */
    public record Punto(double x, double y) {

        public Punto {
            if (Double.isNaN(x) || Double.isNaN(y)) {
                throw new IllegalArgumentException("Coordenada no válida");
            }
        }

        /** Distancia euclídea hasta otro punto. */
        public double distanciaA(Punto otro) {
            return Math.sqrt(Math.pow(x - otro.x, 2) + Math.pow(y - otro.y, 2));
        }

        /** Desplaza el punto y devuelve uno nuevo (no muta el original). */
        public Punto desplazar(double dx, double dy) {
            return new Punto(x + dx, y + dy);
        }
    }

    private Ejercicio14_EnumsYRecords() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 14 · Enums, records y sealed ────────");

        // Enums
        System.out.println("  Laborables      : " + DiaSemana.laborables());
        System.out.println("  Tras VIERNES    : " + DiaSemana.VIERNES.siguiente());
        System.out.println("  Nº 3 =          : " + DiaSemana.deNumero(3));
        System.out.println("  ¿Lunes laborable? " + DiaSemana.LUNES.esLaborable());

        // Records
        Punto a = new Punto(0, 0);
        Punto b = new Punto(3, 4);
        System.out.printf("  Distancia A→B   : %.2f%n", a.distanciaA(b));
        System.out.println("  A desplazado    : " + a.desplazar(1, 1) + " (A sigue en " + a + ")");

        // Records que implementan una interfaz sellada
        List<Forma> formas = List.of(
                new Forma.Circulo(1.5),
                new Forma.Rectangulo(4, 2),
                new Forma.Circulo(3));

        System.out.printf("  Area total      : %.2f%n", Forma.areaTotal(formas));

        // switch sobre el tipo exacto (exhaustivo: no necesita default)
        for (Forma f : formas) {
            String descripcion = switch (f) {
                case Forma.Circulo c -> "Círculo r=" + c.radio();
                case Forma.Rectangulo r -> "Rectángulo " + r.ancho() + "x" + r.alto();
            };
            System.out.printf("  %-24s → area %.2f%n", descripcion, f.area());
        }

        // equals/hashCode de gratis
        System.out.println("  ¿Mismo punto?   : " + new Punto(1, 2).equals(new Punto(1, 2)));
    }

    /** Devuelve la inicial de cada día: L, M, M, J, V, S, D. */
    public static String iniciales() {
        return java.util.stream.Stream.of(DiaSemana.values())
                .map(d -> String.valueOf(d.getNombreLargo().charAt(0)))
                .reduce("", (a, b) -> a + b);
    }

    /** Distancia entre dos listas de puntos emparejadas (mismo tamaño). */
    public static List<Double> distancias(List<Punto> origen, List<Punto> destino) {
        if (origen.size() != destino.size()) {
            throw new IllegalArgumentException("Las listas deben tener el mismo tamaño");
        }
        java.util.List<Double> resultado = new java.util.ArrayList<>();
        for (int i = 0; i < origen.size(); i++) {
            resultado.add(origen.get(i).distanciaA(destino.get(i)));
        }
        return resultado;
    }
}
