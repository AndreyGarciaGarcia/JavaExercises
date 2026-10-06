@echo off
rem ============================================================
rem  test.bat · compila y ejecuta los tests JUnit 5 SIN Maven
rem
rem  Descarga previa (solo la primera vez):
rem    curl -L -o lib\junit-platform-console-standalone.jar ^
rem      https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.3/junit-platform-console-standalone-1.11.3.jar
rem ============================================================
setlocal
cd /d "%~dp0"

set "JUNIT=lib\junit-platform-console-standalone.jar"
set "OUT=out"
set "OUTTEST=out-test"

if not exist "%JUNIT%" (
    echo No encuentro %JUNIT%
    echo Descargalo con el comando curl de la cabecera de este script.
    exit /b 1
)

if not exist "%OUT%" mkdir "%OUT%"
if not exist "%OUTTEST%" mkdir "%OUTTEST%"

echo [1/3] Compilando codigo principal...
dir /s /b src\main\java\*.java > "%OUT%\sources.txt"
javac -encoding UTF-8 -d "%OUT%" @"%OUT%\sources.txt"
set "ERR=%errorlevel%"
del "%OUT%\sources.txt" >nul 2>&1
if not "%ERR%"=="0" (
    echo ERROR compilando el codigo principal.
    exit /b 1
)

echo [2/3] Compilando tests...
dir /s /b src\test\java\*.java > "%OUT%\tests.txt"
javac -encoding UTF-8 -cp "%OUT%;%JUNIT%" -d "%OUTTEST%" @"%OUT%\tests.txt"
set "ERR=%errorlevel%"
del "%OUT%\tests.txt" >nul 2>&1
if not "%ERR%"=="0" (
    echo ERROR compilando los tests.
    exit /b 1
)

echo [3/3] Ejecutando JUnit 5...
echo.
java -Dstdout.encoding=UTF-8 -jar "%JUNIT%" execute ^
    --class-path "%OUT%;%OUTTEST%;src\main\resources;src\test\resources" ^
    --scan-class-path "%OUTTEST%" ^
    --disable-banner ^
    --details=tree

set "RESULTADO=%errorlevel%"
echo.
if "%RESULTADO%"=="0" (echo TODOS LOS TESTS CORRECTOS) else (echo HAY TESTS FALLIDOS -^> codigo %RESULTADO%)
endlocal & exit /b %RESULTADO%
