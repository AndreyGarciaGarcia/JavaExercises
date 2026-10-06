package com.curso;

import com.curso.ejercicios.arrays.Ejercicio04_ArraysYStrings;
import com.curso.ejercicios.colecciones.Ejercicio06_ConteoPalabras;
import com.curso.ejercicios.concurrencia.Ejercicio12_Concurrencia;
import com.curso.ejercicios.excepciones.Ejercicio08_Excepciones;
import com.curso.ejercicios.fechas.Ejercicio10_FechasYTiempo;
import com.curso.ejercicios.ficheros.Ejercicio11_Ficheros;
import com.curso.ejercicios.flujo.Ejercicio03_FizzBuzz;
import com.curso.ejercicios.fundamentos.Ejercicio01_Saludo;
import com.curso.ejercicios.fundamentos.Ejercicio02_TiposYConversiones;
import com.curso.ejercicios.poo.Ejercicio05_CuentaBancaria;
import com.curso.ejercicios.retos.Ejercicio09_MaquinaExpendedora;
import com.curso.ejercicios.streams.Ejercicio07_Streams;
import com.curso.util.Teclado;

/**
 * Punto de entrada del proyecto: menú que lanza cada ejercicio.
 *
 * <p>Ejecutar con Maven: {@code mvn compile exec:java}</p>
 * <p>Ejecutar sin Maven: doble clic en {@code run.bat}</p>
 * <p>Ejecutar un ejercicio suelto: botón ▼ de IntelliJ sobre su {@code main}, o bien</p>
 * <pre>java -cp target/classes com.curso.ejercicios.fundamentos.Ejercicio01_Saludo</pre>
 */
public final class App {

    private static final String SEPARADOR = "─".repeat(58);

    private App() {
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println(SEPARADOR);
        System.out.println("  JAVA EXERCISES · menú principal");
        System.out.println(SEPARADOR);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Teclado.entero("Elige una opción: ");

            switch (opcion) {
                case 1 -> ejecutar(() -> Ejercicio01_Saludo.main(new String[0]));
                case 2 -> ejecutar(() -> Ejercicio02_TiposYConversiones.main(new String[0]));
                case 3 -> ejecutar(() -> Ejercicio03_FizzBuzz.main(new String[0]));
                case 4 -> ejecutar(() -> Ejercicio04_ArraysYStrings.main(new String[0]));
                case 5 -> ejecutar(() -> Ejercicio05_CuentaBancaria.main(new String[0]));
                case 6 -> ejecutar(() -> Ejercicio06_ConteoPalabras.main(new String[0]));
                case 7 -> ejecutar(() -> Ejercicio07_Streams.main(new String[0]));
                case 8 -> ejecutar(() -> Ejercicio08_Excepciones.main(new String[0]));
                case 9 -> ejecutar(() -> Ejercicio09_MaquinaExpendedora.main(new String[0]));
                case 10 -> ejecutar(() -> Ejercicio10_FechasYTiempo.main(new String[0]));
                case 11 -> ejecutar(() -> Ejercicio11_Ficheros.main(new String[0]));
                case 12 -> ejecutar(() -> Ejercicio12_Concurrencia.main(new String[0]));
                case 0 -> salir = true;
                default -> System.out.println("\n  ✗ Opción no válida, prueba otra vez.");
            }
        }

        System.out.println("\n¡Hasta pronto! 👋");
        Teclado.cerrar();
    }

    /**
     * Lanza un ejercicio y evita que una excepción (IOException, etc.) tumbe el menú.
     *
     * <p>Los ejercicios con {@code main throws ...} siguen pudiendo invocarse aquí
     * porque la interfaz funcional declara la excepción.</p>
     */
    private static void ejecutar(Ejercicio tarea) {
        try {
            tarea.ejecutar();
        } catch (Exception e) {
            System.out.println("\n  ✗ El ejercicio ha fallado: " + e.getMessage());
        }
    }

    /** Interfaz funcional que permite excepciones comprobadas. */
    @FunctionalInterface
    private interface Ejercicio {
        void ejecutar() throws Exception;
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("  ── BLOQUE 1 · FUNDAMENTOS ─────────────────────────────────");
        System.out.println("  1  · Saludo y primeros pasos");
        System.out.println("  2  · Tipos de datos y conversiones");
        System.out.println("  3  · Control de flujo → FizzBuzz");
        System.out.println("  ── BLOQUE 2 · ESTRUCTURAS Y POO ───────────────────────────");
        System.out.println("  4  · Arrays y String");
        System.out.println("  5  · POO → CuentaBancaria");
        System.out.println("  6  · Colecciones → conteo de palabras");
        System.out.println("  ── BLOQUE 3 · API Y AVANZADO ──────────────────────────────");
        System.out.println("  7  · Lambdas y Streams");
        System.out.println("  8  · Excepciones");
        System.out.println("  9  · RETO → Máquina expendedora");
        System.out.println("  10 · Fecha y hora (java.time)");
        System.out.println("  11 · Ficheros (java.nio)");
        System.out.println("  12 · Concurrencia e hilos virtuales");
        System.out.println("  0  · Salir");
        System.out.println(SEPARADOR);
    }
}
