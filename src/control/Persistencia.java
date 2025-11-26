package control;

import entidades.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//CLASE DONDE SE MANEJAN TODOS LOS METODOS PARA LA PERSISTENCIA
public class Persistencia
{
	// CENTRALIZAR LA CONFIGURACION DE ARCHIVOS DE TEXTO PARA MAS FACIL
	// RUTAS DE ARCHIVOS EN CONSTANTES NO MODIFICABLES
	private static final String CARPETA_DATA = resolverCarpetaData();
	private static final String ARCHIVO_PERSONAS = CARPETA_DATA + "/personas.txt";
	private static final String ARCHIVO_SUCURSALES = CARPETA_DATA + "/sucursales.txt";
	private static final String ARCHIVO_SESIONES = CARPETA_DATA + "/sesiones.txt";
	private static final String SEPARADOR = "|";

	public static synchronized void inicializar()
	{
		File carpeta = new File(CARPETA_DATA);

		// CREAR LA CARPETA BASE SI AUN NO EXISTE
		if (!carpeta.exists())
		{
			carpeta.mkdir();
		}

		// CREAR CADA ARCHIVO NECESARIO SI FALTA
		crearArchivoSiNoExiste(ARCHIVO_PERSONAS);
		crearArchivoSiNoExiste(ARCHIVO_SUCURSALES);
		crearArchivoSiNoExiste(ARCHIVO_SESIONES);
	}

	private static void crearArchivoSiNoExiste(String ruta)
	{
		File archivo = new File(ruta);
		if (!archivo.exists())
		{
			try
			{
				// CREAR EL ARCHIVO VACIO PARA QUE LOS SIGUIENTES GUARDADOS FUNCIONEN
				archivo.createNewFile();
			}
			catch (IOException e)
			{
				System.err.println("Error al crear archivo: " + ruta);
				e.printStackTrace();
			}
		}
	}

	// ==================== GUARDAR ====================

	//GUARDAR A TODAS LAS PERSONAS EN EL ARCHIVO
	/* Formato nuevo: CLIENTE|sucursalId|<payloadBase64>
	 *                EMPLEADO|sucursalId|<payloadBase64> */
	public static synchronized boolean guardarPersonas(Sucursal[] sucursales)
	{
		if (sucursales == null)
			sucursales = new Sucursal[0];

		StringBuilder contenido = new StringBuilder(1024);

		try
		{
			for (Sucursal sucursal : sucursales)
			{
				if (sucursal == null)
					continue;

				int sucursalId = sucursal.obtenerNumeroSucursal();

				Cliente[] clientes = sucursal.obtenerClientes();
				if (clientes != null)
				{
					for (Cliente cliente : clientes)
					{
						if (cliente == null)
							continue;

						String linea = "CLIENTE" + SEPARADOR +
								sucursalId + SEPARADOR +
								serializarObjeto(cliente);
						contenido.append(linea).append(System.lineSeparator());
					}
				}

				Empleado[] empleados = sucursal.obtenerEmpleados();
				if (empleados != null)
				{
					for (Empleado empleado : empleados)
					{
						if (empleado == null)
							continue;

						String linea = "EMPLEADO" + SEPARADOR +
								sucursalId + SEPARADOR +
								serializarObjeto(empleado);
						contenido.append(linea).append(System.lineSeparator());
					}
				}
			}

			escribirArchivo(ARCHIVO_PERSONAS, contenido.toString());
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Error al guardar personas");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Guarda todas las sucursales en el archivo data.
	 * Formato: SUCURSAL|id|nombre|ubicacion|horario|servicios|cuota
	 */
	public static synchronized boolean guardarSucursales(Sucursal[] sucursales)
	{
		// DETENER GUARDADO SI NO HAY INFORMACION
		if (sucursales == null)
			return false;

		StringBuilder contenido = new StringBuilder(512);

		try
		{
			for (Sucursal sucursal : sucursales)
			{
				if (sucursal == null)
					continue;

				// ESCRIBIR TODOS LOS CAMPOS DE LA SUCURSAL EN UNA SOLA LINEA
				String linea = "SUCURSAL" + SEPARADOR +
						sucursal.obtenerNumeroSucursal() + SEPARADOR +
						sucursal.obtenerNombreSucursal() + SEPARADOR +
						sucursal.obtenerUbicacionSucursal() + SEPARADOR +
						sucursal.obtenerHorarioOperacion() + SEPARADOR +
						sucursal.obtenerServiciosEspeciales() + SEPARADOR +
						sucursal.obtenerCuotaMensual();

				contenido.append(linea).append(System.lineSeparator());
			}

			escribirArchivo(ARCHIVO_SUCURSALES, contenido.toString());
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Error al guardar sucursales");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * GUARDAR TODAS LAS SESIONES EN EL ARCHIVO.
	 * Formato: SESION|id|clienteId|entrenadorId|fecha|duracionMin|descripcion
	 */
	public static synchronized boolean guardarSesiones(SesionEntrenamiento[] sesiones)
	{
		// CONFIRMAR QUE EXISTEN SESIONES PARA SERIALIZAR
		if (sesiones == null)
			return false;

		StringBuilder contenido = new StringBuilder(1024);

		try
		{
			for (SesionEntrenamiento sesion : sesiones)
			{
				if (sesion == null)
					continue;

				// ESCRIBIR IDENTIFICADORES Y METADATOS DE CADA SESION
				String linea = "SESION" + SEPARADOR +
						sesion.obtenerIdSesion() + SEPARADOR +
						sesion.obtenerIdCliente() + SEPARADOR +
						sesion.obtenerIdEntrenador() + SEPARADOR +
						sesion.obtenerFecha() + SEPARADOR +
						sesion.obtenerDuracionMinutos() + SEPARADOR +
						sesion.obtenerDescripcion();

				contenido.append(linea).append(System.lineSeparator());
			}

			escribirArchivo(ARCHIVO_SESIONES, contenido.toString());
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Error al guardar sesiones");
			e.printStackTrace();
			return false;
		}
	}

	private static void escribirArchivo(String ruta, String contenido) throws IOException
	{
		Files.write(Paths.get(ruta), contenido.getBytes(StandardCharsets.UTF_8));
	}

	// ==================== CARGAR ====================

	// CARGAR TODAS LAS PERSONAS DESDE EL ARCHIVO
	public static synchronized Persona[] cargarPersonas(Sucursal[] sucursales)
	{
		// PREPARAR UN ARREGLO AMPLIO PARA RECIBIR LOS REGISTROS
		Persona[] personas = new Persona[500];
		int indice = 0;
		BufferedReader reader = null;
		Map<Integer, Sucursal> mapaSucursales = indexarSucursales(sucursales);

		try
		{
			reader = new BufferedReader(new FileReader(ARCHIVO_PERSONAS));
			String linea;

			while ((linea = reader.readLine()) != null)
			{
				if (linea.trim().isEmpty())
					continue;

				// DIVIDIR LA LINEA SEGUN EL SEPARADOR PARA IDENTIFICAR CAMPOS
				String[] partes = linea.split("\\" + SEPARADOR);

				if (partes.length >= 3)
				{
					Persona reconstruida = reconstruirPersonaNueva(partes, mapaSucursales);
					if (reconstruida != null && indice < personas.length)
						personas[indice++] = reconstruida;
					continue;
				}

				if (partes.length >= 5)
				{
					Persona legacy = reconstruirPersonaLegacy(partes);
					if (legacy != null && indice < personas.length)
						personas[indice++] = legacy;
				}
			}

			return personas;
		}
		catch (IOException e)
		{
			System.err.println("Error al cargar personas");
			e.printStackTrace();
			return new Persona[500];
		}
		catch (NumberFormatException e)
		{
			System.err.println("Error de formato al cargar personas");
			e.printStackTrace();
			return new Persona[500];
		}
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// CARGAR TODAS LAS SUCURSALES DESDE EL ARCHIVO
	public static synchronized Sucursal[] cargarSucursales()
	{
		// UTILIZAR UN ARREGLO TEMPORAL Y LUEGO AJUSTAR SU TAMANO
		Sucursal[] sucursales = new Sucursal[50];
		int indice = 0;
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(ARCHIVO_SUCURSALES));
			String linea;

			while ((linea = reader.readLine()) != null)
			{
				if (linea.trim().isEmpty())
					continue;

				// DIVIDIR CADA REGISTRO PARA RECONSTRUIR LA SUCURSAL
				String[] partes = linea.split("\\" + SEPARADOR);

				if (partes.length < 7)
					continue;

				// SUCURSAL|id|nombre|ubicacion|horario|servicios|cuota
				int id = Integer.parseInt(partes[1]);
				String nombre = partes[2];
				String ubicacion = partes[3];
				String horario = partes[4];
				String servicios = partes[5];
				float cuota = Float.parseFloat(partes[6]);

				Sucursal sucursal = new Sucursal(id, nombre, horario, ubicacion, servicios, cuota);
				sucursales[indice++] = sucursal;
			}

			// REDIMENSIONAR AL TAMANO REAL PARA EVITAR ESPACIOS VACIOS
			Sucursal[] resultado = new Sucursal[indice];
			for (int i = 0; i < indice; i++)
			{
				resultado[i] = sucursales[i];
			}

			return resultado;
		}
		catch (IOException e)
		{
			System.err.println("Error al cargar sucursales");
			e.printStackTrace();
			return new Sucursal[0];
		}
		catch (NumberFormatException e)
		{
			System.err.println("Error de formato al cargar sucursales");
			e.printStackTrace();
			return new Sucursal[0];
		}
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// CARGAR TODAS LAS SESIONES DESDE EL ARCHIVO
	public static synchronized SesionEntrenamiento[] cargarSesiones()
	{
		// RESERVAR UN ARREGLO GRANDE PARA SESIONES Y POBLARLO DESDE ARCHIVO
		SesionEntrenamiento[] sesiones = new SesionEntrenamiento[200];
		int indice = 0;
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(ARCHIVO_SESIONES));
			String linea;

			while ((linea = reader.readLine()) != null)
			{
				if (linea.trim().isEmpty())
					continue;

				// DIVIDIR LOS CAMPOS PARA RECONSTRUIR CADA SESION
				String[] partes = linea.split("\\" + SEPARADOR);

				if (partes.length < 7)
					continue;

				// SESION|id|clienteId|entrenadorId|fecha|duracionMin|descripcion
				long id = Long.parseLong(partes[1]);
				long clienteId = Long.parseLong(partes[2]);
				long entrenadorId = Long.parseLong(partes[3]);
				String fecha = partes[4];
				int duracion = Integer.parseInt(partes[5]);
				String descripcion = partes[6];

				SesionEntrenamiento sesion = new SesionEntrenamiento(clienteId, entrenadorId, duracion, descripcion);
				sesion.definirIdSesion(id);
				sesion.definirFecha(fecha);
				sesiones[indice++] = sesion;
			}

			return sesiones;
		}
		catch (IOException e)
		{
			System.err.println("Error al cargar sesiones");
			e.printStackTrace();
			return new SesionEntrenamiento[200];
		}
		catch (NumberFormatException e)
		{
			System.err.println("Error de formato al cargar sesiones");
			e.printStackTrace();
			return new SesionEntrenamiento[200];
		}
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// ==================== METODOS DE UTILIDAD ====================

	// GUARDAR TODOS LOS DATOS DEL SISTEMA
	public static synchronized boolean guardarTodo()
	{
		Sucursal[] sucursales = Gimnasio.obtenerSucursales();
		if (sucursales == null)
			sucursales = new Sucursal[0];

		boolean exitoPersonas = guardarPersonas(sucursales);
		boolean exitoSucursales = guardarSucursales(sucursales);
		boolean exitoSesiones = guardarSesiones(DatosSistema.obtenerSesiones());

		return exitoPersonas && exitoSucursales && exitoSesiones;
	}

	//CARGAR TODOS LOS ARCHIVOS DEL SISTEMA
	public static synchronized boolean cargarTodo()
	{
		try
		{
			Sucursal[] sucursales = cargarSucursales();
			DatosSistema.definirSucursales(sucursales);

			Persona[] personas = cargarPersonas(sucursales);
			SesionEntrenamiento[] sesiones = cargarSesiones();

			DatosSistema.definirPersonas(personas);
			DatosSistema.definirSesiones(sesiones);

			return true;
		}
		catch (Exception e)
		{
			System.err.println("Error al cargar datos del sistema");
			e.printStackTrace();
			return false;
		}
	}

	private static String serializarObjeto(Serializable objeto) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(baos))
		{
			oos.writeObject(objeto);
		}
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	private static <T> T deserializarObjeto(String base64, Class<T> tipo) throws IOException, ClassNotFoundException
	{
		byte[] datos = Base64.getDecoder().decode(base64);
		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(datos)))
		{
			Object objeto = ois.readObject();
			return tipo.cast(objeto);
		}
	}

	private static Map<Integer, Sucursal> indexarSucursales(Sucursal[] sucursales)
	{
		Map<Integer, Sucursal> mapa = new HashMap<>();
		if (sucursales == null)
			return mapa;

		for (Sucursal sucursal : sucursales)
		{
			if (sucursal != null)
				mapa.put(sucursal.obtenerNumeroSucursal(), sucursal);
		}
		return mapa;
	}

	private static Persona reconstruirPersonaNueva(String[] partes, Map<Integer, Sucursal> mapaSucursales)
	{
		if (partes.length < 3 || partes[0] == null)
			return null;

		try
		{
			String tipo = partes[0];
			int sucursalId = Integer.parseInt(partes[1]);
			String payload = partes[2];

			if ("CLIENTE".equals(tipo))
			{
				Cliente cliente = deserializarObjeto(payload, Cliente.class);
				asignarClienteASucursal(mapaSucursales.get(sucursalId), cliente);
				return cliente;
			}
			else if ("EMPLEADO".equals(tipo))
			{
				Empleado empleado = deserializarObjeto(payload, Empleado.class);
				asignarEmpleadoASucursal(mapaSucursales.get(sucursalId), empleado);
				return empleado;
			}
		}
		catch (NumberFormatException | IOException | ClassNotFoundException ex)
		{
			System.err.println("Registro de persona inválido: " + String.join(SEPARADOR, partes));
			ex.printStackTrace();
		}
		return null;
	}

	private static void asignarClienteASucursal(Sucursal sucursal, Cliente cliente)
	{
		if (sucursal == null || cliente == null)
			return;

		if (!sucursal.agregarCliente(cliente))
			System.err.println("No se pudo reasignar cliente " + cliente.obtenerIdentificador());
	}

	private static void asignarEmpleadoASucursal(Sucursal sucursal, Empleado empleado)
	{
		if (sucursal == null || empleado == null)
			return;

		if (!sucursal.registrarEmpleado(empleado))
			System.err.println("No se pudo reasignar empleado " + empleado.obtenerIdentificador());
	}

	private static Persona reconstruirPersonaLegacy(String[] partes)
	{
		try
		{
			String tipo = partes[0];
			if ("CLIENTE".equals(tipo) && partes.length >= 5)
			{
				long id = Long.parseLong(partes[1]);
				String nombre = partes[2];
				String documento = partes[3];
				long telefono = Long.parseLong(partes[4]);
				Cliente cliente = new Cliente(nombre, documento, "Regular", telefono);
				cliente.definirIdentificador(id);
				return cliente;
			}

			if ("EMPLEADO".equals(tipo) && partes.length >= 6)
			{
				long id = Long.parseLong(partes[1]);
				String rol = partes[2];
				String nombre = partes[3];
				String documento = partes[4];
				long telefono = Long.parseLong(partes[5]);

				Empleado empleado = null;
				if ("ENTRENADOR".equals(rol))
					empleado = new Entrenador("Entrenador", nombre, telefono, "", "", "", "");
				else if ("LIMPIEZA".equals(rol))
					empleado = new Limpieza("Limpieza", nombre, telefono, "", "");
				else if ("SERVICIO_AL_CLIENTE".equals(rol))
					empleado = new ServicioAlCliente("Servicio", nombre, telefono, "", "", 0);

				if (empleado != null)
				{
					empleado.definirIdentificador(id);
					empleado.definirDocumento(documento);
					return empleado;
				}
			}
		}
		catch (NumberFormatException ex)
		{
			System.err.println("Error de formato legacy en personas.txt");
			ex.printStackTrace();
		}
		return null;
	}

	private static String resolverCarpetaData()
	{
		File cwd = new File(System.getProperty("user.dir"));
		if ("bin".equalsIgnoreCase(cwd.getName()) && cwd.getParentFile() != null)
		{
			// Cuando la app se ejecuta desde bin/, redirigir a la carpeta de nivel superior
			return new File(cwd.getParentFile(), "data").getPath();
		}

		return new File(cwd, "data").getPath();
	}
}