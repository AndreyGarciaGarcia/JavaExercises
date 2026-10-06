package com.curso.ejercicios.patrones;

import java.util.ArrayList;
import java.util.List;

/**
 * EJERCICIO 16 · Patrones de diseño (Builder, Strategy, Factory)
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Construye un {@code Usuario} con muchos campos opcionales sin un
 *       constructor de 8 parámetros ni setters mutables.</li>
 *   <li>Elige en tiempo de ejecución qué estrategia de descuento se aplica.</li>
 *   <li>Crea notificaciones sin desacoplar al cliente del tipo concreto.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * Patrón <em>Builder</em>, <em>Strategy</em> con interfaces funcionales,
 * <em>Factory</em> estática, objetos inmutables.
 */
public final class Ejercicio16_Patrones {

    private Ejercicio16_Patrones() {
    }

    // ─────────────────────────────────────────────── BUILDER ──
    /** Objeto inmutable: se termina de montar en el Builder. */
    public static final class Usuario {

        private final String nombre;
        private final String email;
        private final int edad;
        private final String ciudad;
        private final boolean noticias;

        private Usuario(Builder b) {
            this.nombre = b.nombre;
            this.email = b.email;
            this.edad = b.edad;
            this.ciudad = b.ciudad;
            this.noticias = b.noticias;
        }

        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public int getEdad() { return edad; }
        public String getCiudad() { return ciudad; }
        public boolean recibeNoticias() { return noticias; }

        public static Builder builder(String nombre) {
            return new Builder(nombre);
        }

        @Override
        public String toString() {
            return "Usuario{" + nombre + ", " + email + ", " + edad
                    + " años, " + ciudad + ", noticias=" + noticias + "}";
        }

        /** Constructor encadenable: solo los obligarios van en el constructor. */
        public static final class Builder {

            private final String nombre;
            private String email = "sin-email";
            private int edad = 18;
            private String ciudad = "desconocida";
            private boolean noticias = false;

            private Builder(String nombre) {
                if (nombre == null || nombre.isBlank()) {
                    throw new IllegalArgumentException("El nombre es obligatorio");
                }
                this.nombre = nombre.trim();
            }

            public Builder email(String email) { this.email = email; return this; }
            public Builder edad(int edad) {
                if (edad < 0 || edad > 150) {
                    throw new IllegalArgumentException("Edad no válida: " + edad);
                }
                this.edad = edad;
                return this;
            }
            public Builder ciudad(String ciudad) { this.ciudad = ciudad; return this; }
            public Builder conNoticias(boolean s) { this.noticias = s; return this; }

            public Usuario build() {
                return new Usuario(this);
            }
        }
    }

    // ────────────────────────────────────────────── STRATEGY ──
    /** Estrategia (interfaz funcional): cómo calcular el descuento. */
    @FunctionalInterface
    public interface EstrategiaDescuento {

        double aplicar(double precio);

        /** Estrategia "sin descuento" (identidad). */
        EstrategiaDescuento NINGUNA = p -> p;

        /** Estrategia fija: 10 % para socios. */
        EstrategiaDescuento SOCIOS = p -> p * 0.90;

        /** Estrategia progresiva: 20 % a partir de 100 €. */
        EstrategiaDescuento CANTIDAD_ALTA = p -> p >= 100 ? p * 0.80 : p;
    }

    // ─────────────────────────────────────────────── FACTORY ──
    public enum TipoNotificacion { EMAIL, SMS, PUSH }

    public interface Notificacion {
        String enviar(String destinatario, String texto);
    }

    /** Notificaciones concretas: el cliente solo conoce la interfaz. */
    static final class EmailNotificacion implements Notificacion {
        @Override public String enviar(String destinatario, String texto) {
            return "[EMAIL a " + destinatario + "] " + texto;
        }
    }

    static final class SmsNotificacion implements Notificacion {
        @Override public String enviar(String destinatario, String texto) {
            return "[SMS a " + destinatario + "] " + texto;
        }
    }

    static final class PushNotificacion implements Notificacion {
        @Override public String enviar(String destinatario, String texto) {
            return "[PUSH a " + destinatario + "] " + texto;
        }
    }

    /** Factory: centraliza la creación y oculta los "new". */
    public static final class NotificacionFactory {

        private NotificacionFactory() {
        }

        public static Notificacion crear(TipoNotificacion tipo) {
            return switch (tipo) {
                case EMAIL -> new EmailNotificacion();
                case SMS -> new SmsNotificacion();
                case PUSH -> new PushNotificacion();
            };
        }
    }

    // ──────────────────────────────────────────── UTILIDADES ──

    /** Aplica la estrategia indicada y devuelve el precio final redondeado a 2 decimales. */
    public static double precioFinal(double precio, EstrategiaDescuento estrategia) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo: " + precio);
        }
        if (estrategia == null) {
            throw new IllegalArgumentException("La estrategia no puede ser null");
        }
        return Math.round(estrategia.aplicar(precio) * 100.0) / 100.0;
    }

    /** Construye una lista de usuarios de ejemplo con el Builder. */
    public static List<Usuario> usuariosDeEjemplo() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(Usuario.builder("Ana").email("ana@mail.com").edad(30)
                .ciudad("Sevilla").conNoticias(true).build());
        usuarios.add(Usuario.builder("Luis").email("luis@mail.com").build());
        usuarios.add(Usuario.builder("Marta").edad(22).ciudad("Bilbao").build());
        return usuarios;
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 16 · Patrones de diseño ─────────────");

        // Builder
        Usuario usuario = Usuario.builder("Carla")
                .email("carla@mail.com")
                .edad(28)
                .ciudad("Valencia")
                .conNoticias(true)
                .build();
        System.out.println("  Builder → " + usuario);
        System.out.println("  Por defecto → " + Usuario.builder("Pepe").build());

        // Strategy
        double precio = 120.0;
        System.out.printf("  Precio %.2f € | ninguno → %.2f%n", precio,
                precioFinal(precio, EstrategiaDescuento.NINGUNA));
        System.out.printf("  Precio %.2f € | socios  → %.2f%n", precio,
                precioFinal(precio, EstrategiaDescuento.SOCIOS));
        System.out.printf("  Precio %.2f € | alta    → %.2f%n", precio,
                precioFinal(precio, EstrategiaDescuento.CANTIDAD_ALTA));

        // Strategy elegida en ejecución
        EstrategiaDescuento dinamica = precio < 50 ? EstrategiaDescuento.NINGUNA
                : EstrategiaDescuento.SOCIOS;
        System.out.printf("  Dinámica (>=50) → %.2f%n", precioFinal(precio, dinamica));

        // Factory
        for (TipoNotificacion tipo : TipoNotificacion.values()) {
            Notificacion n = NotificacionFactory.crear(tipo);
            System.out.println("  " + n.enviar("600123456", "¡Hola desde Java!"));
        }

        usuariosDeEjemplo().forEach(u -> System.out.println("  Usuario → " + u));
    }
}
