package com.curso.ejercicios.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 17 · JDBC con H2")
class JdbcTest {

    private Connection conexion;

    @BeforeEach
    void abrir() throws SQLException {
        // Si no hay driver H2 en el classpath, el test se omite en lugar de fallar
        Assumptions.assumeTrue(Ejercicio17_Jdbc.driverDisponible(),
                "Driver H2 no disponible (dependencia runtime del pom.xml)");
        conexion = Ejercicio17_Jdbc.conectar();
        Ejercicio17_Jdbc.crearTabla(conexion);
        Ejercicio17_Jdbc.vaciar(conexion);
    }

    @AfterEach
    void cerrar() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }

    @Test
    @DisplayName("Insertar y listar devuelve los productos ordenados por nombre")
    void insertarYListar() throws SQLException {
        long id1 = Ejercicio17_Jdbc.insertar(conexion, "Teclado", 79.90);
        long id2 = Ejercicio17_Jdbc.insertar(conexion, "Ratón", 24.50);

        assertTrue(id1 > 0 && id2 > id1, "Los ids autogenerados crecen");
        List<Ejercicio17_Jdbc.Producto> productos = Ejercicio17_Jdbc.listar(conexion);

        assertEquals(2, productos.size());
        assertEquals("Ratón", productos.get(0).nombre());     // orden alfabético
        assertEquals("Teclado", productos.get(1).nombre());
        assertEquals(79.90, productos.get(1).precio(), 0.001);
    }

    @Test
    @DisplayName("Buscar por nombre usa parámetros y no distingue mayúsculas")
    void buscarPorNombre() throws SQLException {
        Ejercicio17_Jdbc.insertar(conexion, "Monitor 27", 219.0);
        Ejercicio17_Jdbc.insertar(conexion, "Ratón", 24.50);
        Ejercicio17_Jdbc.insertar(conexion, "Teclado", 79.90);

        assertEquals(1, Ejercicio17_Jdbc.buscarPorNombre(conexion, "rat").size());
        assertEquals(1, Ejercicio17_Jdbc.buscarPorNombre(conexion, "MONITOR").size());
        assertEquals(0, Ejercicio17_Jdbc.buscarPorNombre(conexion, "portátil").size());
        assertEquals(3, Ejercicio17_Jdbc.buscarPorNombre(conexion, "").size());
    }

    @Test
    @DisplayName("Actualizar y eliminar afectan a las filas indicadas")
    void actualizarYEliminar() throws SQLException {
        long id = Ejercicio17_Jdbc.insertar(conexion, "Cable", 5.00);

        assertEquals(1, Ejercicio17_Jdbc.actualizarPrecio(conexion, id, 4.00));
        assertEquals(4.00, Ejercicio17_Jdbc.buscarPorNombre(conexion, "cable").get(0).precio(), 0.001);

        assertEquals(1, Ejercicio17_Jdbc.eliminar(conexion, id));
        assertTrue(Ejercicio17_Jdbc.listar(conexion).isEmpty());

        // Id inexistente: 0 filas afectadas, no lanza
        assertEquals(0, Ejercicio17_Jdbc.eliminar(conexion, 999));
        assertEquals(0, Ejercicio17_Jdbc.actualizarPrecio(conexion, 999, 1.0));
    }

    @Test
    @DisplayName("La transacción de descuento se aplica y restaura el autocommit")
    void transaccion() throws SQLException {
        Ejercicio17_Jdbc.insertar(conexion, "A", 100.00);
        Ejercicio17_Jdbc.insertar(conexion, "B", 50.00);

        assertTrue(conexion.getAutoCommit());
        int filas = Ejercicio17_Jdbc.aplicarDescuentoATodos(conexion, 10);

        assertEquals(2, filas);
        assertTrue(conexion.getAutoCommit(), "El autocommit se restaura siempre");
        List<Ejercicio17_Jdbc.Producto> productos = Ejercicio17_Jdbc.listar(conexion);
        assertEquals(90.00, productos.get(0).precio(), 0.001);
        assertEquals(45.00, productos.get(1).precio(), 0.001);

        // Porcentaje inválido: se rechaza antes de tocar la BD
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio17_Jdbc.aplicarDescuentoATodos(conexion, 150));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio17_Jdbc.aplicarDescuentoATodos(conexion, 0));
    }

    @Test
    @DisplayName("Un producto inválido no llega a la base de datos")
    void validarProducto() {
        assertThrows(IllegalArgumentException.class, () -> new Ejercicio17_Jdbc.Producto(1, null, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Ejercicio17_Jdbc.Producto(1, "ok", -1.0));
        assertFalse(Ejercicio17_Jdbc.URL.isEmpty());
        assertTrue(Ejercicio17_Jdbc.URL.startsWith("jdbc:h2:mem:"));
    }

    @Test
    @DisplayName("insertar con SQL manipulado no inyecta (se usa PreparedStatement)")
    void sinInyeccion() throws SQLException {
        Ejercicio17_Jdbc.insertar(conexion, "O'Reilly'; DROP TABLE productos; --", 10.0);
        assertEquals(1, Ejercicio17_Jdbc.listar(conexion).size());
        // La tabla sigue viva y el texto se guarda tal cual
        assertEquals(1, Ejercicio17_Jdbc.buscarPorNombre(conexion, "reilly").size());
    }
}
