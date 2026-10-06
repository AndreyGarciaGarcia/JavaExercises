package com.curso.ejercicios.genericos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * EJERCICIO 15 · Genéricos y PECS
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Caja tipada: comprueba en tiempo de compilación que no metes un Integer
 *       dentro de una {@code Caja<String>}.</li>
 *   <li>Encuentra el máximo de una lista sin comparar tipos distintos.</li>
 *   <li>Copia números de una lista a otra con límites superiores e inferiores.</li>
 *   <li>Agrupa pares clave-valor respetando el orden de inserción.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code <T>}, límite superior {@code <T extends Comparable<T>>}, PECS
 * ({@code Producer Extends, Consumer Super}), type erasure, clases genéricas
 * anidadas.
 */
public final class Ejercicio15_Genericos {

    private Ejercicio15_Genericos() {
    }

    /**
     * Contenedor de un solo elemento ya tipado.
     *
     * @param <T> tipo de lo que guarda
     */
    public static class Caja<T> {

        private T contenido;

        public void poner(T elemento) {
            this.contenido = elemento;
        }

        public T sacar() {
            return contenido;
        }

        public boolean estaVacia() {
            return contenido == null;
        }

        @Override
        public String toString() {
            return "Caja[" + contenido + "]";
        }
    }

    /** Par inmutable de clave-valor (como una mini entrada de mapa). */
    public record Par<K, V>(K clave, V valor) {

        public Par {
            if (clave == null) {
                throw new IllegalArgumentException("La clave no puede ser null");
            }
        }
    }

    /**
     * El mayor de la lista.
     *
     * <p>El límite {@code <T extends Comparable<T>>} es lo que permite llamar a
     * {@code compareTo}: sin él, el compilador no sabría que T es comparable.</p>
     */
    public static <T extends Comparable<T>> T maximo(List<T> elementos) {
        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        T mejor = elementos.get(0);
        for (int i = 1; i < elementos.size(); i++) {
            T actual = elementos.get(i);
            if (actual.compareTo(mejor) > 0) {
                mejor = actual;
            }
        }
        return mejor;
    }

    /**
     * Mínimo de la lista: misma idea, en sentido contrario.
     */
    public static <T extends Comparable<T>> T minimo(List<T> elementos) {
        if (elementos == null || elementos.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        T mejor = elementos.get(0);
        for (T actual : elementos) {
            if (actual.compareTo(mejor) < 0) {
                mejor = actual;
            }
        }
        return mejor;
    }

    /**
     * PECS en acción:
     * <ul>
     *   <li>{@code ? extends Integer} → la fuente es un <strong>productor</strong>
     *       de números: podemos leer de ella.</li>
     *   <li>{@code ? super Integer} → el destino es un <strong>consumidor</strong>
     *       de números: podemos meterle Integer.</li>
     * </ul>
     */
    public static void copiarNumeros(List<? extends Integer> origen, List<? super Integer> destino) {
        destino.addAll(origen);
    }

    /** Agrupa pares por clave conservando el orden de inserción. */
    public static <K, V> Map<K, V> aMapa(List<Par<K, V>> pares) {
        Map<K, V> mapa = new LinkedHashMap<>();
        if (pares != null) {
            pares.forEach(p -> mapa.put(p.clave(), p.valor()));
        }
        return mapa;
    }

    /** Intercambia dos posiciones sin importar el tipo almacenado. */
    public static <T> void intercambiar(List<T> lista, int i, int j) {
        if (i < 0 || j < 0 || i >= lista.size() || j >= lista.size()) {
            throw new IndexOutOfBoundsException("Índices fuera de rango: " + i + ", " + j);
        }
        T temporal = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temporal);
    }

    /** Duplica cada elemento, demostrando que T sigue siendo T al volver. */
    public static <T> List<T> repetir(T elemento, int veces) {
        if (veces < 0) {
            throw new IllegalArgumentException("veces no puede ser negativo: " + veces);
        }
        List<T> resultado = new ArrayList<>();
        for (int i = 0; i < veces; i++) {
            resultado.add(elemento);
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 15 · Genéricos y PECS ───────────────");

        Caja<String> texto = new Caja<>();
        texto.poner("hola");
        System.out.println("  Caja<String> → " + texto.sacar());

        Caja<Integer> numero = new Caja<>();
        numero.poner(42);
        System.out.println("  Caja<Integer> → " + numero.sacar());

        List<Integer> nums = List.of(3, 9, 4, 7, 1);
        System.out.println("  Máximo de " + nums + " → " + maximo(nums));
        System.out.println("  Mínimo de " + nums + " → " + minimo(nums));

        List<Integer> origen = List.of(1, 2, 3);
        List<Integer> destino = new ArrayList<>();
        copiarNumeros(origen, destino);
        System.out.println("  Copia PECS → " + destino);

        List<Par<String, Integer>> pares = List.of(new Par<>("rosa", 1), new Par<>("verde", 2));
        System.out.println("  Mapa de pares → " + aMapa(pares));

        List<String> lista = new ArrayList<>(List.of("a", "b", "c"));
        intercambiar(lista, 0, 2);
        System.out.println("  Tras intercambiar(0,2) → " + lista);
        System.out.println("  repetir(\"java\", 3) → " + repetir("java", 3));
    }
}
