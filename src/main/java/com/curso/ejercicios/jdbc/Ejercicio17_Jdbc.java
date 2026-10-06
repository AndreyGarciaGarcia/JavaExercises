package com.curso.ejercicios.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * EJERCICIO 17 · JDBC con H2 en memoria
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Crea la tabla {@code productos} y métela de ejemplo a la BD en memoria.</li>
 *   <li>Consulta, actualiza y borra filas usando <strong>siempre</strong>
 *       {@code PreparedStatement} (nunca concatenar valores en el SQL).</li>
 *   <li>Envuelve varias escrituras en una <strong>transacción</strong>: o se hacen
 *       todas o ninguna.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code Connection}, {@code PreparedStatement}, {@code ResultSet}, parámetros
 * con {@code ?}, commits/rollbacks, DAO básico.
 *
 * <p><strong>Requiere H2</strong> en el classpath: Maven lo aporta (dependencia
 * {@code runtime} del {@code pom.xml}); los scripts {@code run.bat} /
 * {@code test.bat} lo descargan solos en {@code lib/}.</p>
 */
public final class Ejercicio17_Jdbc {

    /** URL de una BD en memoria; se elimina al desconectar (salvo DB_CLOSE_DELAY). */
    public static final String URL = "jdbc:h2:mem:ejercicio17;DB_CLOSE_DELAY=-1";

    private Ejercicio17_Jdbc() {
    }

    /** Producto inmutable de la tabla. */
    public record Producto(long id, String nombre, double precio) {

        public Producto {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            if (precio < 0) {
                throw new IllegalArgumentException("El precio no puede ser negativo");
            }
        }
    }

    /** Crea la tabla si no existe. */
    public static void crearTabla(Connection conexion) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS productos (
                    id     IDENTITY PRIMARY KEY,
                    nombre VARCHAR(80) NOT NULL,
                    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0)
                )
                """;
        try (Statement st = conexion.createStatement()) {
            st.execute(sql);
        }
    }

    /** Borra todos los datos (útil para empezar cada prueba de cero). */
    public static void vaciar(Connection conexion) throws SQLException {
        try (Statement st = conexion.createStatement()) {
            st.execute("DELETE FROM productos");
        }
    }

    /** Inserta un producto y devuelve su id generado. */
    public static long insertar(Connection conexion, String nombre, double precio) throws SQLException {
        String sql = "INSERT INTO productos(nombre, precio) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getLong(1);
                }
            }
        }
        throw new SQLException("No se pudo obtener el id generado");
    }

    /** Todos los productos ordenados por nombre. */
    public static List<Producto> listar(Connection conexion) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, precio FROM productos ORDER BY nombre";
        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")));
            }
        }
        return productos;
    }

    /** Búsqueda con parámetro: esto es lo que hace seguro contra SQL injection. */
    public static List<Producto> buscarPorNombre(Connection conexion, String texto) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, precio FROM productos WHERE LOWER(nombre) LIKE ? ORDER BY id";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + texto.toLowerCase(java.util.Locale.ROOT) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio")));
                }
            }
        }
        return productos;
    }

    /** Cambia el precio. Devuelve el número de filas afectadas. */
    public static int actualizarPrecio(Connection conexion, long id, double nuevoPrecio) throws SQLException {
        String sql = "UPDATE productos SET precio = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, nuevoPrecio);
            ps.setLong(2, id);
            return ps.executeUpdate();
        }
    }

    /** Borra un producto. Devuelve el número de filas afectadas. */
    public static int eliminar(Connection conexion, long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }

    /**
     * Transacción: aplica un descuento a todos los productos.
     * Si algo falla, se hace <strong>rollback</strong> y no se pinta nada.
     */
    public static int aplicarDescuentoATodos(Connection conexion, double porcentaje) throws SQLException {
        if (porcentaje <= 0 || porcentaje >= 100) {
            throw new IllegalArgumentException("Porcentaje entre 0 y 100: " + porcentaje);
        }
        String sql = "UPDATE productos SET precio = ROUND(precio * ?, 2)";
        boolean anteriorAutocommit = conexion.getAutoCommit();
        try {
            conexion.setAutoCommit(false);
            int filas;
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setDouble(1, 1 - porcentaje / 100.0);
                filas = ps.executeUpdate();
            }
            conexion.commit();
            return filas;
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(anteriorAutocommit);   // se restaura siempre
        }
    }

    /** Conexión a la BD en memoria. */
    public static Connection conectar() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver H2 no encontrado: añade h2 al classpath", e);
        }
        return DriverManager.getConnection(URL, "sa", "");
    }

    /** @return true si el driver H2 está disponible en el classpath. */
    public static boolean driverDisponible() {
        try {
            Class.forName("org.h2.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 17 · JDBC con H2 ────────────────────");
        if (!driverDisponible()) {
            System.out.println("  ✗ No se encontró el driver H2.");
            System.out.println("    Ejecuta 'mvn compile exec:java' o deja que run.bat lo descargue.");
            return;
        }

        try (Connection conexion = conectar()) {
            crearTabla(conexion);
            vaciar(conexion);

            long idTeclado = insertar(conexion, "Teclado mecánico", 79.90);
            insertar(conexion, "Ratón inalámbrico", 24.50);
            insertar(conexion, "Monitor 27\"", 219.00);
            System.out.println("  Insertado teclado con id = " + idTeclado);

            System.out.println("  Productos: ");
            listar(conexion).forEach(p -> System.out.printf("    #%-3d %-20s %.2f €%n",
                    p.id(), p.nombre(), p.precio()));

            System.out.println("  Búsqueda 'rat': " + buscarPorNombre(conexion, "rat").size() + " resultado(s)");

            int afectadas = actualizarPrecio(conexion, idTeclado, 69.90);
            System.out.println("  Filas actualizadas → " + afectadas);

            int descuento = aplicarDescuentoATodos(conexion, 10);
            System.out.println("  Descuento 10 % aplicado a " + descuento + " filas");
            listar(conexion).forEach(p -> System.out.printf("    %-20s %.2f €%n", p.nombre(), p.precio()));

            eliminar(conexion, idTeclado);
            System.out.println("  Tras borrar el teclado quedan " + listar(conexion).size() + " productos");
        } catch (SQLException e) {
            System.out.println("  ✗ Error de base de datos: " + e.getMessage());
        }
    }
}
