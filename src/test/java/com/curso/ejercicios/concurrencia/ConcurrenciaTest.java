package com.curso.ejercicios.concurrencia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 12 · Concurrencia")
class ConcurrenciaTest {

    @Test
    @DisplayName("descargar() devuelve la respuesta simulada")
    void descargar() throws InterruptedException {
        assertEquals("200 OK → https://ejemplo.com", Ejercicio12_Concurrencia.descargar("https://ejemplo.com"));
    }

    @Test
    @DisplayName("tareaCorta() devuelve el id que recibió")
    void tareaCorta() throws InterruptedException {
        assertEquals(0, Ejercicio12_Concurrencia.tareaCorta(0));
        assertEquals(42, Ejercicio12_Concurrencia.tareaCorta(42));
    }

    @Test
    @DisplayName("leerUsuario() y guardar() responden con el formato esperado")
    void respuestas() {
        assertEquals("usuario-7", Ejercicio12_Concurrencia.leerUsuario(7));
        assertEquals("guardado:A", Ejercicio12_Concurrencia.guardar("A"));
    }

    @Test
    @DisplayName("50 tareas en paralelo suman lo mismo que en serie")
    @Timeout(10)
    void poolParalelo() throws Exception {
        int totalTareas = 50;
        List<Future<Integer>> futuros = new ArrayList<>();

        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
            for (int i = 0; i < totalTareas; i++) {
                final int id = i;
                futuros.add(pool.submit(() -> Ejercicio12_Concurrencia.tareaCorta(id)));
            }
        }

        int suma = 0;
        for (Future<Integer> f : futuros) {
            suma += f.get(5, TimeUnit.SECONDS);
        }
        assertEquals(1225, suma);   // 0+1+...+49
    }

    @Test
    @DisplayName("Hilos virtuales: 200 tareas completadas sin errores")
    @Timeout(15)
    void hilosVirtuales() throws Exception {
        try (ExecutorService virtuales = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> tareas = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
                final int id = i;
                tareas.add(virtuales.submit(() -> Ejercicio12_Concurrencia.tareaCorta(id)));
            }
            int completadas = 0;
            for (Future<Integer> t : tareas) {
                assertEquals(completadas, t.get(5, TimeUnit.SECONDS));
                completadas++;
            }
            assertEquals(200, completadas);
        }
        assertTrue(Thread.activeCount() >= 1);
    }
}
