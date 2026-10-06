# 📚 JavaExercises — Ejercicios de Java

Proyecto **Maven** con los ejercicios de Java organizados por bloques temáticos.
Cada ejercicio tiene su `main` y se puede ejecutar de forma independiente.

> 📄 Apuntes teóricos: [`docs/Java-Apuntes-Completos.pdf`](docs/Java-Apuntes-Completos.pdf) — 57 páginas, 20 capítulos (fundamentos → concurrencia) con chuleta final.

---

## 🗂 Estructura

```
JavaExercises/
├── pom.xml                      ← Maven: Java 21 + JUnit 5
├── run.bat                      ← compilar y ejecutar SIN Maven
├── test.bat                     ← ejecutar los tests SIN Maven
├── .gitignore
├── README.md
├── docs/
│   └── Java-Apuntes-Completos.pdf
├── lib/                         ← JUnit standalone (descargado, no versionado)
└── src
    ├── main
    │   ├── java/com/curso
    │   │   ├── App.java                 ← menú principal (punto de entrada)
    │   │   ├── util/
    │   │   │   └── Teclado.java          ← lectura por teclado (Scanner envuelto)
    │   │   └── ejercicios/               ← ⬅ PACKAGE DE EJERCICIOS
    │   │       ├── fundamentos/    01 Saludo · 02 Tipos y conversiones
    │   │       ├── flujo/          03 FizzBuzz (if, for, switch)
    │   │       ├── arrays/         04 Arrays y String
    │   │       ├── poo/            05 CuentaBancaria + SaldoInsuficienteException
    │   │       ├── colecciones/    06 Conteo de palabras (Map)
    │   │       ├── streams/        07 Lambdas y Streams
    │   │       ├── excepciones/    08 try/catch y errores propios
    │   │       ├── retos/          09 RETO · Máquina expendedora
    │   │       ├── fechas/         10 java.time
    │   │       ├── ficheros/       11 java.nio (Files/Path)
    │   │       └── concurrencia/   12 Hilos, ExecutorService, hilos virtuales
    │   └── resources/
    └── test
        ├── java/com/curso/ejercicios
        │   ├── poo/CuentaBancariaTest.java          (7 tests)
        │   ├── arrays/ArraysYStringsTest.java       (6 tests)
        │   ├── streams/StreamsTest.java             (5 tests)
        │   ├── fechas/FechasTest.java               (6 tests)
        │   ├── ficheros/FicherosTest.java           (4 tests)
        │   └── retos/MaquinaExpendedoraTest.java    (5 tests)
        └── resources/
```

**Paquete de ejercicios:** `com.curso.ejercicios`
(ruta: `src/main/java/com/curso/ejercicios/`)

---

## ▶ Cómo ejecutar

### Sin Maven (lo más rápido)
| Opción | Comando |
|---|---|
| Menú principal | **`run.bat`** |
| Un ejercicio suelto | `run.bat com.curso.ejercicios.flujo.Ejercicio03_FizzBuzz` |
| Tests JUnit 5 | **`test.bat`** (requiere `lib/junit-platform-console-standalone.jar`) |

### Con Maven
| Opción | Comando |
|---|---|
| Menú principal | `mvn compile exec:java` |
| Tests | `mvn test` |
| JAR ejecutable | `mvn package` → `java -jar target/java-exercises-1.0.0.jar` |

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
   `src/main/java/com/curso/ejercicios/<tema>/Ejercicio13_MiTema.java`
2. Copia esta plantilla:

```java
package com.curso.ejercicios.<tema>;

/**
 * EJERCICIO 13 · <Tema> — <Título>
 *
 * <h2>Enunciado</h2>
 * <ol>
 *   <li>...</li>
 * </ol>
 *
 * <h2>Conceptos</h2>
 * ...
 */
public final class Ejercicio13_MiTema {

    private Ejercicio13_MiTema() {
    }

    public static void main(String[] args) {
        System.out.println("\n── Ejercicio 13 · <Título> ──");
        // tu código aquí
    }

    /** Lógica en un método estático puro → así se puede testear. */
    public static int metodoProbable(int x) {
        return x * 2;
    }
}
```

3. Añádelo al menú de `App.java`:
   `case 13 -> ejecutar(() -> Ejercicio13_MiTema.main(new String[0]));`
4. (Recomendado) Añade su test en `src/test/java/com/curso/ejercicios/<tema>/`.

---

## ✅ Convenciones

- **Nombres:** clases en `PascalCase` (`Ejercicio04_ArraysYStrings`), métodos y variables en `camelCase`.
- **Javadoc** en cada ejercicio: enunciado + conceptos que practica.
- **Lógica en métodos `static` puros** y el `main` solo orquesta → se pueden testear.
- **Valida al entrar**, lanza excepciones con mensaje claro y captura en la capa que las entienda.
- **Un test por comportamiento**, con nombre descriptivo y patrón AAA (Arrange-Act-Assert).
- **Encoding UTF-8** en todo (configurado en el `pom.xml` y en los `.bat`).
