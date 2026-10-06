package com.curso.ejercicios.herencia;

import java.util.List;

/**
 * EJERCICIO 13 · Herencia, polimorfismo e interfaces
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Crea un perro y un gato y muestra cómo <strong>cada uno suena distinto</strong>
 *       aunque el método se llame igual (polimorfismo).</li>
 *   <li>Comprueba que desde una referencia {@code Animal} <strong>no</strong> se ve
 *       {@code pasear()} (el compilador mira el tipo de la referencia).</li>
 *   <li>Descríbelos con <em>pattern matching</em> de {@code instanceof}.</li>
 *   <li>Recorre la lista llamando a {@code truco()} solo a los domesticables.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code extends}, {@code implements}, {@code abstract}, {@code super},
 * sobrescritura, polimorfismo, {@code instanceof Tipo t}.
 */
public final class Ejercicio13_JerarquiaAnimales {

    private Ejercicio13_JerarquiaAnimales() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 13 · Herencia y polimorfismo ────────");

        Animal rex = new Perro("Rex", "Pastor alemán");
        Animal mishi = new Gato("Mishi");

        // 1) Polimorfismo: mismo mensaje, comportamiento distinto
        List<Animal> zoo = List.of(rex, mishi);
        for (Animal a : zoo) {
            System.out.println("  " + a.getNombre() + " → " + a.sonar());
        }

        // 2) Método herado y método sobrescrito
        System.out.println("  " + rex.comer());
        System.out.println("  " + mishi.comer());

        // 3) Desde Animal NO se ve pasear(); hay que comprobar el tipo
        for (Animal a : zoo) {
            System.out.println("  " + describir(a));
        }

        // 4) Interfaces: solo los domesticables tienen truco
        List<Domesticable> mascotas = List.of((Perro) rex, (Gato) mishi);
        mascotas.forEach(m -> System.out.println("  " + m.presentarse()));

        // 5) getDeclaredMethods / getClass
        System.out.println("  Clases de la lista: " + zoo.stream()
                .map(o -> o.getClass().getSimpleName()).toList());
    }

    /** Describe al animal con pattern matching (Java 16+). */
    public static String describir(Animal animal) {
        if (animal == null) {
            return "nada";
        }
        return switch (animal) {
            case Perro p -> "Perro " + p.getNombre() + " (" + p.getRaza() + "), suena: " + p.sonar();
            case Gato g -> "Gato " + g.getNombre() + ", suena: " + g.sonar();
            default -> "Animal " + animal.getNombre();
        };
    }

    /** Devuelve los sonidos de la lista: demuestra el despacho dinámico. */
    public static List<String> sonidos(List<? extends Animal> animales) {
        return animales.stream().map(Animal::sonar).toList();
    }

    /** Los trucos de todos los domesticables (wildcard: cualquier subclase). */
    public static List<String> trucos(List<? extends Domesticable> mascotas) {
        return mascotas.stream().map(Domesticable::truco).toList();
    }

    /** Cuenta cuántos animales de la lista son de la clase indicada. */
    public static long contarDeTipo(List<? extends Animal> animales, Class<?> tipo) {
        return animales.stream().filter(tipo::isInstance).count();
    }
}
