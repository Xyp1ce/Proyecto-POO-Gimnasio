package entidades;

import control.Gimnasio;

public class Cliente extends Persona
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS PARTICULARES
	private String tipoCliente;
	private int historialVisitas;
	private Membresia membresia;
	private Pago[] historialPagos;
	private int totalPagos;

	// CONSTRUCTORES
	public Cliente()
	{
		super();
		asignarIdentificadorCliente();
		historialVisitas = 0;
		inicializarHistorialPagos();
	}

	public Cliente(String nombre, String documento, String tipoCliente, long telefono)
	{
		super(nombre, documento, telefono);
		this.tipoCliente = tipoCliente;
		historialVisitas = 0;
		asignarIdentificadorCliente();
		inicializarHistorialPagos();
	}

	private void inicializarHistorialPagos()
	{
		historialPagos = new Pago[50]; // Capacidad inicial de 50 pagos
		totalPagos = 0;
	}

	// GETTERS Y SETTERS
	public String obtenerTipoCliente()
	{
		return tipoCliente;
	}

	public void definirTipoCliente(String tipoCliente)
	{
		this.tipoCliente = tipoCliente;
	}

	public int obtenerHistorialVisitas()
	{
		return historialVisitas;
	}

	public void definirHistorialVisitas(int historialVisitas)
	{
		this.historialVisitas = historialVisitas;
	}

	public Membresia obtenerMembresia()
	{
		return membresia;
	}

	public void definirMembresia(Membresia membresia)
	{
		this.membresia = membresia;
	}

	public Pago[] obtenerHistorialPagos()
	{
		return historialPagos;
	}

	public int obtenerTotalPagos()
	{
		return totalPagos;
	}

	// METODOS DE VISITAS
	public void registrarVisita()
	{
		historialVisitas++;
	}

	// METODOS DE MEMBRESIA

	// Verifica si el cliente tiene una membresía activa y vigente.
	public boolean estaActivaMembresia()
	{
		if (membresia == null)
			return false;

		return membresia.verificarVigencia();
	}

	// Asigna una nueva membresía al cliente.
	public boolean asignarMembresia(Membresia nuevaMembresia)
	{
		if (nuevaMembresia == null)
			return false;

		this.membresia = nuevaMembresia;
		return true;
	}

	// Renueva la membresía actual del cliente.
	public boolean renovarMembresia()
	{
		if (membresia == null)
			return false;

		membresia.renovar();
		return true;
	}

	// Obtiene el estado de la membresía en formato texto.
	public String obtenerEstadoMembresia()
	{
		if (membresia == null)
			return "Sin membresía";

		if (membresia.verificarVigencia())
		{
			long dias = membresia.obtenerDiasRestantes();
			return "Activa (" + dias + " días restantes)";
		}
		else
		{
			return "Vencida";
		}
	}

	// METODOS DE PAGOS

	// Registra un nuevo pago del cliente.
	public boolean realizarPago(Pago pago)
	{
		if (pago == null || !pago.validarPago())
			return false;

		if (totalPagos >= historialPagos.length)
			return false; // Historial lleno

		// Asegurar que el pago tenga el ID correcto del cliente
		pago.definirIdCliente(this.identificador);

		historialPagos[totalPagos] = pago;
		totalPagos++;

		return true;
	}

	// Calcula el total de pagos realizados por el cliente.
	public float calcularTotalPagado()
	{
		float total = 0.0f;

		for (int i = 0; i < totalPagos; i++)
		{
			if (historialPagos[i] != null)
			{
				total += historialPagos[i].obtenerMonto();
			}
		}

		return total;
	}

	// Obtiene el último pago realizado.
	public Pago obtenerUltimoPago()
	{
		if (totalPagos == 0)
			return null;

		return historialPagos[totalPagos - 1];
	}

	// Genera un reporte de todos los pagos del cliente.
	public String generarReportePagos()
	{
		if (totalPagos == 0)
			return "No hay pagos registrados.";

		StringBuilder reporte = new StringBuilder();
		reporte.append("\n===== HISTORIAL DE PAGOS =====\n");
		reporte.append("Cliente: ").append(nombre).append("\n");
		reporte.append("Total de pagos: ").append(totalPagos).append("\n");
		reporte.append("-------------------------------\n");

		for (int i = 0; i < totalPagos; i++)
		{
			if (historialPagos[i] != null)
			{
				reporte.append(historialPagos[i].toString()).append("\n");
			}
		}

		reporte.append("-------------------------------\n");
		reporte.append("Total pagado: $").append(String.format("%.2f", calcularTotalPagado()));
		reporte.append("\n================================\n");

		return reporte.toString();
	}

	// Verifica si el cliente está al día con sus pagos.
	public boolean estaAlDia()
	{
		return estaActivaMembresia();
	}

	// METODOS PRIVADOS
	private void asignarIdentificadorCliente()
	{
		long nuevoIdentificador;
		do
			nuevoIdentificador = (long) (Math.random() * 90000000L) + 10000000L;
		while (Gimnasio.validarIdCliente(nuevoIdentificador));

		definirIdentificador(nuevoIdentificador);
	}

	// REPRESENTACION
	@Override
	public String generarDescripcion()
	{
		String estadoMembresia = obtenerEstadoMembresia();

		return "Cliente{" +
				"identificador=" + obtenerIdentificador() +
				", nombre='" + obtenerNombre() + '\'' +
				", tipoCliente='" + tipoCliente + '\'' +
				", historialVisitas=" + historialVisitas +
				", telefono=" + obtenerTelefono() +
				", membresia=" + estadoMembresia +
				", totalPagos=" + totalPagos +
				", totalPagado=$" + String.format("%.2f", calcularTotalPagado()) +
				'}';
	}

	public String generarResumenCompleto()
	{
		StringBuilder resumen = new StringBuilder();

		resumen.append("\n========== CLIENTE ==========\n");
		resumen.append("ID: ").append(identificador).append("\n");
		resumen.append("Nombre: ").append(nombre).append("\n");
		resumen.append("Tipo: ").append(tipoCliente).append("\n");
		resumen.append("Teléfono: ").append(telefono).append("\n");
		resumen.append("Visitas: ").append(historialVisitas).append("\n");
		resumen.append("-----------------------------\n");

		if (membresia != null)
		{
			resumen.append("Membresía: ").append(membresia.resumen()).append("\n");
		}
		else
		{
			resumen.append("Membresía: Sin membresía\n");
		}

		resumen.append("Pagos realizados: ").append(totalPagos).append("\n");
		resumen.append("Total pagado: $").append(String.format("%.2f", calcularTotalPagado())).append("\n");
		resumen.append("Estado: ").append(estaAlDia() ? "✓ Al día" : "✗ Vencida").append("\n");
		resumen.append("=============================\n");

		return resumen.toString();
	}
}