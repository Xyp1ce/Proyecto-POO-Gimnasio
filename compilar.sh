#!/bin/bash
# Crear la carpeta bin si no existe
if [ ! -d "bin" ]; then
    mkdir bin
fi

# Compilar todos los archivos .java recursivamente
tmpfiles=$(find src -name "*.java")
javac -d bin -cp bin $tmpfiles

# Ejecutar el programa principal
cd bin
java paquete.principal.Gimnasio
cd ..

