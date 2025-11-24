package control;

import entidades.*;

public class DatosSistema
{
	// DATOS EN MEMORIA
	private static Persona[] personas;
	private static Sucursal[] sucursales;
	private static SesionEntrenamiento[] sesiones;

	private static int totalPersonas;
	private static int totalSesiones;

	// INICIALIZACION ESTATICA
	static
	{
		personas = new Persona[500]; // Capacidad inicial
		sucursales = new Sucursal[0]; // Se inicializa desde Gimnasio
		sesiones = new SesionEntrenamiento[200];
		totalPersonas = 0;
		totalSesiones = 0;
	}

	// METODOS PARA PERSONAS

	public static synchronized boolean agregarPersona(Persona persona)
	{
		if (persona == null)
			return false;

		if (totalPersonas >= personas.length)
			return false;

		personas[totalPersonas] = persona;
		totalPersonas++;
		return true;
	}

	public static synchronized Persona buscarPersona(long id)
	{
		for (int i = 0; i < totalPersonas; i++)
		{
			if (personas[i] != null && personas[i].obtenerIdentificador() == id)
				return personas[i];
		}
		return null;
	}

	public static synchronized Persona[] obtenerPersonas()
	{
		return personas;
	}

	public static synchronized void definirPersonas(Persona[] nuevasPersonas)
	{
		personas = nuevasPersonas;
		totalPersonas = contarNoNulos(nuevasPersonas);
	}

	public static synchronized int obtenerTotalPersonas()
	{
		return totalPersonas;
	}

	// METODOS PARA SUCURSALES

	public static synchronized Sucursal[] obtenerSucursales()
	{
		return sucursales;
	}

	public static synchronized void definirSucursales(Sucursal[] nuevasSucursales)
	{
		sucursales = nuevasSucursales;
	}

	// METODOS PARA SESIONES

	public static synchronized boolean agregarSesion(SesionEntrenamiento sesion)
	{
		if (sesion == null)
			return false;

		if (totalSesiones >= sesiones.length)
			return false;

		sesiones[totalSesiones] = sesion;
		totalSesiones++;
		return true;
	}

	public static synchronized SesionEntrenamiento[] obtenerSesiones()
	{
		return sesiones;
	}

	public static synchronized void definirSesiones(SesionEntrenamiento[] nuevasSesiones)
	{
		sesiones = nuevasSesiones;
		totalSesiones = contarNoNulos(nuevasSesiones);
	}

	public static synchronized int obtenerTotalSesiones()
	{
		return totalSesiones;
	}

	// METODOS DE UTILIDAD

	private static int contarNoNulos(Object[] arreglo)
	{
		if (arreglo == null)
			return 0;

		int contador = 0;
		for (Object obj : arreglo)
		{
			if (obj != null)
				contador++;
		}
		return contador;
	}

	public static synchronized void limpiar()
	{
		personas = new Persona[500];
		sesiones = new SesionEntrenamiento[200];
		totalPersonas = 0;
		totalSesiones = 0;
	}
}