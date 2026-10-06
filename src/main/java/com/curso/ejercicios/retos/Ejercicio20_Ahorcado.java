package com.curso.ejercicios.retos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * EJERCICIO 20 (reto) · Juego del ahorcado
 *
 * <h2>Enunciado</h2>
 * Juego de palabras con la lógica separada de la entrada del usuario:
 * <ol>
 *   <li>Elige una palabra al azar del diccionario.</li>
 *   <li>Permite hasta 6 fallos; cada intento revela las letras acertadas.</li>
 *   <li>Acabas en victoria al completar la palabra o en derrota al agotar intentos.</li>
 *   <li>Al terminar, enseña las letras usadas y las que quedaban.</li>
 * </ol>
 *
 * <p>La clase {@link Partida} no lee teclado ni escribe nada: por eso se puede
 * probar línea a línea.</p>
 *
 * <h2>Conceptos</h2>
 * encapsular la lógica, {@link Set} sin repetidos, {@link Random} con semilla
 * para reproducir partidas, bucles de estado.
 */
public final class Ejercicio20_Ahorcado {

    /** Intentos máximos antes de perder. */
    public static final int MAX_INTENTOS = 6;

    private static final List<String> DICCIONARIO = List.of(
            "polimorfismo", "herencia", "encapsulacion", "excepcion",
            "coleccion", "interfaz", "abstracto", "generico", "stream",
            "compilador", "paquete", "registro");

    private Ejercicio20_Ahorcado() {
    }

    /** Estado de una partida, sin I/O. */
    public static final class Partida {

        private final String palabra;
        private final Set<Character> usadas = new LinkedHashSet<>();
        private int fallos;

        public Partida(String palabra) {
            if (palabra == null || palabra.isBlank()) {
                throw new IllegalArgumentException("La palabra no puede estar vacía");
            }
            this.palabra = palabra.toLowerCase(java.util.Locale.ROOT);
        }

        /**
         * Prueba una letra.
         *
         * @return 1 si acierta, 0 si falla (y suma intento), -1 si la letra ya
         *         se había usado antes (no cuenta)
         */
        public int intentar(char letra) {
            char l = Character.toLowerCase(letra);
            if (!Character.isLetter(l)) {
                throw new IllegalArgumentException("Solo se admiten letras: " + letra);
            }
            if (usadas.contains(l)) {
                return -1;
            }
            usadas.add(l);
            if (palabra.indexOf(l) >= 0) {
                return 1;
            }
            fallos++;
            return 0;
        }

        /** Máscara de la palabra: "p _ _ _ _ _ _ _ _ _ _" */
        public String mascara() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < palabra.length(); i++) {
                char c = palabra.charAt(i);
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(usadas.contains(c) ? c : '_');
            }
            return sb.toString();
        }

        /** ¿Han quedado letras sin descubrir? */
        public boolean quedanLetras() {
            for (int i = 0; i < palabra.length(); i++) {
                if (!usadas.contains(palabra.charAt(i))) {
                    return true;
                }
            }
            return false;
        }

        public boolean ganada() {
            return !quedanLetras();
        }

        public boolean perdida() {
            return fallos >= MAX_INTENTOS;
        }

        public boolean terminada() {
            return ganada() || perdida();
        }

        /** Letras que se quedan sin descubrir al perder. */
        public List<Character> letrasPendientes() {
            Set<Character> pendientes = new LinkedHashSet<>();
            for (int i = 0; i < palabra.length(); i++) {
                char c = palabra.charAt(i);
                if (!usadas.contains(c)) {
                    pendientes.add(c);
                }
            }
            return new ArrayList<>(pendientes);
        }

        public int getFallos() {
            return fallos;
        }

        public int intentosRestantes() {
            return Math.max(0, MAX_INTENTOS - fallos);
        }

        public String getPalabra() {
            return palabra;
        }

        public Set<Character> getUsadas() {
            return java.util.Collections.unmodifiableSet(usadas);
        }

        /** Mensaje de final de partida. */
        public String veredicto() {
            if (ganada()) {
                return "¡GANASTE! «" + palabra + "» con " + fallos + " fallo(s)";
            }
            if (perdida()) {
                return "PERDISTE: era «" + palabra + "»";
            }
            return "En curso: " + mascara();
        }
    }

    /** Elige una palabra del diccionario. */
    public static String palabraAleatoria() {
        return palabraAleatoria(new Random());
    }

    /** Versión con Random inyectable: permite partidas reproducibles en tests. */
    public static String palabraAleatoria(Random random) {
        if (DICCIONARIO.isEmpty()) {
            throw new IllegalStateException("Diccionario vacío");
        }
        return DICCIONARIO.get(random.nextInt(DICCIONARIO.size()));
    }

    /** Elige una palabra con semilla fija (para pruebas repetibles). */
    public static String palabraConSemilla(long semilla) {
        return palabraAleatoria(new Random(semilla));
    }

    /** Diccionario disponible (copia inmutable). */
    public static List<String> diccionario() {
        return DICCIONARIO;
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 20 · Ahorcado ───────────────────────");
        String palabra = palabraAleatoria();
        Partida partida = new Partida(palabra);
        System.out.println("  Palabra de " + palabra.length() + " letras. Intentos: " + MAX_INTENTOS);

        // Demo automática: se prueban las letras de la palabra + alguna que no está
        String abecedario = palabra + "xyz";
        for (int i = 0; i < abecedario.length() && !partida.terminada(); i++) {
            int resultado = partida.intentar(abecedario.charAt(i));
            if (resultado >= 0) {
                System.out.printf("  «%c» → %s  (quedan %d intentos)%n",
                        Character.toUpperCase(abecedario.charAt(i)),
                        resultado == 1 ? "¡bien!" : "fallo",
                        partida.intentosRestantes());
            }
        }
        System.out.println("  " + partida.veredicto());
        System.out.println("  Usadas: " + partida.getUsadas());
        if (partida.perdida()) {
            System.out.println("  Letras pendientes: " + partida.letrasPendientes());
        }
    }
}
