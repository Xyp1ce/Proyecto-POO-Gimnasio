Guía de metas y versiones (roadmap detallado)

    1.0.1 Creación del proyecto
        Objetivo: Inicializar el proyecto Java (estructura básica) con un punto de entrada que muestre un mensaje de bienvenida mediante JOptionPane.
        Clases: App/Vista con método main.
        Aceptación: Compila y muestra diálogo de bienvenida.

    1.0.2 Creación de clase Gimnasio (clase principal del dominio)
        Objetivo: Representar el concepto de gimnasio contenedor del sistema.
        Clases: Gimnasio con atributos privados: nombre, lista de sucursales (vacía inicialmente).
        Métodos: getters/setters, métodos para agregar/obtener sucursales.
        OOP: Agregación (Gimnasio agrega Sucursal).
        Aceptación: Se puede instanciar Gimnasio y listar sucursales (vacías) por JOptionPane.

    1.0.3 Creación de clase Sucursal
        Objetivo: Modelar una sede del gimnasio.
        Clases: Sucursal con id, nombre, dirección; colecciones de clientes y empleados.
        Aceptación: Se pueden crear sucursales y agregarlas al Gimnasio.

    1.0.4 Clase base Persona y roles Cliente/Empleado (herencia simple)
        Objetivo: Introducir herencia y polimorfismo básico.
        Clases: abstract class Persona (id, nombre, documento, teléfono), Cliente extends Persona, Empleado extends Persona.
        Métodos: toString(), validarDatos(), sobreescritura en subclases según formato de impresión.
        OOP: Herencia simple, niveles de acceso private/protected.
        Aceptación: Crear instancias de Cliente y Empleado y mostrarlas por JOptionPane.

    1.0.5 Empleados concretos (subtipos)
        Objetivo: Especializar empleados con responsabilidades claras.
        Clases: Entrenador, Limpieza y ServicioAlCliente.
        Aceptación: Operaciones básicas por tipo (registrar entrada/salida, tareas asignadas).

    1.0.6 Membresía y pagos (clases y operaciones)
        Objetivo: Administrar membresías y pagos asociados a clientes.
        Clases: Membresia (id, tipo, precio, vigencia), Pago (id, fecha, monto, método).
        Relaciones: Cliente tiene una Membresia y un historial de Pagos.
        Métodos: Cliente.realizarPago(Pago), Cliente.estaActivaMembresia().
        Aceptación: Se registra un pago y se confirma estado de membresía.

    1.1.0 Interfaz de menú con JOptionPane (sin GUI avanzada)
        Objetivo: Menú principal y submenús para operaciones de sucursales, clientes, empleados y membresías/pagos.
        Clases: MenuPrincipal, MenuSucursal, MenuCliente, MenuEmpleados, MenuMembresias, MenuPersistencia.
        Aceptación: Flujos básicos navegables por JOptionPane.

1.2.0 Persistencia mínima con TXT

    Crear PersistenciaBasica con métodos: guardar/cargarPersonas, guardar/cargarSucursales, guardar/cargarSesiones.
    Crear carpeta data/ y archivos vacíos si no existen.
    Integrar al flujo de la app: cargar al iniciar, guardar al salir.


    1.3.0 Interfaces para “herencia múltiple” (en Java)
        Objetivo: Cubrir herencia múltiple mediante interfaces.
        Interfaces: Identificable (getId), ConCosto (getCosto), Reportable (generarReporteTexto).
        Implementación: Persona, Pago y Membresia implementan las interfaces según corresponda.
        Aceptación: Polimorfismo por interfaz; listas de Reportable impresas uniformemente.

    1.3.1 Clases abstractas adicionales y plantilla de operaciones
        Objetivo: Practicar template method y abstracciones.
        Clases: abstract class Operacion (ejecutar(), validar()), subclase OperacionRegistrarPago.
        Aceptación: Ejecución estandarizada de registro de pagos con validación previa.

    1.3.2 Polimorfismo en servicios
        Objetivo: Servicio que opere sobre Persona (Cliente/Empleado) y Reportable sin conocer concreción.
        Clases: ServicioReportes con métodos para listar personas y generar reportes de membresías/pagos.
        Aceptación: Impresión polimórfica de listados.

    1.4.0 Concurrencia básica (autoguardado periódico)
        Objetivo: Mantener copias persistentes sin intervención del usuario.
        Clases: Autoguardado (Thread) integrado desde Gimnasio y detenido al salir.
        Aceptación: Guardados automáticos cada 40 segundos y finalización ordenada del hilo al cerrar la aplicación.
