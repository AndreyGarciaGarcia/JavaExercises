@echo off
rem ============================================================
rem  test.bat · compila y ejecuta los tests JUnit 5 SIN Maven
rem
rem  Si falta lib\junit-platform-console-standalone.jar lo descarga
rem  automaticamente la primera vez (necesita internet).
rem  Alternativa manual:
rem    curl -L -o lib\junit-platform-console-standalone.jar ^
rem      https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.3/junit-platform-console-standalone-1.11.3.jar
rem ============================================================
setlocal
cd /d "%~dp0"

set "JUNIT=lib\junit-platform-console-standalone.jar"
set "JUNIT_URL=https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.3/junit-platform-console-standalone-1.11.3.jar"
set "OUT=out"
set "OUTTEST=out-test"

rem ---------- 0/3 · dependencia ----------
if not exist "%JUNIT%" (
    echo [0/3] JUnit no encontrado: descargando por primera vez...
    if not exist lib mkdir lib
    curl -L --fail --silent --show-error -o "%JUNIT%.tmp" "%JUNIT_URL%"
    if errorlevel 1 (
        echo.
        echo ERROR: no se pudo descargar JUnit. Comprueba tu conexion o descargalo a mano
        echo        con el comando curl de la cabecera de este script.
        if exist "%JUNIT%.tmp" del "%JUNIT%.tmp"
        exit /b 1
    )
    move /Y "%JUNIT%.tmp" "%JUNIT%" >nul
    echo       OK: %JUNIT%
)

if not exist "%OUT%" mkdir "%OUT%"
if not exist "%OUTTEST%" mkdir "%OUTTEST%"

rem ---------- 1/3 · codigo principal ----------
echo [1/3] Compilando codigo principal...
dir /s /b src\main\java\*.java > "%OUT%\sources.txt"
javac -encoding UTF-8 -d "%OUT%" @"%OUT%\sources.txt"
set "ERR=%errorlevel%"
del "%OUT%\sources.txt" >nul 2>&1
if not "%ERR%"=="0" (
    echo ERROR compilando el codigo principal.
    exit /b 1
)

rem ---------- 2/3 · tests ----------
echo [2/3] Compilando tests...
dir /s /b src\test\java\*.java > "%OUT%\tests.txt"
javac -encoding UTF-8 -cp "%OUT%;%JUNIT%" -d "%OUTTEST%" @"%OUT%\tests.txt"
set "ERR=%errorlevel%"
del "%OUT%\tests.txt" >nul 2>&1
if not "%ERR%"=="0" (
    echo ERROR compilando los tests.
    exit /b 1
)

rem ---------- 3/3 · ejecucion ----------
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
