package interfaz;

import javax.swing.JOptionPane;
import control.Sucursal;
import entidades.*;

public class MenuMembresias
{
	public static void menu(Sucursal sucursal)
	{
		String opciones = "";

		do
		{
			opciones = JOptionPane.showInputDialog(null,
					"===== GESTIÓN DE MEMBRESÍAS Y PAGOS =====\n" +
							"Sucursal: " + sucursal.obtenerNombreSucursal() + "\n\n" +
							"[1] Asignar Membresía a Cliente\n" +
							"[2] Renovar Membresía de Cliente\n" +
							"[3] Consultar Estado de Membresía\n" +
							"[4] Registrar Pago\n" +
							"[5] Ver Historial de Pagos de Cliente\n" +
							"[6] Reporte de Morosidad\n" +
							"[7] Regresar\n\n" +
							"Opción: ");

			if (opciones == null)
			{
				JOptionPane.showMessageDialog(null, "Regresando al menú de sucursal...");
				return;
			}

			switch (opciones)
			{
				case "1": // ASIGNAR MEMBRESIA
				{
					asignarMembresia(sucursal);
					break;
				}
				case "2": // RENOVAR MEMBRESIA
				{
					renovarMembresia(sucursal);
					break;
				}
				case "3": // CONSULTAR ESTADO
				{
					consultarEstadoMembresia(sucursal);
					break;
				}
				case "4": // REGISTRAR PAGO
				{
					registrarPago(sucursal);
					break;
				}
				case "5": // VER HISTORIAL DE PAGOS
				{
					verHistorialPagos(sucursal);
					break;
				}
				case "6": // REPORTE DE MOROSIDAD
				{
					generarReporteMorosidad(sucursal);
					break;
				}
				case "7":
				{
					JOptionPane.showMessageDialog(null, "Regresando al menú de sucursal...");
					return;
				}
				default:
					JOptionPane.showMessageDialog(null, "Opción inválida");
			}
		} while (!"7".equals(opciones));
	}

	// METODO PARA ASIGNAR MEMBRESIA
	private static void asignarMembresia(Sucursal sucursal)
	{
		try
		{
			// Solicitar ID del cliente
			String idStr = JOptionPane.showInputDialog(null,
					"Clientes disponibles:\n" +
							MenuSucursal.verClientes(sucursal) + "\n" +
							"Ingrese el ID del cliente:");

			if (idStr == null)
				return;

			long idCliente = Long.parseLong(idStr.trim());
			Cliente cliente = sucursal.buscarCliente(idCliente);

			if (cliente == null)
			{
				JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
				return;
			}

			// Verificar si ya tiene membresía activa
			if (cliente.estaActivaMembresia())
			{
				int respuesta = JOptionPane.showConfirmDialog(null,
						"El cliente ya tiene una membresía activa.\n" +
								cliente.obtenerMembresia().resumen() + "\n\n" +
								"¿Desea reemplazarla?",
						"Membresía Existente",
						JOptionPane.YES_NO_OPTION);

				if (respuesta != JOptionPane.YES_OPTION)
					return;
			}

			// Seleccionar tipo de membresía
			String[] opciones = {"Mensual", "Trimestral", "Semestral", "Anual"};
			String tipoSeleccionado = (String) JOptionPane.showInputDialog(null,
					"Seleccione el tipo de membresía:",
					"Tipo de Membresía",
					JOptionPane.QUESTION_MESSAGE,
					null,
					opciones,
					opciones[0]);

			if (tipoSeleccionado == null)
				return;

			// Solicitar precio
			String precioStr = JOptionPane.showInputDialog(null,
					"Precios sugeridos:\n" +
							"Mensual: $300\n" +
							"Trimestral: $800\n" +
							"Semestral: $1500\n" +
							"Anual: $2800\n\n" +
							"Ingrese el precio:");

			if (precioStr == null)
				return;

			float precio = Float.parseFloat(precioStr.trim());

			if (precio <= 0)
			{
				JOptionPane.showMessageDialog(null, "El precio debe ser mayor a cero.");
				return;
			}

			// Crear y asignar la membresía
			Membresia nuevaMembresia = new Membresia(tipoSeleccionado, precio);
			cliente.asignarMembresia(nuevaMembresia);

			JOptionPane.showMessageDialog(null,
					"✓ Membresía asignada exitosamente\n\n" +
							nuevaMembresia);
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Entrada numérica inválida.");
		}
	}

	// METODO PARA RENOVAR MEMBRESIA
	private static void renovarMembresia(Sucursal sucursal)
	{
		try
		{
			String idStr = JOptionPane.showInputDialog(null,
					"Ingrese el ID del cliente:");

			if (idStr == null)
				return;

			long idCliente = Long.parseLong(idStr.trim());
			Cliente cliente = sucursal.buscarCliente(idCliente);

			if (cliente == null)
			{
				JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
				return;
			}

			if (cliente.obtenerMembresia() == null)
			{
				JOptionPane.showMessageDialog(null,
						"El cliente no tiene membresía asignada.\n" +
								"Use la opción 'Asignar Membresía' primero.");
				return;
			}

			Membresia membresia = cliente.obtenerMembresia();

			int respuesta = JOptionPane.showConfirmDialog(null,
					"Cliente: " + cliente.obtenerNombre() + "\n" +
							"Membresía actual: " + membresia.resumen() + "\n\n" +
							"¿Confirma la renovación?",
					"Renovar Membresía",
					JOptionPane.YES_NO_OPTION);

			if (respuesta == JOptionPane.YES_OPTION)
			{
				cliente.renovarMembresia();
				JOptionPane.showMessageDialog(null,
						"✓ Membresía renovada exitosamente\n\n" +
								membresia);
			}
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}

	// METODO PARA CONSULTAR ESTADO DE MEMBRESIA
	private static void consultarEstadoMembresia(Sucursal sucursal)
	{
		try
		{
			String idStr = JOptionPane.showInputDialog(null,
					"Ingrese el ID del cliente:");

			if (idStr == null)
				return;

			long idCliente = Long.parseLong(idStr.trim());
			Cliente cliente = sucursal.buscarCliente(idCliente);

			if (cliente == null)
			{
				JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
				return;
			}

			if (cliente.obtenerMembresia() == null)
			{
				JOptionPane.showMessageDialog(null,
						"Cliente: " + cliente.obtenerNombre() + "\n" +
								"Estado: Sin membresía asignada");
				return;
			}

			Membresia membresia = cliente.obtenerMembresia();
			String estado = membresia.verificarVigencia() ? "✓ VIGENTE" : "✗ VENCIDA";

			JOptionPane.showMessageDialog(null,
					"===== ESTADO DE MEMBRESÍA =====\n" +
							"Cliente: " + cliente.obtenerNombre() + "\n" +
							"ID: " + cliente.obtenerIdentificador() + "\n" +
							"-------------------------------\n" +
							membresia + "\n" +
							"-------------------------------\n" +
							"Estado: " + estado + "\n" +
							"===============================");
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}

	// METODO PARA REGISTRAR PAGO
	private static void registrarPago(Sucursal sucursal)
	{
		try
		{
			String idStr = JOptionPane.showInputDialog(null,
					"Ingrese el ID del cliente:");

			if (idStr == null)
				return;

			long idCliente = Long.parseLong(idStr.trim());
			Cliente cliente = sucursal.buscarCliente(idCliente);

			if (cliente == null)
			{
				JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
				return;
			}

			String montoStr = JOptionPane.showInputDialog(null,
					"Cliente: " + cliente.obtenerNombre() + "\n\n" +
							"Ingrese el monto del pago:");

			if (montoStr == null)
				return;

			float monto = Float.parseFloat(montoStr.trim());

			if (monto <= 0)
			{
				JOptionPane.showMessageDialog(null, "El monto debe ser mayor a cero.");
				return;
			}

			String[] metodos = {"Efectivo", "Tarjeta", "Transferencia", "Debito", "Credito"};
			String metodoSeleccionado = (String) JOptionPane.showInputDialog(null,
					"Seleccione el método de pago:",
					"Método de Pago",
					JOptionPane.QUESTION_MESSAGE,
					null,
					metodos,
					metodos[0]);

			if (metodoSeleccionado == null)
				return;

			String concepto = JOptionPane.showInputDialog(null,
					"Ingrese el concepto (opcional):",
					"Pago de membresía");

			if (concepto == null || concepto.trim().isEmpty())
				concepto = "Pago de membresía";

			Pago nuevoPago = new Pago(idCliente, monto, metodoSeleccionado, concepto);

			if (cliente.realizarPago(nuevoPago))
			{
				JOptionPane.showMessageDialog(null,
						"✓ Pago registrado exitosamente\n\n" +
								nuevoPago.generarRecibo());
			}
			else
			{
				JOptionPane.showMessageDialog(null,
						"✗ Error al registrar el pago.\n" +
								"Verifique los datos e intente nuevamente.");
			}
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "Entrada numérica inválida.");
		}
	}

	// METODO PARA VER HISTORIAL DE PAGOS
	private static void verHistorialPagos(Sucursal sucursal)
	{
		try
		{
			String idStr = JOptionPane.showInputDialog(null,
					"Ingrese el ID del cliente:");

			if (idStr == null)
				return;

			long idCliente = Long.parseLong(idStr.trim());
			Cliente cliente = sucursal.buscarCliente(idCliente);

			if (cliente == null)
			{
				JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
				return;
			}

			String reporte = cliente.generarReportePagos();
			JOptionPane.showMessageDialog(null, reporte);
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}

	// METODO PARA GENERAR REPORTE DE MOROSIDAD
	private static void generarReporteMorosidad(Sucursal sucursal)
	{
		StringBuilder reporte = new StringBuilder();
		reporte.append("===== REPORTE DE MOROSIDAD =====\n");
		reporte.append("Sucursal: ").append(sucursal.obtenerNombreSucursal()).append("\n\n");

		Cliente[] clientes = sucursal.obtenerClientes();
		int clientesMorosos = 0;
		int clientesActivos = 0;
		int clientesSinMembresia = 0;

		for (Cliente cliente : clientes)
		{
			if (cliente == null)
				continue;

			if (cliente.obtenerMembresia() == null)
			{
				clientesSinMembresia++;
			}
			else if (cliente.estaActivaMembresia())
			{
				clientesActivos++;
			}
			else
			{
				clientesMorosos++;
				reporte.append("✗ ").append(cliente.obtenerNombre());
				reporte.append(" (ID: ").append(cliente.obtenerIdentificador()).append(")");
				reporte.append(" - Membresía vencida\n");
			}
		}

		reporte.append("\n--------------------------------\n");
		reporte.append("Total clientes: ").append(sucursal.obtenerTotalClientes()).append("\n");
		reporte.append("Activos: ").append(clientesActivos).append("\n");
		reporte.append("Morosos: ").append(clientesMorosos).append("\n");
		reporte.append("Sin membresía: ").append(clientesSinMembresia).append("\n");
		reporte.append("================================\n");

		JOptionPane.showMessageDialog(null, reporte.toString());
	}
}