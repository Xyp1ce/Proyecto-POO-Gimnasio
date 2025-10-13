@echo off
REM Crear la carpeta bin si no existe
if not exist bin (
    mkdir bin
)

REM Compilar todos los archivos .java desde src, respetando la estructura de paquetes
REM Compilar todos los archivos .java desde src, respetando la estructura de paquetes
REM Forzar compatibilidad con Java 21 para que las clases funcionen con el runtime instalado
javac --release 21 -d bin -sourcepath src src/control/*.java src/entidades/*.java src/interfaz/*.java
echo.
echo Compilacion finalizada.

REM Ejecutar el programa principal
java -cp bin control.Gimnasio
echo.
echo Ejecución finalizada.
