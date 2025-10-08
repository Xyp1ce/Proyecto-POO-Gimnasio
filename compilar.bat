@echo off
REM Crear la carpeta bin si no existe
if not exist bin (
    mkdir bin
)

REM Compilar todos los archivos .java recursivamente
for /R src %%f in (*.java) do (
    javac -d bin -cp bin "%%f"
)

REM Ejecutar el programa principal
cd bin
java paquete.principal.Gimnasio
cd ..
