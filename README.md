# Sistema de Gimnasio (Proyecto Académico POO en Java)

Proyecto académico de Programación Orientada a Objetos (POO) en Java para gestionar de forma didáctica un Gimnasio usando exclusivamente diálogos con `JOptionPane`. No tiene fines comerciales ni de implementación real; su objetivo es practicar conceptos fundamentales de POO, diseño orientado a objetos e interacción simple con archivos de texto.

Importante:
- Sin interfaz gráfica avanzada (solo `JOptionPane`).
- Sin bases de datos ni frameworks.
- Sin funciones avanzadas de Java (sin lambdas, sin streams complejos, sin red).
- Persistencia mínima en archivos `.txt`.

---

## Tabla de contenido

- [Objetivos de aprendizaje](#objetivos-de-aprendizaje)
- [Alcance y no-objetivos](#alcance-y-no-objetivos)
- [Características principales](#características-principales)
- [Estructura del proyecto (directorios fijos)](#estructura-del-proyecto-directorios-fijos)
- [Modelo del dominio (resumen)](#modelo-del-dominio-resumen)
- [Flujos de uso con JOptionPane](#flujos-de-uso-con-joptionpane)
- [Persistencia mínima (archivos .txt)](#persistencia-mínima-archivos-txt)
- [Concurrencia básica](#concurrencia-básica)
- [Requisitos del sistema](#requisitos-del-sistema)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Roadmap y versionado](#roadmap-y-versionado)
- [Pruebas y datos semilla](#pruebas-y-datos-semilla)


---

## Objetivos de aprendizaje

El proyecto está diseñado para practicar, de manera aplicada al contexto de un gimnasio, los siguientes temas de POO y diseño:

- Clases, métodos y operaciones
- Objetos e instanciación
- Niveles de protección/acceso (`private`, `protected`, `public`)
- Agregación y composición
- Herencia simple
- Clases abstractas
- “Herencia múltiple” mediante interfaces (en Java)
- Polimorfismo (por herencia e interfaces)
- Cohesión alta y acoplamiento bajo
- Persistencia mínima (archivos `.txt`)
- Concurrencia básica (hilo dedicado a autoguardado)

---

## Alcance y no-objetivos

Alcance (sí incluye):
- Gestión de sucursales, clientes y personal (entrenadores, limpieza, servicio al cliente).
- Membresías y pagos asociados a clientes.
- Registro de sesiones de entrenamiento.
- Persistencia mínima de datos esenciales (personas, sucursales, sesiones) en archivos `.txt`.
- Reportes básicos para personas, membresías, pagos y sesiones.
- Guardado automático periódico mediante un hilo dedicado.

No-objetivos (no incluye):
- Interfaz gráfica avanzada (Swing complejo, JavaFX) o aplicaciones web/móviles.
- Bases de datos, frameworks, servicios de red, APIs externas.
- Uso de características avanzadas (lambdas, streams complejos, programación reactiva, etc.).

---

## Características principales

- Menú de navegación por `JOptionPane`.
- CRUD básico de clientes, personal y sucursales.
- Registro de pagos y validación de membresía activa.
- Registro de sesiones de entrenamiento (asociadas a cliente/entrenador).
- Reportes textuales (personas, membresías, pagos recientes y sesiones).
- Persistencia mínima: personas, sucursales y sesiones en `.txt`.
- Uso de interfaces (`Identificable`, `ConCosto`, `Reportable`) y del patrón plantilla (`Operacion`) en flujos clave.
- Concurrencia básica: hilo `Autoguardado` que guarda periódicamente la información.

---

## Estructura del proyecto (directorios fijos)

Se mantienen exactamente los mismos directorios: `control`, `entidades`, `interfaz`. A partir del diagrama actual, se incorporan clases adicionales para cubrir todos los temas sin crear nuevas carpetas.

Estructura esperada:

```
src/
├─ control/
│  ├─ Gimnasio.java
│  ├─ Sucursal.java
│  ├─ Persistencia.java
│  ├─ DatosSistema.java
1  ├─ Autoguardado.java
│  ├─ Operacion.java
│  ├─ OperacionRegistrarPago.java
│  └─ ServicioReportes.java
│
├─ entidades/
│  ├─ Persona.java
│  ├─ Cliente.java
│  ├─ Empleado.java
│  ├─ Entrenador.java
│  ├─ Limpieza.java
│  ├─ ServicioAlCliente.java
│  ├─ Membresia.java
│  ├─ Pago.java
│  ├─ SesionEntrenamiento.java
│  ├─ Identificable.java
│  ├─ ConCosto.java
│  └─ Reportable.java
│
└─ interfaz/
  ├─ Vista.java
  ├─ MenuPrincipal.java
  ├─ MenuSucursal.java
  ├─ MenuCliente.java
  ├─ MenuEmpleados.java
  ├─ MenuMembresias.java
  └─ MenuPersistencia.java
```

Notas:
- Los directorios no cambian; la lista agrupa las clases incluidas actualmente.
- El punto de entrada recomendado es `interfaz.Vista` (invoca al Menú Principal).

---

## Modelo del dominio (resumen)

Relaciones principales:
- `Gimnasio` 1..* `Sucursal`
- `Sucursal` 0..* `Cliente`, 0..* `Empleado`
- `Persona` (abstracta) → `Cliente`, `Empleado` (herencia simple)
- `Empleado` → `Entrenador`, `Limpieza`, `ServicioAlCliente`
- `Cliente` 0..1 `Membresia`, 0..* `Pago`, 0..* `SesionEntrenamiento`

Interfaces para “herencia múltiple”:
- `Identificable` (provee `getId()`)
- `ConCosto` (provee `getCosto()`)
- `Reportable` (provee `generarReporte()`)

---

## Flujos de uso con JOptionPane

Menú principal:
- Gestión de sucursales (alta, listado, eliminación).
- Selección de sucursal para administrar clientes y empleados.
- Gestión de clientes (altas, consultas, selección de cliente para menú individual).
- Gestión de empleados (altas, consultas, selección de empleado para menú individual).
- Módulo de membresías y pagos.
- Persistencia (guardar/cargar datos) y reportes del sistema.
- Salida del sistema.

Ejemplos de flujo:
- Alta de cliente: ingresar datos → validar → crear → confirmar.
- Registrar pago: seleccionar cliente → monto y método → validar → confirmar.
- Registrar visitas del cliente: seleccionar cliente → usar opciones de entrada/salida.
- Reportes: elegir tipo → se genera texto → se muestra en `JOptionPane` (posible `JTextArea` para listas largas).

---

## Persistencia mínima (archivos .txt)

Con fines académicos, la persistencia es intencionalmente simple y limitada. Solo se guardan:

- Personas (clientes y personal)
- Sucursales
- Sesiones de entrenamiento

Archivos:
- `data/personas.txt`
- `data/sucursales.txt`
- `data/sesiones.txt`

Convenciones:
- Separador: `|` (evitar usar `|` en nombres/descripciones).
- Formatos de línea (ejemplos):
  - Personas:
    - `CLIENTE|id|nombre|documento|telefono`
    - `EMPLEADO|id|ROL|nombre|documento|telefono` (ROL ∈ {ENTRENADOR, LIMPIEZA, SERVICIO_AL_CLIENTE})
  - Sucursales:
    - `SUCURSAL|id|nombre|direccion`
  - Sesiones:
    - `SESION|id|clienteId|entrenadorId|fechaEpochMillis|duracionMin|descripcionCorta`

Clases relacionadas:
- `control.Persistencia` centraliza `cargar...()` y `guardar...()` para los tres archivos.
- `control.DatosSistema` mantiene en memoria listas/arreglos usados por la aplicación.


---

## Concurrencia básica

- `control.Autoguardado` extiende `Thread` y ejecuta un guardado completo cada 40 segundos.
- El hilo se inicia desde `control.Gimnasio` al arrancar la aplicación y se detiene cuando el usuario solicita salir.
- Antes de cada guardado se sincronizan las sucursales en memoria para asegurar que el guardado refleje el estado actual.

---

## Requisitos del sistema

- Java SE 11 o superior (Java 8 también es viable; se recomienda 11+).
- Permisos de lectura/escritura en la carpeta `data/`.
- Sin dependencias externas.

---

## Instalación y ejecución

Opción A: Usando los scripts (si están presentes)
- Windows:
  1. Doble clic en `compilar.bat` o desde consola: `compilar.bat`
- Linux/macOS:
  1. Dar permisos: `chmod +x compilar.sh`
  2. Ejecutar: `./compilar.sh`

Opción B: Manual con `javac` y `java`
1. Compilar (ajusta el paquete si difiere):
   ```bash
   javac -d out $(find src -name "*.java")
   ```
2. Ejecutar (clase principal recomendada):
   ```bash
   java -cp out interfaz.Vista
   ```
   Nota: Si tu punto de entrada está en otra clase (por ejemplo, `control.Gimnasio`), ajusta el comando:
   ```bash
   java -cp out control.Gimnasio
   ```

Al primer inicio se crea la carpeta `data/` y archivos vacíos si no existen.

---

## Roadmap y versionado

Plan de versiones incrementales hasta cubrir todos los temas actualmente soportados:

- 1.0.1 Creación del proyecto (estructura base, bienvenida con `JOptionPane`).
- 1.0.2 Clase `Gimnasio` (núcleo del dominio) con lista de sucursales.
- 1.0.3 Clase `Sucursal` (gestión de clientes y empleados).
- 1.0.4 `Persona` (abstracta), `Cliente`, `Empleado` (herencia simple).
- 1.0.5 Subtipos de `Empleado`: `Entrenador`, `Limpieza`, `ServicioAlCliente`.
- 1.0.6 `Membresia` y `Pago` (validación de vigencia y registro de pagos).
- 1.1.0 Menú principal y submenús con `JOptionPane`.
- 1.2.0 Persistencia mínima: `Persistencia` + `DatosSistema` (personas, sucursales, sesiones en `.txt`).
- 1.3.0 Interfaces (`Identificable`, `ConCosto`, `Reportable`) para polimorfismo por interfaz.
- 1.3.1 Clases abstractas y método plantilla (`Operacion` + `OperacionRegistrarPago`).
- 1.3.2 Polimorfismo en servicios y reportes (`ServicioReportes`).
- 1.4.0 Concurrencia básica: `Autoguardado` realiza persistencia periódica cada 40 segundos.

Consulta `Roadmap.md` para la descripción detallada.

---

## Pruebas y datos semilla

Datos semilla sugeridos (para pruebas manuales):
- 2 sucursales
- 5 clientes (distintas membresías; uno con pago vencido)
- 3 empleados (1 entrenador, 1 limpieza, 1 servicio al cliente)
- 6 pagos de ejemplo
- 4 sesiones de entrenamiento (vinculando clientes con entrenador)

Pruebas fundamentales:
- Altas/bajas/modificaciones por menú.
- Flujos fin a fin: registro de clientes, registro de empleados, asignación de membresía y registro de pago.
- Persistencia: cargar al iniciar, guardar al salir; verificar contenido de `data/*.txt`.
- Autoguardado: mantener la aplicación abierta por más de 40 segundos y comprobar que los archivos en `data/` cambian su marca de tiempo.

---