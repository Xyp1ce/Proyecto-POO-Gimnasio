package interfaz;

import javax.swing.JOptionPane;
import control.*;

public class MenuPersistencia
{
	public static void menu()
	{
		String opciones = "";

		do
		{
			opciones = JOptionPane.showInputDialog(null,
							"\tGESTIÓN DE PERSISTENCIA\n" +
							"[1] Guardar Todos los Datos\n" +
							"[2] Cargar Todos los Datos\n" +
							"[3] Guardar Solo Personas\n" +
							"[4] Guardar Solo Sucursales\n" +
							"[5] Guardar Solo Sesiones\n" +
							"[6] Ver Estado de Archivos\n" +
							"[7] Regresar\n\n" +
							"Opción: ");

			if (opciones == null)
			{
				return;
			}

			switch (opciones)
			{
				case "1": // GUARDAR TODO
				{
					guardarTodo();
					break;
				}
				case "2": // CARGAR TODO
				{
					cargarTodo();
					break;
				}
				case "3": // GUARDAR PERSONAS
				{
					guardarPersonas();
					break;
				}
				case "4": // GUARDAR SUCURSALES
				{
					guardarSucursales();
					break;
				}
				case "5": // GUARDAR SESIONES
				{
					guardarSesiones();
					break;
				}
				case "6": // VER ESTADO
				{
					verEstadoArchivos();
					break;
				}
				case "7":
				{
					return;
				}
				default:
					JOptionPane.showMessageDialog(null, "Opción inválida");
			}
		} while (!"7".equals(opciones));
	}

	private static void guardarTodo()
	{
		int confirmacion = JOptionPane.showConfirmDialog(null,
				"¿Confirma que desea guardar todos los datos?\n" +
						"Esto sobrescribirá los archivos existentes.",
				"Confirmar Guardado",
				JOptionPane.YES_NO_OPTION);

		if (confirmacion != JOptionPane.YES_OPTION)
			return;

		boolean exito = PersistenciaBasica.guardarTodo();

		if (exito)
		{
			JOptionPane.showMessageDialog(null,
					"✓ Datos guardados exitosamente\n\n" +
							"Archivos actualizados:\n" +
							"• data/personas.txt\n" +
							"• data/sucursales.txt\n" +
							"• data/sesiones.txt");
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"✗ Error al guardar los datos\n" +
							"Verifique los permisos de escritura.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void cargarTodo()
	{
		int confirmacion = JOptionPane.showConfirmDialog(null,
				"¿Confirma que desea cargar todos los datos?\n" +
						"Esto reemplazará los datos actuales en memoria.",
				"Confirmar Carga",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);

		if (confirmacion != JOptionPane.YES_OPTION)
			return;

		boolean exito = PersistenciaBasica.cargarTodo();

		if (exito)
		{
			JOptionPane.showMessageDialog(null,
					"✓ Datos cargados exitosamente\n\n" +
							"Información cargada:\n" +
							"• Personas: " + DatosSistema.obtenerTotalPersonas() + "\n" +
							"• Sucursales: " + DatosSistema.obtenerSucursales().length + "\n" +
							"• Sesiones: " + DatosSistema.obtenerTotalSesiones());
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"✗ Error al cargar los datos\n" +
							"Verifique que los archivos existan.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void guardarPersonas()
	{
		boolean exito = PersistenciaBasica.guardarPersonas(DatosSistema.obtenerPersonas());

		if (exito)
		{
			JOptionPane.showMessageDialog(null,
					"✓ Personas guardadas exitosamente\n" +
							"Total: " + DatosSistema.obtenerTotalPersonas());
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"✗ Error al guardar personas",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void guardarSucursales()
	{
		boolean exito = PersistenciaBasica.guardarSucursales(DatosSistema.obtenerSucursales());

		if (exito)
		{
			JOptionPane.showMessageDialog(null,
					"✓ Sucursales guardadas exitosamente\n" +
							"Total: " + DatosSistema.obtenerSucursales().length);
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"✗ Error al guardar sucursales",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void guardarSesiones()
	{
		boolean exito = PersistenciaBasica.guardarSesiones(DatosSistema.obtenerSesiones());

		if (exito)
		{
			JOptionPane.showMessageDialog(null,
					"✓ Sesiones guardadas exitosamente\n" +
							"Total: " + DatosSistema.obtenerTotalSesiones());
		}
		else
		{
			JOptionPane.showMessageDialog(null,
					"✗ Error al guardar sesiones",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void verEstadoArchivos()
	{
		java.io.File carpeta = new java.io.File("data");
		java.io.File archivoPersonas = new java.io.File("data/personas.txt");
		java.io.File archivoSucursales = new java.io.File("data/sucursales.txt");
		java.io.File archivoSesiones = new java.io.File("data/sesiones.txt");

		StringBuilder estado = new StringBuilder();
		estado.append("\tESTADO DE ARCHIVOS\n");

		estado.append("Carpeta data/: ");
		estado.append(carpeta.exists() ? "✓ Existe" : "✗ No existe").append("\n\n");

		estado.append("personas.txt:\n");
		if (archivoPersonas.exists())
		{
			estado.append("  Estado: ✓ Existe\n");
			estado.append("  Tamaño: ").append(archivoPersonas.length()).append(" bytes\n");
		}
		else
		{
			estado.append("  Estado: ✗ No existe\n");
		}

		estado.append("\nsucursales.txt:\n");
		if (archivoSucursales.exists())
		{
			estado.append("  Estado: ✓ Existe\n");
			estado.append("  Tamaño: ").append(archivoSucursales.length()).append(" bytes\n");
		}
		else
		{
			estado.append("  Estado: ✗ No existe\n");
		}

		estado.append("\nsesiones.txt:\n");
		if (archivoSesiones.exists())
		{
			estado.append("  Estado: ✓ Existe\n");
			estado.append("  Tamaño: ").append(archivoSesiones.length()).append(" bytes\n");
		}
		else
		{
			estado.append("  Estado: ✗ No existe\n");
		}

		estado.append("\n═══════════════════════════════════\n");
		estado.append("Datos en Memoria:\n");
		estado.append("  Personas: ").append(DatosSistema.obtenerTotalPersonas()).append("\n");
		estado.append("  Sucursales: ").append(DatosSistema.obtenerSucursales().length).append("\n");
		estado.append("  Sesiones: ").append(DatosSistema.obtenerTotalSesiones()).append("\n");

		JOptionPane.showMessageDialog(null, estado.toString());
	}
}