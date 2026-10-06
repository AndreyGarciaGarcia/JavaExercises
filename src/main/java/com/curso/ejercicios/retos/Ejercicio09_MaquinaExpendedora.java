package com.curso.ejercicios.retos;

import com.curso.util.Teclado;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * RETO 09 · Máquina expendedora (ejercicio integrador)
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Muestra el catálogo con precio y stock.</li>
 *   <li>Pide una opción y el dinero introducido.</li>
 *   <li>Si alcanza el precio, entrega el producto y devuelve el cambio; si no, avisa.</li>
 *   <li>Actualiza el stock y permite repetir hasta pulsar 0.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * Integrador: bucles, {@code switch}, {@code Map}, clases, validación y excepciones.
 */
public final class Ejercicio09_MaquinaExpendedora {

    /** Producto inmutable: ideal un {@code record} para datos de solo lectura. */
    public record Producto(String nombre, double precio) {
    }

    private static final Map<Integer, Producto> CATALOGO = new LinkedHashMap<>();
    private static final Map<Integer, Integer> STOCK = new LinkedHashMap<>();

    static {
        CATALOGO.put(1, new Producto("Agua", 0.80));
        CATALOGO.put(2, new Producto("Café", 1.10));
        CATALOGO.put(3, new Producto("Refresco", 1.50));
        CATALOGO.put(4, new Producto("Chocolate", 1.75));
        CATALOGO.put(5, new Producto("Galletas", 2.00));
        CATALOGO.forEach((id, p) -> STOCK.put(id, 5));   // 5 de cada uno
    }

    private Ejercicio09_MaquinaExpendedora() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Reto 09 · Máquina expendedora ─────────────────");

        boolean encendida = true;
        while (encendida) {
            mostrarCatalogo();
            int opcion = Teclado.entero("Elige producto (0 = apagar): ");
            if (opcion == 0) {
                encendida = false;
                continue;
            }
            if (!CATALOGO.containsKey(opcion)) {
                System.out.println("  ✗ Opción inexistente.");
                continue;
            }
            double pagado = Teclado.decimal("  Dinero introducido (€): ");
            try {
                vender(opcion, pagado);
            } catch (IllegalArgumentException | StockAgotadoException e) {
                System.out.println("  ✗ " + e.getMessage());
            }
        }
        System.out.println("\n  Gracias por su compra. 👋");
    }

    /**
     * Realiza la venta de un producto.
     *
     * @param id      identificador del producto
     * @param pagado  dinero introducido
     * @return cambio a devolver (0 si paga exacto)
     * @throws IllegalArgumentException si no alcanza el precio o paga negativo
     * @throws StockAgotadoException   si no queda stock
     */
    public static double vender(int id, double pagado) throws StockAgotadoException {
        Producto producto = CATALOGO.get(id);
        if (producto == null) {
            throw new IllegalArgumentException("producto inexistente");
        }
        if (pagado < 0) {
            throw new IllegalArgumentException("el dinero no puede ser negativo");
        }
        if (STOCK.getOrDefault(id, 0) <= 0) {
            throw new StockAgotadoException(producto.nombre());
        }
        if (pagado < producto.precio()) {
            throw new IllegalArgumentException(
                    "faltan " + String.format("%.2f", producto.precio() - pagado) + " €");
        }

        STOCK.put(id, STOCK.get(id) - 1);
        double cambio = pagado - producto.precio();
        System.out.printf("  ✓ %s entregado. Cambio: %.2f €%n", producto.nombre(), cambio);
        return cambio;
    }

    /** Reinicia el stock a 5 unidades de cada producto (útil entre partidas y en tests). */
    public static void reponer() {
        STOCK.replaceAll((id, cantidad) -> 5);
    }

    /** Unidades disponibles de un producto. */
    public static int stockDe(int id) {
        return STOCK.getOrDefault(id, 0);
    }

    private static void mostrarCatalogo() {
        System.out.println();
        CATALOGO.forEach((id, p) -> System.out.printf("  %d · %-12s %5.2f €   (stock: %d)%n",
                id, p.nombre(), p.precio(), stockDe(id)));
        System.out.println("  ───────────────────────────────────────────────");
    }

    /** Excepción propia (checked) para producto sin existencias. */
    public static class StockAgotadoException extends Exception {
        private static final long serialVersionUID = 1L;

        public StockAgotadoException(String producto) {
            super("Producto agotado: " + producto);
        }
    }
}
