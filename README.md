Requerimiento de la Aplicación del Gimnasio

El gimnasio requiere una nueva aplicación para gestionar a sus empleados y clientes. A continuación se detallan los puntos principales:

Los empleados tienen distintos roles:

Servicio al Cliente

Entrenador

Limpieza

Cada empleado cuenta con un horario, sueldo y funciones específicas según su rol.

Los clientes acceden a las áreas del gimnasio según su tipo de membresía:

Básica

Premium

Los entrenadores atienden a los clientes bajo demanda.

El plan Premium incluye el servicio de los entrenadores.

Los entrenadores reciben comisiones basadas en su rendimiento y en la satisfacción de los clientes atendidos.

Los empleados del área de Servicio al Cliente también reciben comisiones dependiendo de los resultados de las encuestas de satisfacción de los clientes.


# Clases del Gimnasio

| Clase             | Atributos                                                                 | Métodos                                                                                   |
|------------------|---------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| **Gimnasio**       | Nombre, Ubicaciones                                                       | agregarSucursal(), eliminarSucursal()                                                    |
| **Empleado**       | ID, Tipo, Horario, Nombre, RFC, Número telefónico, Dirección             | registrarEntradaSalida(), calcularPago(), solicitarVacaciones(), mostrarInformacion()    |
| **Servicio al Cliente** | Sueldo, Nivel idioma, Clientes atendidos                             | atenderCliente(), registrarQueja(), realizarEncuestaSatisfaccion()                       |
| **Entrenador**      | Sueldo, Especialidad, Certificaciones, Comisión, Rutinas                 | crearRutinaEspecializada(), supervisarEntrenamiento(), corregirTecnica(), evaluarProgreso() |
| **Limpieza**        | Sueldo, Área, Turno                                                      | limpiarArea(), revisarSuministros(), reportarDaños()                                     |
| **Cliente**         | Nombre, ID, Tipo de Membresía, Historial de visitas, Número telefónico  | registrarVisita(), pagarMembresia(), cancelarMembresia(), mostrarInformacion(), evaluarEntrenador() |
| **Sucursal**        | Ubicacion, Servicios, Horarios, Cuota                                    | agregarCliente(), eliminarCliente(), agregarEmpleado(), eliminarEmpleado()              |

![WhatsApp Image 2025-10-07 at 23 41 50_86469936](https://github.com/user-attachments/assets/13cbd605-2885-4fee-9053-3fd9d0f63103)
