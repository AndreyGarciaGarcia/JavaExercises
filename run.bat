@echo off
rem ============================================================
rem  run.bat · compila y ejecuta JavaExercises SIN Maven
rem
rem  Uso:
rem    run.bat                                   → menú principal
rem    run.bat com.curso.ejercicios.flujo.Ejercicio03_FizzBuzz
rem ============================================================
setlocal
cd /d "%~dp0"

set "OUT=out"
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
    java -Dstdout.encoding=UTF-8 -cp "%OUT%" com.curso.App
) else (
    java -Dstdout.encoding=UTF-8 -cp "%OUT%" %~1
)

endlocal
