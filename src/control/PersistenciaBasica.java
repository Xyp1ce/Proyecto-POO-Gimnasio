package control;

import entidades.*;
import java.io.*;

/**
 * Clase que centraliza la persistencia en archivos TXT.
 * Formato: campos separados por | (pipe)
 * Archivos: data/personas.txt, data/sucursales.txt, data/sesiones.txt
 */
public class PersistenciaBasica
{
	// RUTAS DE ARCHIVOS
	private static final String CARPETA_DATA = "data";
	private static final String ARCHIVO_PERSONAS = CARPETA_DATA + "/personas.txt";
	private static final String ARCHIVO_SUCURSALES = CARPETA_DATA + "/sucursales.txt";
	private static final String ARCHIVO_SESIONES = CARPETA_DATA + "/sesiones.txt";
	private static final String SEPARADOR = "|";

	public static synchronized void inicializar()
	{
		File carpeta = new File(CARPETA_DATA);

		// Crear carpeta si no existe
		if (!carpeta.exists())
		{
			carpeta.mkdir();
		}

		// Crear archivos si no existen
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

	/**
	 * Guarda todas las personas en el archivo.
	 * Formato: CLIENTE|id|nombre|documento|telefono
	 *          EMPLEADO|id|ROL|nombre|documento|telefono
	 */
	public static synchronized boolean guardarPersonas(Persona[] personas)
	{
		if (personas == null)
			return false;

		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(ARCHIVO_PERSONAS));

			for (Persona persona : personas)
			{
				if (persona == null)
					continue;

				if (persona instanceof Cliente)
				{
					Cliente cliente = (Cliente) persona;
					String linea = "CLIENTE" + SEPARADOR +
							cliente.obtenerIdentificador() + SEPARADOR +
							cliente.obtenerNombre() + SEPARADOR +
							cliente.obtenerDocumento() + SEPARADOR +
							cliente.obtenerTelefono();
					writer.write(linea);
					writer.newLine();
				}
				else if (persona instanceof Empleado)
				{
					Empleado empleado = (Empleado) persona;
					String rol = "EMPLEADO";

					if (empleado instanceof Entrenador)
						rol = "ENTRENADOR";
					else if (empleado instanceof Limpieza)
						rol = "LIMPIEZA";
					else if (empleado instanceof ServicioAlCliente)
						rol = "SERVICIO_AL_CLIENTE";

					String linea = "EMPLEADO" + SEPARADOR +
							empleado.obtenerIdentificador() + SEPARADOR +
							rol + SEPARADOR +
							empleado.obtenerNombre() + SEPARADOR +
							empleado.obtenerDocumento() + SEPARADOR +
							empleado.obtenerTelefono();
					writer.write(linea);
					writer.newLine();
				}
			}

			writer.flush();
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Error al guardar personas");
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Guarda todas las sucursales en el archivo.
	 * Formato: SUCURSAL|id|nombre|ubicacion|horario|servicios|cuota
	 */
	public static synchronized boolean guardarSucursales(Sucursal[] sucursales)
	{
		if (sucursales == null)
			return false;

		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(ARCHIVO_SUCURSALES));

			for (Sucursal sucursal : sucursales)
			{
				if (sucursal == null)
					continue;

				String linea = "SUCURSAL" + SEPARADOR +
						sucursal.obtenerNumeroSucursal() + SEPARADOR +
						sucursal.obtenerNombreSucursal() + SEPARADOR +
						sucursal.obtenerUbicacionSucursal() + SEPARADOR +
						sucursal.obtenerHorarioOperacion() + SEPARADOR +
						sucursal.obtenerServiciosEspeciales() + SEPARADOR +
						sucursal.obtenerCuotaMensual();

				writer.write(linea);
				writer.newLine();
			}

			writer.flush();
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Error al guardar sucursales");
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Guarda todas las sesiones en el archivo.
	 * Formato: SESION|id|clienteId|entrenadorId|fecha|duracionMin|descripcion
	 */
	public static synchronized boolean guardarSesiones(SesionEntrenamiento[] sesiones)
	{
		if (sesiones == null)
			return false;

		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter(new FileWriter(ARCHIVO_SESIONES));

			for (SesionEntrenamiento sesion : sesiones)
			{
				if (sesion == null)
					continue;

				String linea = "SESION" + SEPARADOR +
						sesion.obtenerIdSesion() + SEPARADOR +
						sesion.obtenerIdCliente() + SEPARADOR +
						sesion.obtenerIdEntrenador() + SEPARADOR +
						sesion.obtenerFecha() + SEPARADOR +
						sesion.obtenerDuracionMinutos() + SEPARADOR +
						sesion.obtenerDescripcion();

				writer.write(linea);
				writer.newLine();
			}

			writer.flush();
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Error al guardar sesiones");
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// ==================== CARGAR ====================

	/**
	 * Carga todas las personas desde el archivo.
	 */
	public static synchronized Persona[] cargarPersonas()
	{
		Persona[] personas = new Persona[500];
		int indice = 0;
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(ARCHIVO_PERSONAS));
			String linea;

			while ((linea = reader.readLine()) != null)
			{
				if (linea.trim().isEmpty())
					continue;

				String[] partes = linea.split("\\" + SEPARADOR);

				if (partes.length < 5)
					continue;

				String tipo = partes[0];

				if (tipo.equals("CLIENTE"))
				{
					// CLIENTE|id|nombre|documento|telefono
					long id = Long.parseLong(partes[1]);
					String nombre = partes[2];
					String documento = partes[3];
					long telefono = Long.parseLong(partes[4]);

					Cliente cliente = new Cliente(nombre, documento, "Regular", telefono);
					cliente.definirIdentificador(id);
					personas[indice++] = cliente;
				}
				else if (tipo.equals("EMPLEADO") && partes.length >= 6)
				{
					// EMPLEADO|id|ROL|nombre|documento|telefono
					long id = Long.parseLong(partes[1]);
					String rol = partes[2];
					String nombre = partes[3];
					String documento = partes[4];
					long telefono = Long.parseLong(partes[5]);

					Empleado empleado = null;

					if (rol.equals("ENTRENADOR"))
					{
						empleado = new Entrenador("Entrenador", nombre, telefono, "", "", "", "");
					}
					else if (rol.equals("LIMPIEZA"))
					{
						empleado = new Limpieza("Limpieza", nombre, telefono, "", "");
					}
					else if (rol.equals("SERVICIO_AL_CLIENTE"))
					{
						empleado = new ServicioAlCliente("Servicio", nombre, telefono, "", "", 0);
					}

					if (empleado != null)
					{
						empleado.definirIdentificador(id);
						empleado.definirDocumento(documento);
						personas[indice++] = empleado;
					}
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

	/**
	 * Carga todas las sucursales desde el archivo.
	 */
	public static synchronized Sucursal[] cargarSucursales()
	{
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

			// Redimensionar al tamaño real
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

	/**
	 * Carga todas las sesiones desde el archivo.
	 */
	public static synchronized SesionEntrenamiento[] cargarSesiones()
	{
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

	/**
	 * Guarda todos los datos del sistema.
	 */
	public static synchronized boolean guardarTodo()
	{
		boolean exitoPersonas = guardarPersonas(DatosSistema.obtenerPersonas());
		boolean exitoSucursales = guardarSucursales(DatosSistema.obtenerSucursales());
		boolean exitoSesiones = guardarSesiones(DatosSistema.obtenerSesiones());

		return exitoPersonas && exitoSucursales && exitoSesiones;
	}

	/**
	 * Carga todos los datos del sistema.
	 */
	public static synchronized boolean cargarTodo()
	{
		try
		{
			Persona[] personas = cargarPersonas();
			Sucursal[] sucursales = cargarSucursales();
			SesionEntrenamiento[] sesiones = cargarSesiones();

			DatosSistema.definirPersonas(personas);
			DatosSistema.definirSucursales(sucursales);
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
}