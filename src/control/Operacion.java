package control;

public abstract class Operacion
{
	// DEFINIR EL CONTRATO GENERAL PARA CUALQUIER OPERACION CONTROLADA
	protected String mensajeResultado;
	// INDICAR SI LA OPERACION TERMINO CORRECTAMENTE
	protected boolean exitosa;

	public final boolean ejecutar()
	{
		// PASO 1: VALIDAR TODOS LOS DATOS NECESARIOS
		if (!validar())
		{
			mensajeResultado = "Validación fallida: " + obtenerMensajeError();
			exitosa = false;
			return false;
		}

		// PASO 2: REALIZAR LA OPERACION CON LOS DATOS ACEPTADOS
		boolean resultado = realizarOperacion();

		// PASO 3: CONFIRMAR O REVERTIR SEGUN EL RESULTADO
		if (resultado)
		{
			confirmar();
			exitosa = true;
		}
		else
		{
			revertir();
			exitosa = false;
		}

		return exitosa;
	}

	// DETERMINAR SI LA OPERACION ESTA LISTA PARA EJECUTAR
	protected abstract boolean validar();

	// EJECUTAR LA LOGICA CONCRETA DE LA OPERACION
	protected abstract boolean realizarOperacion();

	protected void confirmar()
	{
		// GUARDAR MENSAJE EXITOSO POR DEFECTO
		mensajeResultado = "Operación completada exitosamente.";
	}

	protected void revertir()
	{
		// GUARDAR MENSAJE DE FALLO Y AVISO DE REVERSA
		mensajeResultado = "Operación fallida. Cambios revertidos.";
	}

	protected String obtenerMensajeError()
	{
		// PROPORCIONAR UN MENSAJE GENERICO CUANDO NO HAY DETALLES
		return "Error en validación";
	}

	public String obtenerMensajeResultado()
	{
		// PERMITIR QUE CAPAS SUPERIORES LEAN EL RESULTADO
		return mensajeResultado;
	}

	public boolean fueExitosa()
	{
		// INFORMAR EL ESTADO FINAL DE LA OPERACION
		return exitosa;
	}
}