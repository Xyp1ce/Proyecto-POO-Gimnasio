package control;

class OperacionRegistrarPago extends Operacion
{
	private entidades.Cliente cliente;
	private entidades.Pago pago;
	private String mensajeError;

	public OperacionRegistrarPago(entidades.Cliente cliente, entidades.Pago pago)
	{
		this.cliente = cliente;
		this.pago = pago;
		this.mensajeError = "";
	}

	@Override
	protected boolean validar()
	{
		// Validar que el cliente exista
		if (cliente == null)
		{
			mensajeError = "Cliente no encontrado";
			return false;
		}

		// Validar que el pago sea válido
		if (pago == null || !pago.validarPago())
		{
			mensajeError = "Datos del pago inválidos";
			return false;
		}

		// Validar que el monto sea positivo
		if (pago.obtenerMonto() <= 0)
		{
			mensajeError = "El monto debe ser mayor a cero";
			return false;
		}

		return true;
	}

	@Override
	protected boolean realizarOperacion()
	{
		// Registrar el pago en el cliente
		return cliente.realizarPago(pago);
	}

	@Override
	protected void confirmar()
	{
		mensajeResultado = "Pago registrado exitosamente.\n" +
				"Referencia: " + pago.obtenerReferencia() + "\n" +
				"Monto: $" + String.format("%.2f", pago.obtenerMonto());
	}

	@Override
	protected void revertir()
	{
		mensajeResultado = "No se pudo registrar el pago. Verifique los datos.";
	}

	@Override
	protected String obtenerMensajeError()
	{
		return mensajeError;
	}
}