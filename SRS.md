SRS (Software Requirements Specification) del Sistema de Gimnasio

    Introducción

    Propósito: Especificar los requisitos para un sistema de gestión de gimnasio, orientado a objetos en Java, con interacción exclusivamente mediante JOptionPane.
    Audiencia: Estudiantes, docentes y desarrolladores que evaluarán POO (clases, herencia, polimorfismo), persistencia y concurrencia.

    Alcance

    Funciones: Gestión de sucursales, clientes, empleados, membresías, pagos, equipos, clases grupales, planes de entrenamiento, asistencias, reportes, persistencia en archivos y procesos concurrentes simples.
    Exclusiones: No hay GUI avanzada, no hay base de datos relacional, no hay servicios de red, no lambdas ni características avanzadas.

    Definiciones y términos

    Sucursal: Sede del gimnasio.
    Membresía: Tipo de suscripción (mensual, trimestral, anual).
    Plan de entrenamiento: Conjunto de rutinas y ejercicios.
    Persistencia: Almacenamiento en archivos CSV/Texto.
    Concurrencia: Hilos para auto-guardado y colas básicas.

    Actores

    Recepcionista: Administra clientes, check-in, pagos, inscripciones a clases.
    Entrenador: Asigna planes de entrenamiento.
    Administrador (rol lógico): Visualiza reportes, gestiona sucursales/equipos.
    Sistema (hilo): Auto-guardado y procesamiento de cola de accesos.

    Requisitos funcionales (RF)

    RF-01: Crear/editar/eliminar Sucursal.
    RF-02: Alta/baja/modificación de Cliente.
    RF-03: Alta/baja/modificación de Empleado (Entrenador, Recepcionista).
    RF-04: Crear/editar Membresía; asignar membresía a Cliente; verificar vigencia.
    RF-05: Registrar Pago (monto, fecha, método); asociar a Cliente; calcular deuda.
    RF-06: Registrar acceso/Asistencia de Cliente (check-in), condicionado a membresía activa.
    RF-07: Gestionar Equipo (alta, estado, mantenimiento), con historial.
    RF-08: Gestionar ClaseGrupal (cupos, horario, inscripción/cancelación).
    RF-09: Crear/editar PlanEntrenamiento con Rutinas/Ejercicios; asignar a Cliente.
    RF-10: Menú de navegación con JOptionPane para todas las operaciones.
    RF-11: Persistencia CSV: cargar al iniciar, guardar al salir y auto-guardado periódico.
    RF-12: Reportes: morosidad, asistencia, ocupación de clases, estado de equipos, ingresos.
    RF-13: Validaciones de entrada y reglas (no cupos negativos, pagos > 0, documentos válidos).
    RF-14: Manejo de errores con mensajes claros y reintentos por JOptionPane.

    Requisitos no funcionales (RNF)

    RNF-01: Simplicidad: sin uso de lambdas, streams avanzados, frameworks ni redes.
    RNF-02: Persistencia legible en archivos CSV/Texto.
    RNF-03: Concurrencia limitada a hilos básicos (Thread), synchronized en repositorios.
    RNF-04: Mantenibilidad: alta cohesión por clase, bajo acoplamiento entre módulos.
    RNF-05: Portabilidad: ejecución en JRE estándar.
    RNF-06: Usabilidad: Menús claros en JOptionPane, mensajes comprensibles.

    Modelo de datos conceptual (resumen)

    Gimnasio 1..* Sucursal
    Sucursal 0..* Cliente, 0..* Empleado, 0..* Equipo, 0..* ClaseGrupal
    Cliente 0..1 Membresia, 0..* Pago, 0..1 PlanEntrenamiento, 0..* RegistroAcceso
    PlanEntrenamiento 1..* Rutina; Rutina 1..* Ejercicio
    ClaseGrupal 0..* Cliente (inscritos)
    Equipo 0..* RegistroMantenimiento

    Diseño orientado a objetos (por temas)

8.1 Clases y objetos

    Núcleo: Gimnasio, Sucursal, Persona (abstracta), Cliente, Empleado, Entrenador, Recepcionista, Membresia, Pago, Equipo, RegistroMantenimiento, ClaseGrupal, PlanEntrenamiento, Rutina, Ejercicio, RegistroAcceso, Repositorios y Servicios.
    Objetos creados por casos de uso (alta de cliente, creación de clase, etc.).

8.2 Métodos y operaciones

    CRUD por entidad (crear/editar/eliminar/listar).
    Operaciones de negocio: Cliente.realizarPago(), Cliente.estaActivaMembresia(), ClaseGrupal.inscribirCliente(), Entrenador.asignarRutina().
    Servicios: ServicioReportes.generarMorosidad(), RepositorioX.guardar()/cargar().

8.3 Niveles de protección/acceso

    Atributos privados; getters/setters públicos cuando aplique.
    Métodos helper con visibilidad package/private donde corresponda.
    protected para métodos de plantilla en clases abstractas (e.g., Operacion.validar()).

8.4 Agregación y composición

    Agregación: Gimnasio–Sucursal, Sucursal–Cliente/Empleado/ClaseGrupal/Equipo.
    Composición: PlanEntrenamiento–Rutina–Ejercicio; Cliente–Pagos (historial local); Sucursal–RegistroMantenimiento.

8.5 Herencia simple

    Persona → Cliente, Empleado.
    Empleado → Entrenador, Recepcionista.

8.6 Clases abstractas

    Persona (datos comunes).
    Operacion (patrón plantilla para operaciones transaccionales).

8.7 “Herencia múltiple” (vía interfaces)

    Interfaces: Identificable, ConCosto, Reportable, Persistible (si se requiere).
    Implementaciones múltiples por clases concretas para demostrar polimorfismo por interfaz.

8.8 Polimorfismo

    Upcasting: tratar Cliente/Empleado como Persona.
    Polimorfismo por interfaz: listar Reportable y producir reportes uniformes.
    Sobrecarga/sobrescritura: toString(), reportes con distintas firmas.

8.9 Cohesión

    Cada clase con responsabilidad única: p.ej., RepositorioCliente solo persistencia de Cliente, ServicioReportes solo composición de reportes.

8.10 Acoplamiento

    Bajo acoplamiento mediante interfaces y separación de responsabilidades.
    Controladores del menú invocan servicios; servicios dependen de repositorios a través de interfaces simples si se introduce ese nivel.

8.11 Persistencia

    CSV por entidad con separador claro; manejo de escape de comas si aplica.
    Repositorios con synchronized en operaciones de lectura/escritura.
    Carga inicial y guardado al salir; auto-guardado en hilo.

8.12 Concurrencia

    Thread de auto-guardado periódico.
    Cola sincronizada de RegistroAcceso para simular concurrencia.
    Evitar condiciones de carrera con synchronized en repositorios y estructuras compartidas.

    Casos de uso clave (resumen de flujo con JOptionPane)

    CU-01 Alta de Cliente: Menú → solicitar datos → validar → crear → confirmar → guardar (en memoria).
    CU-02 Registrar Pago: Seleccionar cliente → monto/forma → validar membresía → generar Pago → confirmar → reflejar en reporte.
    CU-03 Inscripción a Clase: Seleccionar clase y cliente → validar cupo y membresía → inscribir → confirmar.
    CU-04 Asignar Plan: Seleccionar cliente → crear plan con rutinas/ejercicios → asignar → mostrar resumen.
    CU-05 Mantenimiento de Equipo: Elegir equipo → registrar mantenimiento → actualizar estado.
    CU-06 Reportes: Elegir tipo → generar String → mostrar en JOptionPane (scrollable si se usa JTextArea en JOptionPane).

    Reglas de negocio y validaciones

    Membresía activa para acceso/inscripción a clases.
    Pagos con monto positivo; fecha válida; método permitido.
    Documentos únicos por cliente (según decisión de diseño).
    Cupo de clase ≥ inscritos.
    Equipo en “Fuera de servicio” no debe asignarse a clase/uso simulada (si aplica).

    Manejo de errores

    Captura de NumberFormatException para entradas numéricas en JOptionPane.
    Manejo de IOException en persistencia con mensajes y opción de reintento.
    Validaciones previas a escritura.

    Suposiciones y restricciones

    No base de datos; sólo archivos CSV/Texto.
    Sin redes; sin hilos complejos; sin librerías externas.
    Java estándar; interacción por JOptionPane únicamente.

    Criterios de aceptación por tema

    Clases/Objetos: Se crean, muestran y relacionan instancias según modelo.
    Métodos/Operaciones: CRUD y operaciones de negocio funcionales por menú.
    Niveles de acceso: Encapsulamiento estricto; sin acceso directo a campos desde fuera.
    Agregación/Composición: Relaciones implementadas y observables en flujos.
    Herencia simple/abstractas/múltiple (interfaces): Jerarquías y usos implementados; métodos abstractos definidos y cumplidos; interfaces aplicadas a múltiples clases.
    Polimorfismo: Listados y reportes funcionan sobre tipos generales (Persona/Reportable).
    Cohesión/Acoplamiento: Revisión de responsabilidades y dependencias (documentado).
    Persistencia: Carga/guardado correcto; archivos legibles; tolerancia a errores básicos.
    Concurrencia: Auto-guardado y cola de accesos funcionando sin corrupción de datos.

    Plan de pruebas (manual, con apoyo de datos semilla)

    Pruebas de unidad manuales por clase (crear, validar, imprimir).
    Pruebas de integración por caso de uso (fin a fin con persistencia).
    Pruebas de concurrencia: ejecutar operaciones mientras corre auto-guardado; verificar integridad de archivos.
    Pruebas de regresión en 1.9.0 y 1.9.1.

    Datos de ejemplo (mínimos)

    Sucursales: 2
    Clientes: 5 (con distintas membresías y estados)
    Empleados: 1 Entrenador, 1 Recepcionista
    Clases grupales: 3 (cupo variado)
    Equipos: 4 (distintos estados)
    Planes: 2 planes con rutinas de 3 días, 3 ejercicios por día
    Pagos: 6 (incluyendo uno atrasado)

    Riesgos y mitigaciones

    Corrupción de CSV por cierre inesperado: auto-guardado con rotación (archivo.bak).
    Inconsistencia por concurrencia: synchronized en repositorios y estructuras compartidas.
    Complejidad del menú: dividir en submenús y controladores simples.
