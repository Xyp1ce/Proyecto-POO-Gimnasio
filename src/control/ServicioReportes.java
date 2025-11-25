package control;

import entidades.*;

public class ServicioReportes
{
	// GENERAR UN REPORTE CONSOLIDADO DE OBJETOS REPORTABLE USANDO POLIMORFISMO
	public static String generarReporteConsolidado(Reportable[] objetos)
	{
		// VALIDAR QUE EXISTAN OBJETOS PARA PROCESAR
		if (objetos == null || objetos.length == 0)
			return "No hay objetos para reportar.";

		StringBuilder consolidado = new StringBuilder();
		consolidado.append("\n╔════════════════════════════════════════╗\n");
		consolidado.append("║   REPORTE CONSOLIDADO DEL SISTEMA     ║\n");
		consolidado.append("╚════════════════════════════════════════╝\n\n");

		int contador = 0;
		for (Reportable objeto : objetos)
		{
			// EVITAR NULOS PARA APROVECHAR EL POLIMORFISMO
			if (objeto != null)
			{
				consolidado.append("--- Item ").append(++contador).append(" ---\n");
				// DELEGAR LA GENERACION DEL REPORTE A CADA OBJETO
				consolidado.append(objeto.generarReporte());
				consolidado.append("\n");
			}
		}

		consolidado.append("Total de items reportados: ").append(contador).append("\n");
		return consolidado.toString();
	}

	// CALCULAR COSTO TOTAL DE OBJETOS CONCOSTO USANDO POLIMORFISMO
	public static float calcularCostoTotal(ConCosto[] objetos)
	{
		// RETORNAR CERO CUANDO NO HAY ELEMENTOS
		if (objetos == null || objetos.length == 0)
			return 0.0f;

		float total = 0.0f;
		for (ConCosto objeto : objetos)
		{
			// SUMAR SOLAMENTE LOS OBJETOS VALIDOS
			if (objeto != null)
			{
				total += objeto.obtenerCosto();
			}
		}

		return total;
	}

	// GENERAR REPORTE DETALLADO DE COSTOS PARA CADA OBJETO CONCOSTO
	public static String generarReporteCostos(ConCosto[] objetos)
	{
		// VALIDAR QUE EXISTAN COSTOS PARA MOSTRAR
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
			// IDENTIFICAR EL TIPO CON REFLEXION PARA HACER EL REPORTE CLARO
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

	// LISTAR TODAS LAS PERSONAS DEL SISTEMA APLICANDO POLIMORFISMO POR HERENCIA
	public static String listarPersonas(Persona[] personas)
	{
		// MANEJAR EL CASO SIN PERSONAS REGISTRADAS
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

			// APROVECHAR POLIMORFISMO PARA IMPRIMIR LA DESCRIPCION CORRESPONDIENTE
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

	// GENERAR REPORTE DE MEMBRESIAS ACTIVAS VS VENCIDAS
	public static String generarReporteMembresias(Cliente[] clientes)
	{
		// VERIFICAR QUE EXISTAN CLIENTES PARA ANALIZAR
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
				// UTILIZAR LA INTERFAZ CONCOSTO PARA SUMAR INGRESOS
				ingresoTotal += membresia.obtenerCosto();
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

	// GENERAR REPORTE DE PAGOS RECIENTES PARA UN RANGO DE DIAS
	public static String generarReportePagosRecientes(Cliente[] clientes, int dias)
	{
		// DETECTAR AUSENCIA DE CLIENTES PARA EVITAR PROCESAMIENTO INUTIL
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

					// ACUMULAR EL TOTAL PARA RESUMIR EL PERIODO
					totalPagosRecientes++;
					montoTotal += pago.obtenerCosto();
				}
			}
		}

		reporte.append("\n─────────────────────────────────────────\n");
		reporte.append("Total de Pagos: ").append(totalPagosRecientes).append("\n");
		reporte.append("Monto Total: $").append(String.format("%,.2f", montoTotal)).append("\n");

		return reporte.toString();
	}
}