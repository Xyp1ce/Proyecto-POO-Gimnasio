package control;

import entidades.*;

public class ServicioReportes
{
	/**
	 * Genera un reporte consolidado de múltiples objetos Reportable.
	 * Demuestra polimorfismo por interfaz.
	 */
	public static String generarReporteConsolidado(Reportable[] objetos)
	{
		if (objetos == null || objetos.length == 0)
			return "No hay objetos para reportar.";

		StringBuilder consolidado = new StringBuilder();
		consolidado.append("\n╔════════════════════════════════════════╗\n");
		consolidado.append("║   REPORTE CONSOLIDADO DEL SISTEMA     ║\n");
		consolidado.append("╚════════════════════════════════════════╝\n\n");

		int contador = 0;
		for (Reportable objeto : objetos)
		{
			if (objeto != null)
			{
				consolidado.append("--- Item ").append(++contador).append(" ---\n");
				consolidado.append(objeto.generarReporte());
				consolidado.append("\n");
			}
		}

		consolidado.append("Total de items reportados: ").append(contador).append("\n");
		return consolidado.toString();
	}

	/**
	 * Calcula el costo total de múltiples objetos ConCosto.
	 * Demuestra polimorfismo por interfaz.
	 */
	public static float calcularCostoTotal(ConCosto[] objetos)
	{
		if (objetos == null || objetos.length == 0)
			return 0.0f;

		float total = 0.0f;
		for (ConCosto objeto : objetos)
		{
			if (objeto != null)
			{
				total += objeto.obtenerCosto();
			}
		}

		return total;
	}

	/**
	 * Genera reporte de costos detallado.
	 */
	public static String generarReporteCostos(ConCosto[] objetos)
	{
		if (objetos == null || objetos.length == 0)
			return "No hay objetos con costo.";

		StringBuilder reporte = new StringBuilder();
		reporte.append("\n╔════════════════════════════════════════╗\n");
		reporte.append("║      REPORTE DE COSTOS DETALLADO      ║\n");
		reporte.append("╚════════════════════════════════════════╝\n\n");

		int item = 1;
		float subtotal = 0.0f;

		for (ConCosto objeto : objetos)
		{
			if (objeto != null)
			{
				float costo = objeto.obtenerCosto();
				String tipo = objeto.getClass().getSimpleName();

				reporte.append(String.format("%d. %-20s $%,10.2f\n", item++, tipo, costo));
				subtotal += costo;
			}
		}

		reporte.append("─────────────────────────────────────────\n");
		reporte.append(String.format("   %-20s $%,10.2f\n", "TOTAL:", subtotal));
		reporte.append("─────────────────────────────────────────\n");

		return reporte.toString();
	}

	/**
	 * Lista todas las personas del sistema (polimorfismo por herencia).
	 */
	public static String listarPersonas(Persona[] personas)
	{
		if (personas == null || personas.length == 0)
			return "No hay personas registradas.";

		StringBuilder lista = new StringBuilder();
		lista.append("\n╔════════════════════════════════════════╗\n");
		lista.append("║      LISTADO DE PERSONAS              ║\n");
		lista.append("╚════════════════════════════════════════╝\n\n");

		int clientes = 0;
		int empleados = 0;

		for (Persona persona : personas)
		{
			if (persona == null)
				continue;

			// Polimorfismo: toString() llama a generarDescripcion() según tipo
			lista.append(persona.toString()).append("\n\n");

			if (persona instanceof Cliente)
				clientes++;
			else if (persona instanceof Empleado)
				empleados++;
		}

		lista.append("─────────────────────────────────────────\n");
		lista.append("Total Clientes: ").append(clientes).append("\n");
		lista.append("Total Empleados: ").append(empleados).append("\n");
		lista.append("Total Personas: ").append(clientes + empleados).append("\n");

		return lista.toString();
	}

	/**
	 * Genera reporte de membresías activas vs vencidas.
	 */
	public static String generarReporteMembresias(Cliente[] clientes)
	{
		if (clientes == null || clientes.length == 0)
			return "No hay clientes registrados.";

		StringBuilder reporte = new StringBuilder();
		reporte.append("\n╔════════════════════════════════════════╗\n");
		reporte.append("║   REPORTE DE MEMBRESÍAS               ║\n");
		reporte.append("╚════════════════════════════════════════╝\n\n");

		int activas = 0;
		int vencidas = 0;
		int sinMembresia = 0;
		float ingresoTotal = 0.0f;

		for (Cliente cliente : clientes)
		{
			if (cliente == null)
				continue;

			Membresia membresia = cliente.obtenerMembresia();

			if (membresia == null)
			{
				sinMembresia++;
			}
			else if (membresia.verificarVigencia())
			{
				activas++;
				ingresoTotal += membresia.obtenerCosto(); // Polimorfismo por interfaz
			}
			else
			{
				vencidas++;
			}
		}

		reporte.append("Membresías Activas: ").append(activas).append("\n");
		reporte.append("Membresías Vencidas: ").append(vencidas).append("\n");
		reporte.append("Clientes sin Membresía: ").append(sinMembresia).append("\n");
		reporte.append("─────────────────────────────────────────\n");
		reporte.append("Ingreso por Membresías Activas: $");
		reporte.append(String.format("%,.2f", ingresoTotal)).append("\n");

		return reporte.toString();
	}

	/**
	 * Genera reporte de pagos recientes.
	 */
	public static String generarReportePagosRecientes(Cliente[] clientes, int dias)
	{
		if (clientes == null || clientes.length == 0)
			return "No hay clientes registrados.";

		StringBuilder reporte = new StringBuilder();
		reporte.append("\n╔════════════════════════════════════════╗\n");
		reporte.append("║   PAGOS RECIENTES (").append(dias).append(" días)          ║\n");
		reporte.append("╚════════════════════════════════════════╝\n\n");

		int totalPagosRecientes = 0;
		float montoTotal = 0.0f;

		for (Cliente cliente : clientes)
		{
			if (cliente == null)
				continue;

			Pago[] pagos = cliente.obtenerHistorialPagos();
			if (pagos == null)
				continue;

			for (Pago pago : pagos)
			{
				if (pago != null && pago.esReciente(dias))
				{
					reporte.append("• Cliente: ").append(cliente.obtenerNombre()).append("\n");
					reporte.append("  Monto: $").append(String.format("%.2f", pago.obtenerCosto()));
					reporte.append(" | Fecha: ").append(pago.obtenerFechaPago()).append("\n");

					totalPagosRecientes++;
					montoTotal += pago.obtenerCosto(); // Polimorfismo por interfaz
				}
			}
		}

		reporte.append("\n─────────────────────────────────────────\n");
		reporte.append("Total de Pagos: ").append(totalPagosRecientes).append("\n");
		reporte.append("Monto Total: $").append(String.format("%,.2f", montoTotal)).append("\n");

		return reporte.toString();
	}
}