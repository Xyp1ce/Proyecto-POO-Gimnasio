package control;

class OperacionRegistrarPago extends Operacion
{
	// RETENER EL CLIENTE QUE RECIBE EL MOVIMIENTO
	private entidades.Cliente cliente;
	// GUARDAR LOS DETALLES DEL PAGO A PROCESAR
	private entidades.Pago pago;
	// DESCRIBIR LA RAZON DE UNA VALIDACION FALLIDA
	private String mensajeError;

	public OperacionRegistrarPago(entidades.Cliente cliente, entidades.Pago pago)
	{
		// ASIGNAR LAS REFERENCIAS NECESARIAS PARA LA OPERACION
		this.cliente = cliente;
		this.pago = pago;
		this.mensajeError = "";
	}

	@Override
	protected boolean validar()
	{
		// VALIDAR QUE EL CLIENTE EXISTA
		if (cliente == null)
		{
			mensajeError = "Cliente no encontrado";
			return false;
		}

		// VALIDAR QUE EL PAGO SEA VALIDO
		if (pago == null || !pago.validarPago())
		{
			mensajeError = "Datos del pago inválidos";
			return false;
		}

		// VALIDAR QUE EL MONTO SEA POSITIVO
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
		// REGISTRAR EL PAGO EN EL HISTORIAL DEL CLIENTE
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
		// INDICAR QUE NO SE COMPLETO EL REGISTRO
		mensajeResultado = "No se pudo registrar el pago. Verifique los datos.";
	}

	@Override
	protected String obtenerMensajeError()
	{
		// DEVOLVER LA DESCRIPCION ACUMULADA DURANTE LA VALIDACION
		return mensajeError;
	}
}