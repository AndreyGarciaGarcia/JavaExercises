package com.curso.ejercicios.patrones;

import com.curso.ejercicios.patrones.Ejercicio16_Patrones.EstrategiaDescuento;
import com.curso.ejercicios.patrones.Ejercicio16_Patrones.Notificacion;
import com.curso.ejercicios.patrones.Ejercicio16_Patrones.NotificacionFactory;
import com.curso.ejercicios.patrones.Ejercicio16_Patrones.TipoNotificacion;
import com.curso.ejercicios.patrones.Ejercicio16_Patrones.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ejercicio 16 · Patrones de diseño")
class PatronesTest {

    @Test
    @DisplayName("Builder: solo relleno lo que me interesa")
    void builder() {
        Usuario ana = Usuario.builder("Ana")
                .email("ana@mail.com")
                .edad(30)
                .ciudad("Sevilla")
                .conNoticias(true)
                .build();

        assertEquals("Ana", ana.getNombre());
        assertEquals("ana@mail.com", ana.getEmail());
        assertEquals(30, ana.getEdad());
        assertEquals("Sevilla", ana.getCiudad());
        assertTrue(ana.recibeNoticias());

        Usuario pepe = Usuario.builder("Pepe").build();
        assertEquals("sin-email", pepe.getEmail());          // valores por defecto
        assertEquals(18, pepe.getEdad());
        assertEquals("desconocida", pepe.getCiudad());
        assertFalse(pepe.recibeNoticias());
        assertTrue(pepe.toString().contains("Pepe"));
    }

    @Test
    @DisplayName("Builder: valida obligatorios y rangos")
    void builderValida() {
        assertThrows(IllegalArgumentException.class, () -> Usuario.builder("   "));
        assertThrows(IllegalArgumentException.class, () -> Usuario.builder(null));
        assertThrows(IllegalArgumentException.class, () -> Usuario.builder("Ana").edad(-1));
        assertThrows(IllegalArgumentException.class, () -> Usuario.builder("Ana").edad(200));
    }

    @Test
    @DisplayName("Strategy: cada estrategia da un precio distinto sin tocar el resto")
    void estrategias() {
        assertEquals(120.0, Ejercicio16_Patrones.precioFinal(120, EstrategiaDescuento.NINGUNA));
        assertEquals(108.0, Ejercicio16_Patrones.precioFinal(120, EstrategiaDescuento.SOCIOS));
        assertEquals(96.0, Ejercicio16_Patrones.precioFinal(120, EstrategiaDescuento.CANTIDAD_ALTA));
        // Cantidad alta solo actúa a partir de 100 €
        assertEquals(50.0, Ejercicio16_Patrones.precioFinal(50, EstrategiaDescuento.CANTIDAD_ALTA));
        // Redondeo a 2 decimales: 33,33 · 0,90 = 29,997 → 30,00
        assertEquals(30.0, Ejercicio16_Patrones.precioFinal(33.33, EstrategiaDescuento.SOCIOS));

        // Las tres estrategias se comportan distinto: eso es lo que las hace intercambiables
        assertNotEquals(Ejercicio16_Patrones.precioFinal(100, EstrategiaDescuento.NINGUNA),
                Ejercicio16_Patrones.precioFinal(100, EstrategiaDescuento.SOCIOS));

        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio16_Patrones.precioFinal(-1, EstrategiaDescuento.NINGUNA));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicio16_Patrones.precioFinal(10, null));
    }

    @Test
    @DisplayName("Strategy: estrategia elegida en tiempo de ejecución (lambda)")
    void estrategiaDinamica() {
        EstrategiaDescuento personalizada = p -> p - 5;       // descuento fijo
        assertEquals(95.0, Ejercicio16_Patrones.precioFinal(100, personalizada));

        double precio = 200;
        EstrategiaDescuento elegida = precio >= 100
                ? EstrategiaDescuento.CANTIDAD_ALTA
                : EstrategiaDescuento.NINGUNA;
        assertEquals(160.0, Ejercicio16_Patrones.precioFinal(precio, elegida));
    }

    @ParameterizedTest(name = "factory {0}")
    @EnumSource(TipoNotificacion.class)
    @DisplayName("Factory: crea el tipo concreto sin que el cliente haga new")
    void factory(TipoNotificacion tipo) {
        Notificacion n = NotificacionFactory.crear(tipo);

        assertEquals("[" + tipo + " a 600123456] hola", n.enviar("600123456", "hola"));
        assertFalse(NotificacionFactory.crear(tipo) == null);
    }

    @Test
    @DisplayName("Los tipos de notificación son distintos entre sí")
    void tiposDeNotificacion() {
        assertEquals(3, TipoNotificacion.values().length);
        assertEquals("EMAIL", TipoNotificacion.EMAIL.name());
        assertNotEquals(NotificacionFactory.crear(TipoNotificacion.EMAIL).getClass(),
                NotificacionFactory.crear(TipoNotificacion.SMS).getClass());
    }

    @Test
    @DisplayName("usuariosDeEjemplo() devuelve 3 usuarios de ejemplo")
    void usuariosDeEjemplo() {
        List<Usuario> usuarios = Ejercicio16_Patrones.usuariosDeEjemplo();
        assertEquals(3, usuarios.size());
        assertEquals("Ana", usuarios.get(0).getNombre());
        assertTrue(usuarios.get(0).recibeNoticias());
        assertEquals(18, usuarios.get(1).getEdad());       // valor por defecto
        assertEquals("Bilbao", usuarios.get(2).getCiudad());
    }
}
