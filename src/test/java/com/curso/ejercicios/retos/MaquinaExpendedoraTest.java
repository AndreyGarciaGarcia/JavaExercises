package com.curso.ejercicios.retos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Reto 09 · Máquina expendedora")
class MaquinaExpendedoraTest {

    @BeforeEach
    void reponer() {
        Ejercicio09_MaquinaExpendedora.reponer();
    }

    @Test
    @DisplayName("Venta correcta devuelve el cambio")
    void ventaCorrecta() throws Exception {
        double cambio = Ejercicio09_MaquinaExpendedora.vender(1, 2.00);   // Agua 0,80 €
        assertEquals(1.20, cambio, 0.0001);
        assertEquals(4, Ejercicio09_MaquinaExpendedora.stockDe(1));
    }

    @Test
    @DisplayName("Pago exacto devuelve 0 de cambio")
    void pagoExacto() throws Exception {
        double cambio = Ejercicio09_MaquinaExpendedora.vender(2, 1.10);   // Café 1,10 €
        assertEquals(0.0, cambio, 0.0001);
    }

    @Test
    @DisplayName("No alcanza el precio → IllegalArgumentException")
    void pagoInsuficiente() {
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio09_MaquinaExpendedora.vender(5, 1.00));    // Galletas 2,00 €
        // El stock no debe haberse modificado
        assertEquals(5, Ejercicio09_MaquinaExpendedora.stockDe(5));
    }

    @Test
    @DisplayName("Producto inexistente o dinero negativo → error")
    void entradasInvalidas() {
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio09_MaquinaExpendedora.vender(99, 5.00));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio09_MaquinaExpendedora.vender(1, -1));
    }

    @Test
    @DisplayName("Agotar el stock lanza StockAgotadoException")
    void stockAgotado() throws Exception {
        for (int i = 0; i < 5; i++) {
            Ejercicio09_MaquinaExpendedora.vender(3, 5.00);
        }
        assertEquals(0, Ejercicio09_MaquinaExpendedora.stockDe(3));
        assertThrows(Ejercicio09_MaquinaExpendedora.StockAgotadoException.class,
                () -> Ejercicio09_MaquinaExpendedora.vender(3, 5.00));
    }
}
