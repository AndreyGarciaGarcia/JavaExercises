package com.curso.ejercicios.fechas;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * EJERCICIO 10 · Fecha y hora (java.time)
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>Dada una fecha de nacimiento, calcula la edad exacta y los días vividos.</li>
 *   <li>Cuenta los días que faltan hasta una fecha dada.</li>
 *   <li>Comprueba si un año es bisiesto.</li>
 *   <li>Muestra el próximo lunes y el número de la semana ISO.</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * {@code LocalDate}, {@code LocalDateTime}, {@code Period}/{@code Duration},
 * {@code DateTimeFormatter}, inmutabilidad de la API de fechas.
 */
public final class Ejercicio10_FechasYTiempo {

    private Ejercicio10_FechasYTiempo() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 10 · Fecha y hora ───────────────────");

        LocalDate hoy = LocalDate.now();
        System.out.println("Hoy                     : " + fechaLegible(hoy));
        System.out.println("Día de la semana        : " + diaDeLaSemana(hoy));
        System.out.println("Semana ISO              : " + hoy.get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear()));

        LocalDate nacimiento = LocalDate.parse("2000-06-15");
        System.out.println("Nacimiento              : " + fechaLegible(nacimiento));
        System.out.println("Edad (años)             : " + edadEnAnios(nacimiento, hoy));
        System.out.println("Días vividos            : " + diasEntre(nacimiento, hoy));
        System.out.println("¿2024 bisiesto?         : " + esBisiesto(2024));
        System.out.println("¿2023 bisiesto?         : " + esBisiesto(2023));

        LocalDate objetivo = hoy.plusDays(30);
        System.out.println("Faltan hasta " + fechaLegible(objetivo) + " : " + diasEntre(hoy, objetivo) + " días");

        Duration hastaFinDeAno = Duration.between(LocalDateTime.now(),
                hoy.withMonth(12).withDayOfMonth(31).plusDays(1).atStartOfDay());
        System.out.println("Hasta terminar el año   : " + hastaFinDeAno.toHours() + " horas");

        System.out.println("Próximo lunes           : " + proximoLunes(hoy));
        System.out.println("Formato corto           : " + hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Formato largo           : " + hoy.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")));
    }

    /** Edad cumplida en años entre una fecha de nacimiento y otra. */
    public static long edadEnAnios(LocalDate nacimiento, LocalDate referencia) {
        if (nacimiento.isAfter(referencia)) {
            throw new IllegalArgumentException("la fecha de nacimiento es posterior a la de referencia");
        }
        return ChronoUnit.YEARS.between(nacimiento, referencia);
    }

    /** Número de días completos entre dos fechas (la segunda menos la primera). */
    public static long diasEntre(LocalDate desde, LocalDate hasta) {
        return ChronoUnit.DAYS.between(desde, hasta);
    }

    /** Regla oficial: múltiplo de 4, salvo siglos no múltiplos de 400. */
    public static boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
    }

    /** Primer lunes estrictamente posterior a la fecha dada. */
    public static LocalDate proximoLunes(LocalDate fecha) {
        LocalDate candidato = fecha.plusDays(1);
        while (candidato.getDayOfWeek() != DayOfWeek.MONDAY) {
            candidato = candidato.plusDays(1);
        }
        return candidato;
    }

    /** "15 de junio de 2000" (capitaliza el día de la semana si aparece). */
    public static String fechaLegible(LocalDate fecha) {
        String texto = fecha.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy"));
        return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
    }

    /** "lunes", "martes"... en minúsculas. */
    public static String diaDeLaSemana(LocalDate fecha) {
        return fecha.getDayOfWeek().getDisplayName(
                java.time.format.TextStyle.FULL, java.util.Locale.of("es", "ES"));
    }
}
