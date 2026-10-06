package com.curso.ejercicios.examenes;

import java.util.ArrayList;
import java.util.List;

/**
 * EJERCICIO 18 · Cuestionario autocorregible
 *
 * <h2>Enunciado</h2>
 * Implementa un test de Java tipo examen:
 * <ol>
 *   <li>Banco de preguntas con opciones y respuesta correcta.</li>
 *   <li>Pasa el cuestionario, comprueba cada respuesta al instante y muestra
 *       la <strong>explicación</strong> solo cuando fallas.</li>
 *   <li>Al terminar: aciertos, porcentaje, nota sobre 10 y mensaje de
 *       aprobado/suspenso.</li>
 *   <li>Repasa después solo las preguntas falladas.</li>
 * </ol>
 *
 * <p>La <strong>lógica de corrección</strong> ({@link #corregir}) está separada de
 * la parte interactiva: por eso se puede testear sin teclear nada.</p>
 *
 * <h2>Conceptos</h2>
 * {@code record}, inmutabilidad, encapsular la lógica testeable, streams,
 * {@code List.copyOf}.
 */
public final class Ejercicio18_Cuestionario {

    private Ejercicio18_Cuestionario() {
    }

    /**
     * Pregunta de opción múltiple inmutable.
     *
     * @param enunciado  texto de la pregunta
     * @param opciones   lista de opciones (mínimo 2)
     * @param correcta   índice de la opción correcta, empezando por 0
     * @param explicacion se muestra si el alumno falla
     */
    public record Pregunta(String enunciado, List<String> opciones, int correcta, String explicacion) {

        public Pregunta {
            if (enunciado == null || enunciado.isBlank()) {
                throw new IllegalArgumentException("El enunciado es obligatorio");
            }
            opciones = List.copyOf(opciones);
            if (opciones.size() < 2) {
                throw new IllegalArgumentException("Hacen falta al menos 2 opciones");
            }
            if (correcta < 0 || correcta >= opciones.size()) {
                throw new IllegalArgumentException("La correcta (" + correcta + ") está fuera de rango");
            }
        }

        /** ¿Es válida la respuesta dada? */
        public boolean esCorrecta(int respuesta) {
            return respuesta == correcta;
        }

        /** Letra de la respuesta correcta: A, B, C… */
        public char letraCorrecta() {
            return (char) ('A' + correcta);
        }
    }

    /** El resultado de corregir un cuestionario. */
    public record Resultado(int aciertos, int total, List<Integer> fallos) {

        public Resultado {
            fallos = List.copyOf(fallos);
            if (total <= 0) {
                throw new IllegalArgumentException("total debe ser > 0");
            }
            if (aciertos < 0 || aciertos > total) {
                throw new IllegalArgumentException("aciertos fuera de rango: " + aciertos);
            }
        }

        /** Nº de respuestas erróneas (el componente {@code fallos} es la lista de índices). */
        public int totalFallos() {
            return total - aciertos;
        }

        public double porcentaje() {
            return Math.round(aciertos * 1000.0 / total) / 10.0;
        }

        /** Nota sobre 10. */
        public double nota() {
            return Math.round(aciertos * 100.0 / total) / 10.0;
        }

        public boolean aprobado() {
            return nota() >= 5.0;
        }

        /** Mensaje final en castellano. */
        public String resumen() {
            return String.format("%d/%d correctas (%.1f %%) · nota %.1f/10 → %s",
                    aciertos, total, porcentaje(), nota(), aprobado() ? "APROBADO" : "SUSPENSO");
        }
    }

    /** El banco de preguntas del cuestionario. */
    public static List<Pregunta> banco() {
        return List.of(
                new Pregunta("¿Qué imprime System.out.println(10 / 3)?",
                        List.of("3", "3.33", "4", "Error de compilación"), 0,
                        "La división de enteros trunca: 10/3 = 3."),
                new Pregunta("¿Cuál de estas NO es primitiva?",
                        List.of("int", "double", "String", "char"), 2,
                        "String es una clase de java.lang, no un tipo primitivo."),
                new Pregunta("¿Cómo se hereda de una clase en Java?",
                        List.of("class Hijo implements Padre",
                                "class Hijo extends Padre",
                                "class Hijo inherits Padre",
                                "class Hijo : Padre"), 1,
                        "extends hereda de una clase; implementa se usa para interfaces."),
                new Pregunta("¿Cuál es la jerarquía correcta de tipos (de menor a mayor)?",
                        List.of("byte < int < long < float < double",
                                "int < byte < long < double < float",
                                "short < long < int < byte < double",
                                "byte < short < int < long < double"), 0,
                        "El orden de rango es byte, short, int, long, float, double."),
                new Pregunta("¿Qué interfaz exige implementar equals() y hashCode() a la vez?",
                        List.of("Comparable", "Iterable", "Object (todo objeto)", "Cloneable"), 2,
                        "Son métodos de Object; conviene sobrescribirlos juntos."),
                new Pregunta("¿Cuál de estos lanza una excepción 'checked'?",
                        List.of("NullPointerException", "IndexOutOfBoundsException",
                                "IOException", "ArithmeticException"), 2,
                        "IOException es checked: obliga a capturarla o declararla."),
                new Pregunta("¿En qué orden se recorre un HashMap?",
                        List.of("Siempre alfabético", "Según la clave (hashCode)",
                                "En el orden de inserción", "Es indeterminado"), 3,
                        "HashMap no garantiza ningún orden de iteración."),
                new Pregunta("¿Qué hace stream().map(...).collect(Collectors.toList())?",
                        List.of("Filtra los elementos",
                                "Transforma cada elemento y los recolecta en una lista",
                                "Ordena la lista",
                                "Cuenta los elementos"), 1,
                        "map transforma; filter filtra; collect reúne el resultado."),
                new Pregunta("¿Cuántas veces se ejecuta un bucle while(true) si nunca cambia la condición?",
                        List.of("Una", "Dos", "Infinitas (hasta break/return)", "Ninguna"), 2,
                        "Sin un break o return, sería un bucle infinito."),
                new Pregunta("¿Qué palabra clave evita que una clase se herede?",
                        List.of("final", "static", "abstract", "private"), 0,
                        "Las clases finales no pueden tener subclases."));
    }

    /**
     * Corrige las respuestas dadas por el alumno.
     *
     * @param preguntas  banco usado
     * @param respuestas índice elegido para cada pregunta (en el mismo orden)
     * @return el resultado con aciertos, porcentaje y fallos
     * @throws IllegalArgumentException si las listas no cuadran o hay índices fuera de rango
     */
    public static Resultado corregir(List<Pregunta> preguntas, List<Integer> respuestas) {
        if (preguntas == null || preguntas.isEmpty()) {
            throw new IllegalArgumentException("No hay preguntas");
        }
        if (respuestas == null || respuestas.size() != preguntas.size()) {
            throw new IllegalArgumentException("Faltan respuestas: " + preguntas.size()
                    + " preguntas y " + (respuestas == null ? 0 : respuestas.size()) + " respuestas");
        }
        int aciertos = 0;
        List<Integer> fallos = new ArrayList<>();
        for (int i = 0; i < preguntas.size(); i++) {
            int respuesta = respuestas.get(i);
            if (respuesta < 0 || respuesta >= preguntas.get(i).opciones().size()) {
                throw new IllegalArgumentException("Respuesta fuera de rango en la pregunta " + (i + 1));
            }
            if (preguntas.get(i).esCorrecta(respuesta)) {
                aciertos++;
            } else {
                fallos.add(i);
            }
        }
        return new Resultado(aciertos, preguntas.size(), fallos);
    }

    /** Respuestas "todas correctas" (útil como caso base de pruebas). */
    public static List<Integer> todasCorrectas(List<Pregunta> preguntas) {
        return preguntas.stream().map(Pregunta::correcta).toList();
    }

    /** Respuestas "todas incorrectas": cada una, la opción siguiente a la correcta. */
    public static List<Integer> todasIncorrectas(List<Pregunta> preguntas) {
        return preguntas.stream()
                .map(p -> (p.correcta() + 1) % p.opciones().size())
                .toList();
    }

    /** Recorta el banco a las primeras {@code n} preguntas. */
    public static List<Pregunta> primeras(int n) {
        List<Pregunta> banco = banco();
        if (n < 1 || n > banco.size()) {
            throw new IllegalArgumentException("n entre 1 y " + banco.size());
        }
        return banco.subList(0, n);
    }

    /** Devuelve solo las preguntas que el alumno ha fallado (para el repaso). */
    public static List<Pregunta> falladas(List<Pregunta> preguntas, Resultado resultado) {
        List<Pregunta> seleccion = new ArrayList<>();
        resultado.fallos().forEach(i -> seleccion.add(preguntas.get(i)));
        return seleccion;
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 18 · Cuestionario autocorregible ────");
        List<Pregunta> banco = banco();
        System.out.println("  Banco: " + banco.size() + " preguntas");

        // Demostración automática de la corrección con respuestas de ejemplo
        List<Integer> ejemplo = List.of(0, 3, 1, 0, 2, 0, 3, 3, 2, 0);   // 7 aciertos
        Resultado resultado = corregir(banco, ejemplo);
        System.out.println("  Respuestas de ejemplo → " + resultado.resumen());

        if (!resultado.fallos().isEmpty()) {
            System.out.println("  Repaso de falladas:");
            falladas(banco, resultado).forEach(p -> System.out.println("    ✗ " + p.enunciado()
                    + "\n      Correcta: " + p.letraCorrecta() + ") "
                    + p.opciones().get(p.correcta())
                    + "\n      " + p.explicacion()));
        }

        // Autoevaluación perfecta para comprobar el techo
        Resultado perfecto = corregir(banco, todasCorrectas(banco));
        System.out.println("  Máximo posible → " + perfecto.resumen());
    }
}
