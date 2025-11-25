package interfaz;
import javax.swing.JOptionPane;
import control.*;

public class MenuPrincipal
{
	// INSTANCIA GLOBAL PARA COORDINAR OPERACIONES DEL GIMNASIO
	static Gimnasio gimnasio = new Gimnasio();

	public static void menu()
	{
		// PRESENTA MENU PRINCIPAL Y DESPACHA A SUBMENUS ESPECIALIZADOS
		String opciones = "";

		do
		{
			opciones = JOptionPane.showInputDialog(null,
							"\tSISTEMA DE GESTIÓN DE GIMNASIO\n" +
							gimnasio.resumenSucursales() +
							"\nSeleccione una opción:\n\n" +
							"[1] Agregar Sucursal\n" +
							"[2] Mostrar Lista de Sucursales\n" +
							"[3] Seleccionar Sucursal\n" +
							"[4] Eliminar Sucursal\n" +
							"[5] Gestión de Persistencia (Guardar/Cargar)\n" +
							"[6] Reportes del Sistema\n" +
							"[7] Salir\n\n" +
							"Opción: ");

			if (opciones == null)
			{
				// CONFIRMAR SALIDA SI EL DIALOGO SE CANCELA
				confirmarSalida();
				return;
			}

			switch(opciones)
			{
				case "1": // AGREGAR SUCURSAL
				{
					agregarSucursal();
					break;
				}
				case "2": // VER SUCURSALES
				{
					JOptionPane.showMessageDialog(null, gimnasio.toString());
					break;
				}
				case "3": // SELECCIONAR SUCURSAL
				{
					seleccionarSucursal();
					break;
				}
				case "4": // ELIMINAR SUCURSAL
				{
					eliminarSucursal();
					break;
				}
				case "5": // PERSISTENCIA
				{
					MenuPersistencia.menu();
					break;
				}
				case "6": // REPORTES
				{
					menuReportes();
					break;
				}
				case "7": // SALIR
				{
					confirmarSalida();
					return;
				}
				default:
					JOptionPane.showMessageDialog(null, "Opcion invalida");
			}
		} while (!"7".equals(opciones));
	}

	private static void agregarSucursal()
	{
		// CAPTURA DATOS DE UNA NUEVA SUCURSAL CON VALIDACIONES BASICAS
		try
		{
			String inputNo = JOptionPane.showInputDialog("Ingrese el número de la sucursal:");
			if (inputNo == null)
				return;
			int noSucursal = Integer.parseInt(inputNo.trim());

			String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la Sucursal:");
			if (nombre == null)
				return;

			String horario = JOptionPane.showInputDialog(
					"Ingrese el horario en formato:\n" +
							"hh:mm-hh:mm\n" +
							"(Ejemplo: 06:00-22:00)");
			if (horario == null)
				return;

			String ubicacion = JOptionPane.showInputDialog("Ingrese la dirección de la Sucursal:");
			if (ubicacion == null)
				return;

			String servicios = JOptionPane.showInputDialog(
					"Ingrese los servicios especializados:\n" +
							"(Separados por comas)");
			if (servicios == null)
				return;

			String cuotaStr = JOptionPane.showInputDialog("Ingrese la cuota mensual:");
			if (cuotaStr == null)
				return;
			float cuota = Float.parseFloat(cuotaStr.trim());

			// CREAR INSTANCIA Y REGISTRARLA EN EL GIMNASIO
			Sucursal sucursal = new Sucursal(noSucursal, nombre, horario, ubicacion, servicios, cuota);
			gimnasio.agregarSucursal(sucursal);

			JOptionPane.showMessageDialog(null,
					"Sucursal agregada exitosamente\n\n" +
							sucursal);
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null,
					"Entrada numérica inválida. Operación cancelada.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void seleccionarSucursal()
	{
		// BUSCA SUCURSAL POR NUMERO Y ABRE EL MENU ESPECIFICO
		try
		{
			String opcion = JOptionPane.showInputDialog(null,
					"SELECCIONAR SUCURSAL\n\n" +
							gimnasio.resumenSucursales() +
							"\nIngrese el número de sucursal:");

			if (opcion == null)
				return;

			int opcSucursal = Integer.parseInt(opcion.trim());
			Sucursal sucursal = gimnasio.buscarSucursal(opcSucursal);

			if (sucursal != null)
			{
				MenuSucursal.menu(sucursal);
			}
			else
			{
				JOptionPane.showMessageDialog(null,
						"No se encontró la sucursal con número: " + opcSucursal,
						"Sucursal No Encontrada",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null,
					"Número de sucursal inválido.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void eliminarSucursal()
	{
		// SOLICITA CONFIRMACION ANTES DE ELIMINAR SUCURSAL DEL SISTEMA
		try
		{
			String opcion = JOptionPane.showInputDialog(null,
					"ELIMINAR SUCURSAL\n\n" +
							gimnasio.resumenSucursales() +
							"\nIngrese el número de sucursal a eliminar:");

			if (opcion == null)
				return;

			int opcSucursal = Integer.parseInt(opcion.trim());
			Sucursal sucursal = gimnasio.buscarSucursal(opcSucursal);

			if (sucursal != null)
			{
				int confirmacion = JOptionPane.showConfirmDialog(null,
						"¿Está seguro de eliminar la sucursal?\n\n" +
								sucursal +
								"\n\nEsta acción no se puede deshacer.",
						"Confirmar Eliminación",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (confirmacion == JOptionPane.YES_OPTION)
				{
					gimnasio.eliminarSucursal(opcSucursal);
					JOptionPane.showMessageDialog(null, "✓ Sucursal eliminada exitosamente");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,
						"No se encontró la sucursal con número: " + opcSucursal,
						"Sucursal No Encontrada",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null,
					"Número de sucursal inválido.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void menuReportes()
	{
		// AGRUPA REPORTES DEL SISTEMA PARA PERSONAS PAGOS Y SESIONES
		String opciones = "";

		do
		{
			opciones = JOptionPane.showInputDialog(null,
							"REPORTES DEL SISTEMA\n" +
							"[1] Reporte de Todas las Personas\n" +
							"[2] Reporte de Membresías\n" +
							"[3] Reporte de Pagos Recientes\n" +
							"[4] Reporte de Sesiones\n" +
							"[5] Regresar\n\n" +
							"Opción: ");

			if (opciones == null)
				return;

			switch (opciones)
			{
				case "1":
				{
					String reporte = ServicioReportes.listarPersonas(DatosSistema.obtenerPersonas());
					JOptionPane.showMessageDialog(null, reporte);
					break;
				}
				case "2":
				{
					// COMPILAR TODOS LOS CLIENTES DISPONIBLES ANTES DE GENERAR REPORTE
					entidades.Cliente[] todosClientes = recopilarClientes();
					String reporte = ServicioReportes.generarReporteMembresias(todosClientes);
					JOptionPane.showMessageDialog(null, reporte);
					break;
				}
				case "3":
				{
					String diasStr = JOptionPane.showInputDialog("¿Cuántos días hacia atrás?\n(Ejemplo: 30)");
					if (diasStr == null)
						break;

					try
					{
						int dias = Integer.parseInt(diasStr.trim());
						entidades.Cliente[] todosClientes = recopilarClientes();
						String reporte = ServicioReportes.generarReportePagosRecientes(todosClientes, dias);
						JOptionPane.showMessageDialog(null, reporte);
					}
					catch (NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(null, "Número inválido");
					}
					break;
				}
				case "4":
				{
					entidades.SesionEntrenamiento[] sesiones = DatosSistema.obtenerSesiones();
					StringBuilder reporte = new StringBuilder();
					reporte.append("SESIONES DE ENTRENAMIENTO\n\n");

					int contador = 0;
					for (entidades.SesionEntrenamiento sesion : sesiones)
					{
						if (sesion != null)
						{
							reporte.append(sesion.toString()).append("\n\n");
							contador++;
						}
					}

					reporte.append("Total de sesiones: ").append(contador);
					JOptionPane.showMessageDialog(null, reporte.toString());
					break;
				}
				case "5":
				{
					return;
				}
				default:
					JOptionPane.showMessageDialog(null, "Opcion invalida");
			}
		} while (!"5".equals(opciones));
	}

	private static entidades.Cliente[] recopilarClientes()
	{
		// UNE CLIENTES DE TODAS LAS SUCURSALES EN UN SOLO ARREGLO TEMPORAL
		entidades.Cliente[] resultado = new entidades.Cliente[500];
		int indice = 0;

		for (Sucursal sucursal : Gimnasio.obtenerSucursales())
		{
			if (sucursal == null)
				continue;

			entidades.Cliente[] clientes = sucursal.obtenerClientes();
			if (clientes == null)
				continue;

			for (entidades.Cliente cliente : clientes)
			{
				if (cliente != null && indice < resultado.length)
				{
					resultado[indice++] = cliente;
				}
			}
		}

		return resultado;
	}

	private static void confirmarSalida()
	{
		// OFRECE GUARDAR ANTES DE CERRAR EL SISTEMA Y DETIENE AUTOGUARDADO
		int confirmacion = JOptionPane.showConfirmDialog(null,
				"¿Desea guardar los datos antes de salir?",
				"Confirmar Salida",
				JOptionPane.YES_NO_CANCEL_OPTION);

		if (confirmacion == JOptionPane.YES_OPTION)
		{
			DatosSistema.definirSucursales(Gimnasio.obtenerSucursales());
			boolean exito = Persistencia.guardarTodo();

			if (exito)
			{
				JOptionPane.showMessageDialog(null,
						"Datos guardados exitosamente.\n" +
								"Hasta luego!");
			}
			else
			{
				JOptionPane.showMessageDialog(null,
						"Hubo un problema al guardar.\n" +
								"Saliendo de todas formas...");
			}

			Gimnasio.detenerAutoGuardado();
			System.exit(0);
		}
		else if (confirmacion == JOptionPane.NO_OPTION)
		{
			JOptionPane.showMessageDialog(null, "Saliendo sin guardar...");
			Gimnasio.detenerAutoGuardado();
			System.exit(0);
		}
		// SI ES CANCEL NO HACER NADA Y REGRESAR AL MENU
	}
}