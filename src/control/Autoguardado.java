package control;

public class Autoguardado extends Thread
{
	// DEFINIR RITMO DE GUARDADO PERIODICO
	private static long INTERVALO_MS = 40000L;
	// CONTROLAR SI EL HILO CONTINUA TRABAJANDO
	private volatile boolean ejecutando = true;

	public Autoguardado()
	{
		// EVITAR BLOQUEAR EL CIERRE DEL PROGRAMA
		// ESTABLECER ESTE PARAMETRO IMPITE QUE SE CIERRE LA EJECUCION EN JVM SI 
		// SE INTERRUMPE EL HILO
		// NO ES NECESARIO, PERO POR SI ACASO
		setDaemon(true);
	}

	@Override
	public void run()
	{
		// CICLAR HASTA QUE EL SISTEMA SOLICITE DETENER EL HILO
		while (ejecutando)
		{
			// SINCRONIZAR DATOS EN MEMORIA CON LOS ARCHIVOS
			guardarDatos();
			// ESPERAR EL LAPSO DEFINIDO ANTES DEL SIGUIENTE GUARDADO
			esperarIntervalo();
		}
	}

	private void guardarDatos()
	{
		// REPLICAR LAS SUCURSALES ACTUALES EN EL CONTENEDOR GLOBAL
		DatosSistema.definirSucursales(Gimnasio.obtenerSucursales());
		// GUARDAR TODO EL ESTADO DEL SISTEMA EN ALMACENAMIENTO
		boolean exito = Persistencia.guardarTodo();
		System.out.println("Autoguardado del sistema");

		if (!exito)
			// MENSAJE DE ERROR SOBRE UN GUARDADO FALLIDO
			System.err.println("AutoGuardadoThread: No se pudieron guardar los datos.");
	}

	private void esperarIntervalo()
	{
		try
		{
			// PAUSAR EL HILO HASTA EL PROXIMO CICLO
			Thread.sleep(INTERVALO_MS);
		}
		catch (InterruptedException ex)
		{
			// FINALIZAR PAUSA CUANDO DETENER INTERRUMPE EL SLEEP
		}
	}

	public void detener()
	{
		// CORTAR EL BUCLE PRINCIPAL
		ejecutando = false;
		// INTERRUMPIR EL SLEEP ACTUAL PARA CERRAR DE INMEDIATO
		interrupt();
	}
}
