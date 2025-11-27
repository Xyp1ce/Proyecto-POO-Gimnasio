package control;

import entidades.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// CLASE DONDE SE MANEJAN TODOS LOS METODOS PARA LA PERSISTENCIA
public class Persistencia
{
	private static final Path CARPETA_DATA = resolverCarpetaData();
	private static final Path ARCHIVO_PERSONAS = CARPETA_DATA.resolve("personas.txt");
	private static final Path ARCHIVO_SUCURSALES = CARPETA_DATA.resolve("sucursales.txt");
	private static final Path ARCHIVO_SESIONES = CARPETA_DATA.resolve("sesiones.txt");

	public static synchronized void inicializar()
	{
		try
		{
			Files.createDirectories(CARPETA_DATA);
			crearArchivoSiNoExiste(ARCHIVO_PERSONAS);
			crearArchivoSiNoExiste(ARCHIVO_SUCURSALES);
			crearArchivoSiNoExiste(ARCHIVO_SESIONES);
		}
		catch (IOException e)
		{
			System.out.println("fin de lectura");
		}
	}

	private static void crearArchivoSiNoExiste(Path ruta) throws IOException
	{
		if (Files.notExists(ruta))
		{
			Files.createFile(ruta);
		}
	}

	// ==================== GUARDAR ====================

	public static synchronized boolean guardarPersonas(Sucursal[] sucursales)
	{
		RegistroPersona[] registros = prepararRegistrosPersonas(sucursales);
		return escribirObjeto(ARCHIVO_PERSONAS, registros);
	}

	public static synchronized boolean guardarSucursales(Sucursal[] sucursales)
	{
		RegistroSucursal[] registros = prepararRegistrosSucursales(sucursales);
		return escribirObjeto(ARCHIVO_SUCURSALES, registros);
	}

	public static synchronized boolean guardarSesiones(SesionEntrenamiento[] sesiones)
	{
		SesionEntrenamiento[] datos = sesiones == null ? new SesionEntrenamiento[0] : sesiones;
		return escribirObjeto(ARCHIVO_SESIONES, datos);
	}

	private static boolean escribirObjeto(Path ruta, Object objeto)
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(ruta)))
		{
			oos.writeObject(objeto);
			return true;
		}
		catch (IOException e)
		{
			System.out.println("fin de lectura");
			return false;
		}
	}

	// ==================== CARGAR ====================

	public static synchronized Persona[] cargarPersonas(Sucursal[] sucursales)
	{
		RegistroPersona[] registros = leerObjeto(ARCHIVO_PERSONAS, RegistroPersona[].class);
		if (registros == null)
			return new Persona[0];

		Persona[] personas = new Persona[registros.length];
		Map<Integer, Sucursal> mapa = indexarSucursales(sucursales);
		int indice = 0;

		for (RegistroPersona registro : registros)
		{
			if (registro == null || registro.persona == null)
				continue;

			personas[indice++] = registro.persona;
			Sucursal sucursal = mapa.get(registro.sucursalId);

			if (sucursal == null)
				continue;

			if (registro.esCliente && registro.persona instanceof Cliente)
				sucursal.agregarCliente((Cliente) registro.persona);
			else if (!registro.esCliente && registro.persona instanceof Empleado)
				sucursal.registrarEmpleado((Empleado) registro.persona);
		}

		return Arrays.copyOf(personas, indice);
	}

	public static synchronized Sucursal[] cargarSucursales()
	{
		RegistroSucursal[] registros = leerObjeto(ARCHIVO_SUCURSALES, RegistroSucursal[].class);
		if (registros == null)
			return new Sucursal[0];

		Sucursal[] sucursales = new Sucursal[registros.length];
		for (int i = 0; i < registros.length; i++)
		{
			RegistroSucursal registro = registros[i];
			sucursales[i] = registro == null ? null : registro.construir();
		}
		return sucursales;
	}

	public static synchronized SesionEntrenamiento[] cargarSesiones()
	{
		SesionEntrenamiento[] sesiones = leerObjeto(ARCHIVO_SESIONES, SesionEntrenamiento[].class);
		return sesiones == null ? new SesionEntrenamiento[0] : sesiones;
	}

	private static <T> T leerObjeto(Path ruta, Class<T> tipo)
	{
		try
		{
			if (Files.notExists(ruta) || Files.size(ruta) == 0)
				return null;

			try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(ruta)))
			{
				Object objeto = ois.readObject();
				return tipo.cast(objeto);
			}
		}
		catch (IOException | ClassNotFoundException | ClassCastException e)
		{
			System.out.println("fin de lectura");
			return null;
		}
	}

	// ==================== METODOS DE UTILIDAD ====================

	public static synchronized boolean guardarTodo()
	{
		Sucursal[] sucursales = Gimnasio.obtenerSucursales();
		if (sucursales == null)
			sucursales = new Sucursal[0];

		boolean personas = guardarPersonas(sucursales);
		boolean sucursalesGuardadas = guardarSucursales(sucursales);
		boolean sesiones = guardarSesiones(DatosSistema.obtenerSesiones());

		return personas && sucursalesGuardadas && sesiones;
	}

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
			System.out.println("fin de lectura");
			return false;
		}
	}

	private static RegistroPersona[] prepararRegistrosPersonas(Sucursal[] sucursales)
	{
		if (sucursales == null)
			return new RegistroPersona[0];

		int total = contarRegistrosPersonas(sucursales);
		RegistroPersona[] registros = new RegistroPersona[total];
		int indice = 0;

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
					if (cliente != null)
						registros[indice++] = RegistroPersona.cliente(sucursalId, cliente);
				}
			}

			Empleado[] empleados = sucursal.obtenerEmpleados();
			if (empleados != null)
			{
				for (Empleado empleado : empleados)
				{
					if (empleado != null)
						registros[indice++] = RegistroPersona.empleado(sucursalId, empleado);
				}
			}
		}

		return registros;
	}

	private static RegistroSucursal[] prepararRegistrosSucursales(Sucursal[] sucursales)
	{
		if (sucursales == null)
			return new RegistroSucursal[0];

		int total = contarSucursalesValidas(sucursales);
		RegistroSucursal[] registros = new RegistroSucursal[total];
		int indice = 0;

		for (Sucursal sucursal : sucursales)
		{
			if (sucursal == null)
				continue;

			RegistroSucursal registro = new RegistroSucursal();
			registro.id = sucursal.obtenerNumeroSucursal();
			registro.nombre = sucursal.obtenerNombreSucursal();
			registro.ubicacion = sucursal.obtenerUbicacionSucursal();
			registro.horario = sucursal.obtenerHorarioOperacion();
			registro.servicios = sucursal.obtenerServiciosEspeciales();
			registro.cuota = sucursal.obtenerCuotaMensual();
			registros[indice++] = registro;
		}

		return registros;
	}

	private static int contarRegistrosPersonas(Sucursal[] sucursales)
	{
		if (sucursales == null)
			return 0;

		int total = 0;
		for (Sucursal sucursal : sucursales)
		{
			if (sucursal == null)
				continue;

			Cliente[] clientes = sucursal.obtenerClientes();
			if (clientes != null)
			{
				for (Cliente cliente : clientes)
				{
					if (cliente != null)
						total++;
				}
			}

			Empleado[] empleados = sucursal.obtenerEmpleados();
			if (empleados != null)
			{
				for (Empleado empleado : empleados)
				{
					if (empleado != null)
						total++;
				}
			}
		}
		return total;
	}

	private static int contarSucursalesValidas(Sucursal[] sucursales)
	{
		if (sucursales == null)
			return 0;

		int total = 0;
		for (Sucursal sucursal : sucursales)
		{
			if (sucursal != null)
				total++;
		}
		return total;
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

	private static Path resolverCarpetaData()
	{
		Path cwd = Paths.get(System.getProperty("user.dir"));
		if (cwd.getFileName() != null && "bin".equalsIgnoreCase(cwd.getFileName().toString()) && cwd.getParent() != null)
		{
			return cwd.getParent().resolve("data");
		}
		return cwd.resolve("data");
	}

	// ==================== CLASES DE APOYO ====================

	private static class RegistroPersona implements Serializable
	{
		private static final long serialVersionUID = 1L;
		final int sucursalId;
		final boolean esCliente;
		final Persona persona;

		private RegistroPersona(int sucursalId, boolean esCliente, Persona persona)
		{
			this.sucursalId = sucursalId;
			this.esCliente = esCliente;
			this.persona = persona;
		}

		static RegistroPersona cliente(int sucursalId, Cliente cliente)
		{
			return new RegistroPersona(sucursalId, true, cliente);
		}

		static RegistroPersona empleado(int sucursalId, Empleado empleado)
		{
			return new RegistroPersona(sucursalId, false, empleado);
		}
	}

	private static class RegistroSucursal implements Serializable
	{
		private static final long serialVersionUID = 1L;
		int id;
		String nombre;
		String ubicacion;
		String horario;
		String servicios;
		float cuota;

		Sucursal construir()
		{
			return new Sucursal(id, nombre, horario, ubicacion, servicios, cuota);
		}
	}
}
