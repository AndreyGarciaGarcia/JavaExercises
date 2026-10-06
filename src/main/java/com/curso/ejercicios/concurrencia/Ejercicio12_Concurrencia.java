package com.curso.ejercicios.concurrencia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * EJERCICIO 12 · Concurrencia
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Descarga (simulada) 5 URLs en paralelo y espera a todas.</li>
 *   <li>Cadena dos tareas con {@code CompletableFuture} sin bloquear.</li>
 *   <li>Extra: usa <strong>hilos virtuales</strong> (Java 21) para 1000 tareas cortas.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code ExecutorService}, {@code Future}, {@code CompletableFuture}, hilos virtuales,
 * visibilidad de resultados y cierre ordenado de pools.
 */
public final class Ejercicio12_Concurrencia {

    private Ejercicio12_Concurrencia() {
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\n── Ejercicio 12 · Concurrencia ───────────────────");

        List<String> urls = List.of(
                "https://ejemplo.com/inicio", "https://ejemplo.com/docs",
                "https://ejemplo.com/api", "https://ejemplo.com/blog", "https://ejemplo.com/precios");

        // 1) Pool clásico con hilos platform
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            List<Future<String>> futuros = new ArrayList<>();
            for (String url : urls) {
                futuros.add(pool.submit(() -> descargar(url)));
            }
            System.out.println("— Pool de 3 hilos —");
            for (Future<String> f : futuros) {
                System.out.println("   " + f.get(5, TimeUnit.SECONDS));
            }
        }

        // 2) Cadena no bloqueante
        System.out.println("\n— CompletableFuture —");
        String resultado = CompletableFuture
                .supplyAsync(() -> leerUsuario(7))
                .thenApply(nombre -> "Hola, " + nombre)
                .thenApply(s -> s.toUpperCase())
                .exceptionally(e -> "ERROR: " + e.getMessage())
                .get();
        System.out.println("   " + resultado);

        CompletableFuture<Void> combinado = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> guardar("A")),
                CompletableFuture.supplyAsync(() -> guardar("B")));
        combinado.get();
        System.out.println("   ambas tareas completadas");

        // 3) Hilos virtuales: 1000 tareas de E/S sin consumir 1000 hilos del sistema
        System.out.println("\n— 1000 hilos virtuales —");
        long inicio = System.nanoTime();
        try (ExecutorService virtuales = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> tareas = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                final int id = i;
                tareas.add(virtuales.submit(() -> tareaCorta(id)));
            }
            int total = 0;
            for (Future<Integer> t : tareas) {
                total += t.get();
            }
            long ms = (System.nanoTime() - inicio) / 1_000_000;
            System.out.println("   suma de resultados = " + total + " en " + ms + " ms");
        }

        System.out.println("\n   Hilos del sistema usados: "
                + Thread.activeCount() + " (la JVM solo mantiene unos pocos)");
    }

    /** Simula una descarga con retardo. */
    static String descargar(String url) throws InterruptedException {
        Thread.sleep(150);
        return "200 OK → " + url;
    }

    static String leerUsuario(int id) {
        return "usuario-" + id;
    }

    static String guardar(String clave) {
        return "guardado:" + clave;
    }

    /** Trabajo corto de E/S; devuelve un número para poder comprobar el resultado. */
    static int tareaCorta(int id) throws InterruptedException {
        Thread.sleep(1);
        return id;
    }
}
