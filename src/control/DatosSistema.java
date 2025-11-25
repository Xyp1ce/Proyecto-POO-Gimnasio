package control;

import entidades.*;

public class DatosSistema
{
	// COORDINAR TODAS LAS COLECCIONES EN MEMORIA
	// DATOS EN MEMORIA
	private static Persona[] personas;
	private static Sucursal[] sucursales;
	private static SesionEntrenamiento[] sesiones;

	private static int totalPersonas;
	private static int totalSesiones;

	// INICIALIZACION ESTATICA
	static
	{
		// RESERVAR ESPACIO INICIAL PARA PERSONAS
		personas = new Persona[500]; // Capacidad inicial
		// CARGAR SUCURSALES DESDE LA CAPA PRINCIPAL
		sucursales = new Sucursal[0]; // Se inicializa desde Gimnasio
		// REPARTIR CAPACIDAD DE SESIONES EN MEMORIA
		sesiones = new SesionEntrenamiento[200];
		// AJUSTAR CONTADORES DESDE EL ARRANQUE
		totalPersonas = 0;
		totalSesiones = 0;
	}

	// METODOS PARA PERSONAS

	public static synchronized boolean agregarPersona(Persona persona)
	{
		// VALIDAR QUE EXISTA UNA REFERENCIA VALIDA
		if (persona == null)
			return false;

		// EVITAR SOBREPASAR LA CAPACIDAD RESERVADA
		if (totalPersonas >= personas.length)
			return false;

		// INSERTAR LA PERSONA Y ACTUALIZAR EL CONTEO
		personas[totalPersonas] = persona;
		totalPersonas++;
		return true;
	}

	public static synchronized Persona buscarPersona(long id)
	{
		// RECORRER SOLO LOS ESPACIOS OCUPADOS
		for (int i = 0; i < totalPersonas; i++)
		{
			// COMPARAR IDENTIFICADOR PARA ENCONTRAR EL REGISTRO
			if (personas[i] != null && personas[i].obtenerIdentificador() == id)
				return personas[i];
		}
		return null;
	}

	public static synchronized Persona[] obtenerPersonas()
	{
		// ENTREGAR LA REFERENCIA PARA CONSULTAS EXTERNAS
		return personas;
	}

	public static synchronized void definirPersonas(Persona[] nuevasPersonas)
	{
		// REEMPLAZAR LA COLECCION COMPLETA DESDE LA PERSISTENCIA
		personas = nuevasPersonas;
		// RECALCULAR LOS CONTADORES SEGUN EL NUEVO ARREGLO
		totalPersonas = contarNoNulos(nuevasPersonas);
	}

	public static synchronized int obtenerTotalPersonas()
	{
		// INFORMAR CUANTOS REGISTROS ESTAN ACTIVOS
		return totalPersonas;
	}

	// METODOS PARA SUCURSALES

	public static synchronized Sucursal[] obtenerSucursales()
	{
		// EXPONER LA LISTA DE SUCURSALES ACTUALES
		return sucursales;
	}

	public static synchronized void definirSucursales(Sucursal[] nuevasSucursales)
	{
		// REEMPLAZAR EL INVENTARIO DE SUCURSALES EN MEMORIA
		sucursales = nuevasSucursales;
	}

	// METODOS PARA SESIONES

	public static synchronized boolean agregarSesion(SesionEntrenamiento sesion)
	{
		// VALIDAR LA SESION RECIBIDA
		if (sesion == null)
			return false;

		// GARANTIZAR ESPACIO DISPONIBLE EN EL ARREGLO
		if (totalSesiones >= sesiones.length)
			return false;

		// REGISTRAR LA SESION Y SUMARLA AL TOTAL
		sesiones[totalSesiones] = sesion;
		totalSesiones++;
		return true;
	}

	public static synchronized SesionEntrenamiento[] obtenerSesiones()
	{
		// ENTREGAR LA REFERENCIA A TODAS LAS SESIONES
		return sesiones;
	}

	public static synchronized void definirSesiones(SesionEntrenamiento[] nuevasSesiones)
	{
		// SINCRONIZAR LAS SESIONES CON EL CONTENIDO NUEVO
		sesiones = nuevasSesiones;
		// CALCULAR CUANTAS SESIONES SON VALIDAS
		totalSesiones = contarNoNulos(nuevasSesiones);
	}

	public static synchronized int obtenerTotalSesiones()
	{
		// INFORMAR EL NUMERO DE SESIONES ALMACENADAS
		return totalSesiones;
	}

	// METODOS DE UTILIDAD

	private static int contarNoNulos(Object[] arreglo)
	{
		// PROTEGER CONTRA ARREGLOS NO INICIALIZADOS
		if (arreglo == null)
			return 0;

		int contador = 0;
		for (Object obj : arreglo)
		{
			// SUMAR SOLO LAS POSICIONES OCUPADAS
			if (obj != null)
				contador++;
		}
		return contador;
	}

	public static synchronized void limpiar()
	{
		// REINICIAR LOS ARREGLOS A SUS CAPACIDADES ORIGINALES
		personas = new Persona[500];
		sesiones = new SesionEntrenamiento[200];
		// REINICIAR LOS CONTADORES PARA COMENZAR DESDE CERO
		totalPersonas = 0;
		totalSesiones = 0;
	}
}