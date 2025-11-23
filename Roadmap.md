Guía de metas y versiones (roadmap detallado)

    1.0.1 Creación del proyecto
        Objetivo: Inicializar el proyecto Java (estructura básica), punto de entrada Main con JOptionPane para mostrar un mensaje de bienvenida.
        Clases: App (con método main).
        Niveles de acceso: mantener todo en paquete por defecto o paquete gimnasio para facilitar visibilidad controlada más adelante.
        Aceptación: compila y muestra diálogo de bienvenida.

    1.0.2 Creación de clase Gimnasio (clase principal del dominio)
        Objetivo: Representar el concepto de gimnasio contenedor del sistema.
        Clases: Gimnasio con atributos privados: nombre, lista de sucursales (vacía inicialmente).
        Métodos: getters/setters, métodos para agregar/obtener sucursales.
        OOP: Agregación (Gimnasio agrega Sucursal).
        Aceptación: Se puede instanciar Gimnasio y listar sucursales (vacías) por JOptionPane.

    1.0.3 Creación de clase Sucursal
        Objetivo: Modelar una sede del gimnasio.
        Clases: Sucursal con id, nombre, dirección; colecciones de clientes, empleados, equipos, clases grupales.
        OOP: Composición suave con sus colecciones (el ciclo de vida puede ser controlado por Sucursal).
        Aceptación: Se pueden crear sucursales y agregarlas al Gimnasio.

    1.0.4 Clase base Persona y roles Cliente/Empleado (herencia simple)
        Objetivo: Introducir herencia y polimorfismo básico.
        Clases: abstract class Persona (id, nombre, documento, teléfono), Cliente extends Persona, Empleado extends Persona.
        Métodos: toString(), validarDatos(), sobreescritura en subclases según formato de impresión.
        OOP: Herencia simple, niveles de acceso private/protected.
        Aceptación: Crear instancias de Cliente y Empleado y mostrarlas por JOptionPane.

    1.0.5 Empleados concretos (subtipos) y cohesión
        Objetivo: Especializar empleados con coherencia de responsabilidades.
        Clases: Entrenador extends Empleado, Recepcionista extends Empleado.
        Métodos: Entrenador.asignarRutina(Cliente), Recepcionista.registrarAcceso(Cliente).
        Aceptación: Acciones básicas simuladas con JOptionPane.

    1.0.6 Membresía y pagos (clases y operaciones)
        Objetivo: Administrar membresías y pagos asociados a clientes.
        Clases: Membresia (id, tipo, precio, vigencia), Pago (id, fecha, monto, método).
        Relaciones: Cliente tiene una Membresia (agregación) y un historial de Pagos (composición).
        Métodos: Cliente.realizarPago(Pago), Cliente.estaActivaMembresia().
        Aceptación: Se registra un pago y se confirma estado de membresía.

    1.0.7 Equipos y mantenimiento (cohesión y composición)
        Objetivo: Gestionar inventario mínimo de equipos.
        Clases: Equipo (id, nombre, estado, fechaMantenimiento), RegistroMantenimiento.
        Relaciones: Sucursal compone equipos y registros de mantenimiento.
        Aceptación: Agregar equipos, marcar mantenimiento y mostrar estado.

    1.0.8 Clases grupales y cupos (agregación, composición)
        Objetivo: Manejar clases grupales en una sucursal.
        Clases: ClaseGrupal (id, nombre, horario, cupo, lista de inscritos).
        Métodos: inscribirCliente(Cliente), cancelarInscripcion(Cliente).
        Aceptación: Inscribir/cancelar y validar cupos por JOptionPane.

    1.1.0 Plan de entrenamiento y rutinas (composición fuerte)
        Objetivo: Asociar planes a clientes.
        Clases: PlanEntrenamiento (id, objetivo, lista de Rutina), Rutina (día, lista de ejercicios), Ejercicio (nombre, repeticiones).
        Relaciones: PlanEntrenamiento compone Rutina y Rutina compone Ejercicio.
        Aceptación: Asignar plan a cliente y visualizar plan.

    1.2.0 Interfaz de menú con JOptionPane (sin GUI avanzada)
        Objetivo: Menú principal y submenús para operaciones: clientes, empleados, membresías, pagos, equipos, clases, rutinas.
        Clases: Menu (coordinador estático), Controladores simples por sección.
        Aceptación: Flujos básicos navegables por JOptionPane.

1.3.0 Persistencia mínima con TXT

    Crear PersistenciaBasica con métodos: guardar/cargarPersonas, guardar/cargarSucursales, guardar/cargarSesiones.
    Crear carpeta data/ y archivos vacíos si no existen.
    Integrar al flujo de la app: cargar al iniciar, guardar al salir.


    1.4.0 Interfaces para “herencia múltiple” (en Java)
        Objetivo: Cubrir herencia múltiple mediante interfaces.
        Interfaces: Identificable (getId), ConCosto (getCosto), Reportable (generarReporteTexto).
        Implementación: Pago, Membresia, ClaseGrupal implementan interfaces según corresponda.
        Aceptación: Polimorfismo por interfaz; listas de Reportable impresas uniformemente.

    1.4.1 Clases abstractas adicionales y plantilla de operaciones
        Objetivo: Practicar template method y abstracciones.
        Clases: abstract class Operacion (ejecutar(), validar()), subclases: OperacionRegistrarPago, OperacionInscribirClase.
        Aceptación: Ejecución estandarizada de operaciones con validación previa.

    1.5.0 Polimorfismo avanzado en servicios
        Objetivo: Servicio que opere sobre Persona (Cliente/Empleado) y Reportable sin conocer concreción.
        Clases: ServicioReportes con métodos sobrecargados/sobrescritos.
        Aceptación: Impresión polimórfica de listados.

    1.5.1 Revisión de cohesión y acoplamiento
        Objetivo: Refactor para alta cohesión y bajo acoplamiento.
        Acciones: Extraer métodos largos, eliminar dependencias innecesarias, introducir pequeñas interfaces de proveedor de tiempo/ID.
        Aceptación: Métricas manuales (revisión de responsabilidades por clase).

    1.6.0 Concurrencia: auto-guardado y bloqueo
        Objetivo: Hilo en background que hace auto-guardado periódico a CSV.
        Clases: AutoGuardadoThread (Thread), Bloqueo con synchronized en repositorios.
        Aceptación: Guardado sin interferir con operaciones; sin corrupciones de archivo.

    1.6.1 Concurrencia: colas simples para check-in
        Objetivo: Simular múltiples registros de acceso mientras se consulta estado.
        Clases: RegistroAcceso, ColaAccesos (estructura sincronizada).
        Aceptación: Operaciones concurrentes básicas sin condiciones de carrera.

    1.7.0 Validaciones y reglas de negocio
        Objetivo: Validar entradas de JOptionPane, documentos, fechas, cupos, membresía activa, pagos mínimos.
        Clases: Validador, ReglaNegocio.
        Aceptación: Errores informativos por JOptionPane; no se persiste información inválida.

    1.7.1 Reportes operativos
        Objetivo: Generar reportes (texto) de morosidad, asistencia, ocupación, inventario, mantenimiento.
        Métodos: ServicioReportes.generarXxx() que devuelve String y se muestra con JOptionPane.
        Aceptación: Reportes con totales y listados claros.

    1.8.0 Pruebas manuales y datos semilla
        Objetivo: Scripts (métodos) que pueblen datos semilla para pruebas, menú de “demo”.
        Aceptación: Recorrer flujos clave con datos semilla.

    1.8.1 Manejo de errores y recuperación
        Objetivo: Captura de excepciones de I/O, formateo CSV, nulos; reintentos de entrada por JOptionPane.
        Aceptación: Sin cierres inesperados; mensajes claros.

    1.8.2 Documentación técnica (breve) en comentarios
        Objetivo: Comentarios Javadoc básicos en clases y métodos públicos.
        Aceptación: Navegación legible del código.

    1.9.0 Proyecto completo, listo para pruebas de funcionalidad
        Objetivo: Cierre de alcance; checklist de temas.
        Aceptación: Todos los temas abordados y verificables vía menú y reportes.

    1.9.1 Correcciones a fallos no contemplados
        Objetivo: Hotfixes tras pruebas, mejoras menores de mensajes y validaciones.
        Aceptación: Lista de issues resueltos y retesteo básico.
