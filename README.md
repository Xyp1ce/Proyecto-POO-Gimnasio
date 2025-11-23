# Sistema de Gimnasio (Proyecto Académico POO en Java)

Proyecto académico de Programación Orientada a Objetos (POO) en Java para gestionar de forma didáctica un Gimnasio usando exclusivamente diálogos con `JOptionPane`. No tiene fines comerciales ni de implementación real; su objetivo es practicar conceptos fundamentales de POO, diseño orientado a objetos, persistencia mínima con archivos de texto y concurrencia básica.

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
- Concurrencia básica (`Thread`, `synchronized`)

---

## Alcance y no-objetivos

Alcance (sí incluye):
- Gestión de sucursales, clientes y personal (entrenadores, limpieza, servicio al cliente).
- Membresías, pagos, clases grupales e inscripciones.
- Planes de entrenamiento, rutinas, ejercicios y sesiones de entrenamiento.
- Persistencia mínima de datos esenciales (personas, sucursales, sesiones) en archivos `.txt`.
- Concurrencia básica para auto-guardado y cola de accesos.

No-objetivos (no incluye):
- Interfaz gráfica avanzada (Swing complejo, JavaFX) o aplicaciones web/móviles.
- Bases de datos, frameworks, servicios de red, APIs externas.
- Uso de características avanzadas (lambdas, streams complejos, programación reactiva, etc.).

---

## Características principales

- Menú de navegación por `JOptionPane`.
- CRUD básico de clientes, personal y sucursales.
- Registro de pagos y validación de membresía activa.
- Clases grupales con cupo, inscripción y cancelación.
- Planes de entrenamiento con rutinas y ejercicios.
- Registro de sesiones de entrenamiento (asociadas a cliente/entrenador).
- Reportes textuales (morosidad, ocupación de clases, inventario, ingresos, etc.).
- Persistencia mínima: personas, sucursales y sesiones en `.txt`.
- Concurrencia básica: auto-guardado periódico y cola sincronizada de accesos.

---

## Estructura del proyecto (directorios fijos)

Se mantienen exactamente los mismos directorios: `control`, `entidades`, `interfaz`. A partir del diagrama actual, se incorporan clases adicionales para cubrir todos los temas sin crear nuevas carpetas.

Estructura esperada:

```
src/
├─ control/
│  ├─ Gimnasio.java                 (existe)
│  ├─ Sucursal.java                 (existe)
│  ├─ PersistenciaBasica.java       (nuevo; persistencia mínima .txt)
│  ├─ DatosSistema.java             (nuevo; contenedor en memoria)
│  ├─ AutoGuardadoThread.java       (nuevo; concurrencia)
│  ├─ ColaAccesos.java              (nuevo; concurrencia)
│  ├─ Operacion.java                (nuevo; abstracta, patrón plantilla)
│  ├─ OperacionRegistrarPago.java   (nuevo)
│  ├─ OperacionInscribirClase.java  (nuevo)
│  ├─ ServicioPagos.java            (nuevo)
│  ├─ ServicioClases.java           (nuevo)
│  └─ ServicioReportes.java         (nuevo)
│
├─ entidades/
│  ├─ Cliente.java                  (existe)
│  ├─ Empleado.java                 (existe)
│  ├─ Entrenador.java               (existe)
│  ├─ Limpieza.java                 (existe)
│  ├─ ServicioAlCliente.java        (existe)
│  ├─ Persona.java                  (nuevo; abstracta, base)
│  ├─ Membresia.java                (nuevo)
│  ├─ Pago.java                     (nuevo)
│  ├─ ClaseGrupal.java              (nuevo)
│  ├─ Equipo.java                   (nuevo)
│  ├─ RegistroMantenimiento.java    (nuevo)
│  ├─ PlanEntrenamiento.java        (nuevo)
│  ├─ Rutina.java                   (nuevo)
│  ├─ Ejercicio.java                (nuevo)
│  ├─ RegistroAcceso.java           (nuevo)
│  ├─ SesionEntrenamiento.java      (nuevo)
│  ├─ Identificable.java            (nuevo; interface)
│  ├─ ConCosto.java                 (nuevo; interface)
│  └─ Reportable.java               (nuevo; interface)
│
└─ interfaz/
   ├─ Vista.java                    (existe; punto de interacción con JOptionPane)
   ├─ MenuPrincipal.java            (nuevo)
   ├─ MenuClientes.java             (nuevo)
   ├─ MenuEmpleados.java            (nuevo)
   ├─ MenuClases.java               (nuevo)
   ├─ MenuEquipos.java              (nuevo)
   └─ MenuReportes.java             (nuevo)
```

Notas:
- Los directorios no cambian; solo se agregan clases para abordar todos los temas.
- El punto de entrada recomendado es `interfaz.Vista` (invoca al Menú Principal).

---

## Modelo del dominio (resumen)

Relaciones principales:
- `Gimnasio` 1..* `Sucursal`
- `Sucursal` 0..* `Cliente`, 0..* `Empleado`, 0..* `Equipo`, 0..* `ClaseGrupal`
- `Persona` (abstracta) → `Cliente`, `Empleado` (herencia simple)
- `Empleado` → `Entrenador`, `Limpieza`, `ServicioAlCliente`
- `Cliente` 0..1 `Membresia`, 0..* `Pago`, 0..1 `PlanEntrenamiento`, 0..* `RegistroAcceso`
- `PlanEntrenamiento` 1..* `Rutina` y `Rutina` 1..* `Ejercicio` (composición fuerte)
- `ClaseGrupal` 0..* `Cliente` (inscritos), con `cupo` y `horario`
- `Equipo` 0..* `RegistroMantenimiento`
- `SesionEntrenamiento` vincula `Cliente` con `Entrenador` y fecha/duración

Interfaces para “herencia múltiple”:
- `Identificable` (provee `getId()`)
- `ConCosto` (provee `getCosto()`)
- `Reportable` (provee `generarTexto()`)

---

## Flujos de uso con JOptionPane

Menú principal:
- Sucursales
- Clientes
- Empleados
- Membresías y Pagos
- Clases grupales
- Equipos y Mantenimiento
- Planes de entrenamiento
- Sesiones de entrenamiento
- Reportes
- Cargar/Guardar
- Salir

Ejemplos de flujo:
- Alta de cliente: ingresar datos → validar → crear → confirmar.
- Registrar pago: seleccionar cliente → monto y método → validar → confirmar.
- Inscribir a clase: seleccionar clase → seleccionar cliente → validar cupo y membresía → inscribir.
- Asignar plan: crear plan con rutinas/ejercicios → asignar a cliente → mostrar resumen.
- Registrar sesión: seleccionar cliente y entrenador → fecha y duración → guardar.
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
- `control.PersistenciaBasica` centraliza `cargar...()` y `guardar...()` para los tres archivos.
- `control.DatosSistema` mantiene en memoria listas/arreglos usados por la aplicación.
- `AutoGuardadoThread` (opcional) realiza guardados periódicos.


---

## Concurrencia básica

- `AutoGuardadoThread`: realiza guardados periódicos a archivos `.txt`.
- `ColaAccesos`: estructura sincronizada para simular múltiples registros de acceso (check-ins).
- Sincronización con `synchronized` en operaciones de lectura/escritura de `PersistenciaBasica` y acceso a `DatosSistema`.
- Objetivo: demostrar conceptos de concurrencia y evitar condiciones de carrera de forma simple.

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

Plan de versiones incrementales hasta cubrir todos los temas:

- 1.0.1 Creación del proyecto (estructura base, bienvenida con `JOptionPane`).
- 1.0.2 Clase `Gimnasio` (núcleo del dominio) con lista de sucursales.
- 1.0.3 Clase `Sucursal` (agrega clientes, empleados, equipos, clases).
- 1.0.4 `Persona` (abstracta), `Cliente`, `Empleado` (herencia simple).
- 1.0.5 Subtipos de `Empleado`: `Entrenador`, `Limpieza`, `ServicioAlCliente`.
- 1.0.6 `Membresia` y `Pago` (validación de vigencia y registro de pagos).
- 1.0.7 `Equipo` y `RegistroMantenimiento` (inventario y mantenimiento).
- 1.0.8 `ClaseGrupal` (cupos e inscripciones).
- 1.1.0 `PlanEntrenamiento`, `Rutina`, `Ejercicio` (composición fuerte).
- 1.2.0 Menú principal y submenús con `JOptionPane`.
- 1.3.0 Persistencia mínima: `PersistenciaBasica` + `DatosSistema` (personas, sucursales, sesiones en `.txt`).
- 1.4.0 Interfaces (`Identificable`, `ConCosto`, `Reportable`) para polimorfismo por interfaz.
- 1.4.1 Clases abstractas y método plantilla (`Operacion` + operaciones concretas).
- 1.5.0 Polimorfismo en servicios y reportes.
- 1.5.1 Revisión de cohesión y acoplamiento (pequeños refactors).
- 1.6.0 Concurrencia: `AutoGuardadoThread` y `synchronized` en persistencia/datos.
- 1.6.1 Concurrencia: `ColaAccesos` para check-ins.
- 1.7.0 Validaciones y reglas de negocio (entradas y lógica).
- 1.7.1 Reportes operativos (morosidad, asistencia, ocupación, inventario, ingresos).
- 1.8.0 Pruebas manuales y datos semilla (menú “demo”).
- 1.8.1 Manejo de errores y recuperación (I/O, formatos, nulos).
- 1.8.2 Comentarios/Javadoc en clases/métodos clave.
- 1.9.0 Proyecto completo, listo para pruebas de funcionalidad.
- 1.9.1 Correcciones a fallos no contemplados.

---

## Pruebas y datos semilla

Datos semilla sugeridos (para pruebas manuales):
- 2 sucursales
- 5 clientes (distintas membresías; uno con pago vencido)
- 3 empleados (1 entrenador, 1 limpieza, 1 servicio al cliente)
- 3 clases grupales (cupos distintos)
- 4 equipos (diferentes estados)
- 2 planes de entrenamiento (3 días, 3 ejercicios por día)
- 6 pagos de ejemplo
- 4 sesiones de entrenamiento (vinculando clientes con entrenador)

Pruebas fundamentales:
- Altas/bajas/modificaciones por menú.
- Flujos fin a fin: inscripción a clase (con validación de membresía), registro de pago, mantenimiento de equipo, asignación de plan, registro de sesión.
- Persistencia: cargar al iniciar, guardar al salir; verificar contenido de `data/*.txt`.
- Concurrencia: utilizar la app mientras corre el auto-guardado; no deben aparecer errores ni archivos corruptos.

---