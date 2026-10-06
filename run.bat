@echo off
rem ============================================================
rem  run.bat · compila y ejecuta JavaExercises SIN Maven
rem
rem  Uso:
rem    run.bat                                   → menú principal
rem    run.bat com.curso.ejercicios.flujo.Ejercicio03_FizzBuzz
rem
rem  Si falta lib\h2-2.3.232.jar lo descarga solo (solo lo usa
rem  el ejercicio 17, de JDBC; el resto no lo necesita).
rem ============================================================
setlocal
cd /d "%~dp0"

set "OUT=out"
set "H2=lib\h2-2.3.232.jar"
set "H2_URL=https://repo1.maven.org/maven2/com/h2database/h2/2.3.232/h2-2.3.232.jar"
set "CP=%OUT%;lib\h2-2.3.232.jar"

rem ---------- dependencia opcional (ejercicio 17) ----------
if not exist "%H2%" (
    echo [0/2] Driver H2 no encontrado: descargando...
    if not exist lib mkdir lib
    curl -L --fail --silent --show-error -o "%H2%.tmp" "%H2_URL%"
    if errorlevel 1 (
        echo       Aviso: sin H2 el ejercicio 17 no podra conectarse a la BD.
    ) else (
        move /Y "%H2%.tmp" "%H2%" >nul
        echo       OK: %H2%
    )
    if exist "%H2%.tmp" del "%H2%.tmp" >nul 2>&1
)

if not exist "%OUT%" mkdir "%OUT%"

echo [1/2] Compilando fuentes principales...
dir /s /b src\main\java\*.java > "%OUT%\sources.txt"
javac -encoding UTF-8 -d "%OUT%" @"%OUT%\sources.txt"
set "ERR=%errorlevel%"
del "%OUT%\sources.txt" >nul 2>&1

if not "%ERR%"=="0" (
    echo.
    echo ERROR: la compilacion ha fallado. Revisa los mensajes de arriba.
    exit /b 1
)

echo [2/2] Ejecutando...
echo.
if "%~1"=="" (
    java -Dstdout.encoding=UTF-8 -cp "%CP%" com.curso.App
) else (
    java -Dstdout.encoding=UTF-8 -cp "%CP%" %~1
)

endlocal
