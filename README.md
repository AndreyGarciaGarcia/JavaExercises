# 📚 JavaExercises — Ejercicios de Java

[![CI](https://github.com/AndreyGarciaGarcia/JavaExercises/actions/workflows/ci.yml/badge.svg)](https://github.com/AndreyGarciaGarcia/JavaExercises/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/Java-21%20%7C%2025-orange)](https://openjdk.org/projects/jdk/21/)
[![JUnit](https://img.shields.io/badge/JUnit-5.11-green)](https://junit.org/junit5/)
[![tests](https://img.shields.io/badge/tests-149%20correctos-brightgreen)](#-tests)
[![license](https://img.shields.io/badge/licencia-MIT-blue)](LICENSE)

Proyecto **Maven** con los ejercicios de Java organizados por bloques temáticos.
Cada ejercicio tiene su `main` y se puede ejecutar de forma independiente.

> 📄 Apuntes teóricos: [`docs/Java-Apuntes-Completos.pdf`](docs/Java-Apuntes-Completos.pdf) — 57 páginas, 20 capítulos (fundamentos → concurrencia) con chuleta final.

---

## 🗂 Estructura

```
JavaExercises/
├── pom.xml                      ← Maven: Java 21 + JUnit 5 + H2
├── run.bat                      ← compilar y ejecutar SIN Maven
├── test.bat                     ← ejecutar los tests SIN Maven (auto-descarga)
├── LICENSE                      ← MIT
├── .gitignore
├── README.md
├── .gitattributes               ← LF en el repo, CRLF solo en .bat
├── .github/workflows/ci.yml     ← CI: Java 21 + 25 en cada push
├── docs/
│   └── Java-Apuntes-Completos.pdf
├── lib/                         ← JUnit y H2 (descargados, no versionados)
└── src
    ├── main
    │   ├── java/com/curso
    │   │   ├── App.java                 ← menú principal (punto de entrada)
    │   │   ├── util/
    │   │   │   └── Teclado.java          ← lectura por teclado (Scanner envuelto)
    │   │   └── ejercicios/               ← ⬅ PACKAGE DE EJERCICIOS (20 ejercicios)
    │   │       ├── fundamentos/    01 Saludo · 02 Tipos y conversiones
    │   │       ├── flujo/          03 FizzBuzz (if, for, switch)
    │   │       ├── arrays/         04 Arrays y String
    │   │       ├── poo/            05 CuentaBancaria + SaldoInsuficienteException
    │   │       ├── colecciones/    06 Conteo de palabras (Map)
    │   │       ├── streams/        07 Lambdas y Streams
    │   │       ├── excepciones/    08 try/catch y errores propios
    │   │       ├── retos/          09 Máquina expendedora · 19 Biblioteca · 20 Ahorcado
    │   │       ├── fechas/         10 java.time
    │   │       ├── ficheros/       11 java.nio (Files/Path)
    │   │       ├── concurrencia/   12 Hilos, ExecutorService, hilos virtuales
    │   │       ├── herencia/       13 extends, interfaces, polimorfismo
    │   │       ├── enums/          14 enum, record, sealed
    │   │       ├── genericos/      15 <T> y PECS
    │   │       ├── patrones/       16 Builder, Strategy, Factory
    │   │       ├── jdbc/           17 JDBC con H2 en memoria
    │   │       └── examenes/       18 RETO · Cuestionario autocorregible
    │   └── resources/
    └── test
        ├── java/com/curso/ejercicios
        │   ├── fundamentos/SaludoTest.java               (10 tests)
        │   ├── fundamentos/TiposYConversionesTest.java   (10 tests)
        │   ├── flujo/FizzBuzzTest.java                   (16 tests)
        │   ├── arrays/ArraysYStringsTest.java            (6 tests)
        │   ├── poo/CuentaBancariaTest.java               (7 tests)
        │   ├── colecciones/ConteoPalabrasTest.java       (5 tests)
        │   ├── streams/StreamsTest.java                  (5 tests)
        │   ├── fechas/FechasTest.java                    (7 tests)
        │   ├── ficheros/FicherosTest.java                (4 tests)
        │   ├── concurrencia/ConcurrenciaTest.java        (5 tests)
        │   ├── herencia/JerarquiaAnimalesTest.java       (7 tests)
        │   ├── enums/EnumsYRecordsTest.java              (16 tests)
        │   ├── genericos/GenericosTest.java              (6 tests)
        │   ├── patrones/PatronesTest.java                (9 tests)
        │   ├── jdbc/JdbcTest.java                        (6 tests)
        │   ├── examenes/CuestionarioTest.java            (8 tests)
        │   └── retos/MaquinaExpendedora/Biblioteca/Ahorcado (5+6+11 tests)
        └── resources/
```

> **19 clases de test · 149 tests · los 20 ejercicios tienen cobertura**
> La CI de GitHub Actions los ejecuta en **Java 21 y 25** en cada push.

**Paquete de ejercicios:** `com.curso.ejercicios`
(ruta: `src/main/java/com/curso/ejercicios/`)

---

## 📖 Índice de los 20 ejercicios

| # | Tema | Clase | Paquete |
|---|---|---|---|
| 01 | Saludo y primeros pasos | `Ejercicio01_Saludo` | `fundamentos` |
| 02 | Tipos y conversiones | `Ejercicio02_TiposYConversiones` | `fundamentos` |
| 03 | Control de flujo (FizzBuzz) | `Ejercicio03_FizzBuzz` | `flujo` |
| 04 | Arrays y String | `Ejercicio04_ArraysYStrings` | `arrays` |
| 05 | POO (cuenta bancaria) | `Ejercicio05_CuentaBancaria` | `poo` |
| 06 | Colecciones (conteo de palabras) | `Ejercicio06_ConteoPalabras` | `colecciones` |
| 07 | Lambdas y Streams | `Ejercicio07_Streams` | `streams` |
| 08 | Excepciones | `Ejercicio08_Excepciones` | `excepciones` |
| 09 | 🏆 Máquina expendedora | `Ejercicio09_MaquinaExpendedora` | `retos` |
| 10 | Fecha y hora | `Ejercicio10_FechasYTiempo` | `fechas` |
| 11 | Ficheros | `Ejercicio11_Ficheros` | `ficheros` |
| 12 | Concurrencia | `Ejercicio12_Concurrencia` | `concurrencia` |
| 13 | Herencia y polimorfismo | `Ejercicio13_JerarquiaAnimales` | `herencia` |
| 14 | Enums, records y sealed | `Ejercicio14_EnumsYRecords` | `enums` |
| 15 | Genéricos y PECS | `Ejercicio15_Genericos` | `genericos` |
| 16 | Patrones (Builder/Strategy/Factory) | `Ejercicio16_Patrones` | `patrones` |
| 17 | JDBC con H2 | `Ejercicio17_Jdbc` | `jdbc` |
| 18 | 🏆 Cuestionario autocorregible | `Ejercicio18_Cuestionario` | `examenes` |
| 19 | 🏆 Gestión de biblioteca | `Ejercicio19_GestionBiblioteca` | `retos` |
| 20 | 🏆 Ahorcado | `Ejercicio20_Ahorcado` | `retos` |

Lanzar cualquiera: `run.bat com.curso.ejercicios.<paquete>.<Clase>` o el menú (`run.bat`).

---

## ▶ Cómo ejecutar

### Sin Maven (lo más rápido)
| Opción | Comando |
|---|---|
| Menú principal | **`run.bat`** |
| Un ejercicio suelto | `run.bat com.curso.ejercicios.flujo.Ejercicio03_FizzBuzz` |
| Tests JUnit 5 | **`test.bat`** — si faltan los JAR de `lib/` **los descarga solo** (JUnit y H2) |

### Con Maven
| Opción | Comando |
|---|---|
| Menú principal | `mvn compile exec:java` |
| Tests | `mvn test` |
| JAR ejecutable | `mvn package` → `java -jar target/java-exercises-1.0.0.jar` |

### 🧪 Tests
149 tests en 19 clases, cubriendo los **20 ejercicios**:
`fundamentos` · `flujo` · `arrays` · `poo` · `colecciones` · `streams` ·
`excepciones` · `retos` · `fechas` · `ficheros` · `concurrencia` ·
`herencia` · `enums` · `genericos` · `patrones` · `jdbc` · `examenes`.

Cada test sigue el patrón **AAA** (Arrange-Act-Assert) y usa `@DisplayName`
en castellano para que el informe se lea sin traducir. Los del paquete `jdbc`
se **omiten automáticamente** si no está el driver H2 (`Assumptions`).

### Integración continua
En cada `push` a `main` (y en cada *pull request*), **GitHub Actions** compila el
proyecto y ejecuta los 149 tests con **Java 21** y **Java 25**, y sube el informe de
surefire como artefacto. Workflow: [`.github/workflows/ci.yml`](.github/workflows/ci.yml).

### Con IntelliJ IDEA
`File → Open →` selecciona esta carpeta → botón ▶ del `main` que quieras.
IntelliJ detecta el `pom.xml` y usa su Maven embebido (`Settings → Build Tools → Maven`).

### Compilar a mano
```bash
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java  -cp out com.curso.App
```

### 🖥 Consola Windows (acentos y símbolos)
El código está en **UTF-8**. Si ejecutas desde `cmd`/`PowerShell` y ves `?` en lugar de `á · → ✗`:
```bat
chcp 65001
java -Dstdout.encoding=UTF-8 -cp out com.curso.App
```
`run.bat` y `test.bat` ya añaden esa opción. En IntelliJ no hace falta.

---

## ➕ Cómo añadir un ejercicio nuevo

1. Crea el fichero en el paquete correspondiente:
   `src/main/java/com/curso/ejercicios/<tema>/Ejercicio21_MiTema.java`
2. Copia esta plantilla:

```java
package com.curso.ejercicios.<tema>;

/**
 * EJERCICIO 21 · <Tema> — <Título>
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>...</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * ...
 */
public final class Ejercicio21_MiTema {

    private Ejercicio21_MiTema() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 21 · <Título> ──");
        // tu código aquí
    }

    /** Lógica en un método estático puro → así se puede testear. */
    public static int metodoProbable(int x) {
        return x * 2;
    }
}
```

3. Añádelo al menú de `App.java`:
   `case 21 -> ejecutar(() -> Ejercicio21_MiTema.main(new String[0]));`
4. (Recomendado) Añade su test en `src/test/java/com/curso/ejercicios/<tema>/`.

---

## ✅ Convenciones

- **Nombres:** clases en `PascalCase` (`Ejercicio04_ArraysYStrings`), métodos y variables en `camelCase`.
- **Javadoc** en cada ejercicio: enunciado + conceptos que practica.
- **Lógica en métodos `static` puros** y el `main` solo orquesta → se pueden testear.
- **Valida al entrar**, lanza excepciones con mensaje claro y captura en la capa que las entienda.
- **Un test por comportamiento**, con nombre descriptivo y patrón AAA (Arrange-Act-Assert).
- **Independiente del locale**: nada de `toLowerCase()` ni formatos de fecha "a pelo";
  se usa `Locale.ROOT` / `Locale.of(...)`. Así los tests pasan en español **y** en inglés
  (el entorno por defecto de la CI de Linux).
- **Encoding UTF-8** en todo (configurado en el `pom.xml` y en los `.bat`).
