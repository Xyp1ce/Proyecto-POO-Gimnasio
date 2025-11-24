package control;

public abstract class Operacion
{
	protected String mensajeResultado;
	protected boolean exitosa;

	public final boolean ejecutar()
	{
		// Paso 1: Validar
		if (!validar())
		{
			mensajeResultado = "Validación fallida: " + obtenerMensajeError();
			exitosa = false;
			return false;
		}

		// Paso 2: Realizar operación
		boolean resultado = realizarOperacion();

		// Paso 3: Confirmar
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

	protected abstract boolean validar();

	protected abstract boolean realizarOperacion();

	protected void confirmar()
	{
		mensajeResultado = "Operación completada exitosamente.";
	}

	protected void revertir()
	{
		mensajeResultado = "Operación fallida. Cambios revertidos.";
	}

	protected String obtenerMensajeError()
	{
		return "Error en validación";
	}

	public String obtenerMensajeResultado()
	{
		return mensajeResultado;
	}

	public boolean fueExitosa()
	{
		return exitosa;
	}
}