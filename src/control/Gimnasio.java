package control;

import java.io.Serializable;
import entidades.*;
import interfaz.*;

public class Gimnasio implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private String nombre = "Gimnasio POO";
	private static Sucursal[] sucursales;

	// MAIN
	public static void main(String[] args)
	{
		// Inicializar sistema de persistencia
		PersistenciaBasica.inicializar();

		// Crear gimnasio
		Gimnasio gimnasio = new Gimnasio();
		System.out.println(gimnasio);

		// Intentar cargar datos previos
		System.out.println("Intentando cargar datos previos...");
		boolean datoscargados = PersistenciaBasica.cargarTodo();

		if (datoscargados)
		{
			System.out.println("Datos cargados exitosamente.");
			sucursales = DatosSistema.obtenerSucursales();

			if (sucursales.length == 0)
			{
				System.out.println("No hay sucursales guardadas. Generando sucursales de ejemplo...");
				gimnasio.generarSucursales();
			}
		}
		else
		{
			System.out.println("No se encontraron datos previos. Iniciando con datos nuevos.");
		}

		// Iniciar menú principal
		MenuPrincipal.menu();

		// Guardar al salir
		System.out.println("Guardando datos...");
		DatosSistema.definirSucursales(sucursales);
		PersistenciaBasica.guardarTodo();
		System.out.println("Datos guardados. Hasta luego!");
	}

	// CONSTRUCTORES
	public Gimnasio()
	{
		if (sucursales == null)
		{
			sucursales = new Sucursal[0];
			generarSucursales();
		}
	}

	// GETTERS Y SETTERS
	public String obtenerNombre()
	{
		return nombre;
	}

	public String obtenerUbicaciones()
	{
		String ubicaciones = "";
		boolean primera = true;
		for (Sucursal sucursal : sucursales)
		{
			if (sucursal != null)
			{
				if (!primera)
					ubicaciones += ", ";
				ubicaciones += sucursal.obtenerUbicacionSucursal();
				primera = false;
			}
		}
		return ubicaciones;
	}

	public static Sucursal[] obtenerSucursales()
	{
		return sucursales;
	}

	// METODOS DE VALIDACION

	public static boolean validarIdEmpleado(long ID)
	{
		if (sucursales == null)
			return false;

		for (Sucursal sucursal : sucursales)
		{
			if (sucursal == null)
				continue;

			Empleado[] empleados = sucursal.obtenerEmpleados();
			if (empleados == null)
				continue;

			for (Empleado empleado : empleados)
			{
				if (empleado != null && empleado.obtenerIdentificador() == ID)
					return true;
			}
		}
		return false;
	}

	public static boolean validarIdCliente(long ID)
	{
		if (sucursales == null)
			return false;

		for (Sucursal sucursal : sucursales)
		{
			if (sucursal == null)
				continue;

			Cliente[] clientes = sucursal.obtenerClientes();
			if (clientes == null)
				continue;

			for (Cliente cliente : clientes)
			{
				if (cliente != null && cliente.obtenerIdentificador() == ID)
					return true;
			}
		}
		return false;
	}

	// METODOS DE GESTION

	public void generarSucursales()
	{
		String[] nombres = {"Peninsula", "Plaza Rio", "Santa Fe", "Dubai"};
		String[] horarios = {"6:00-22:00", "5:00-23:00", "24 horas", "7:00-21:00"};
		String[] ubicaciones = {"Centro", "Norte", "Sur", "Este"};
		String[] servicios = {"Pesas, Cardio", "Yoga, Pilates", "Natación, Spa", "Crossfit, Boxeo"};
		float[] cuotas = {300.0f, 450.0f, 600.0f, 750.0f};

		for (int i = 0; i < 4; i++)
		{
			Sucursal nueva = new Sucursal(i, nombres[i], horarios[i], ubicaciones[i], servicios[i], cuotas[i]);
			agregarSucursal(nueva);
		}
	}

	public void agregarSucursal(Sucursal sucursal)
	{
		Sucursal[] temp = new Sucursal[sucursales.length + 1];

		for (int i = 0; i < sucursales.length; i++)
		{
			temp[i] = sucursales[i];
		}

		temp[sucursales.length] = sucursal;
		sucursales = temp;
	}

	public void eliminarSucursal(int noSucursal)
	{
		int count = 0;
		for (Sucursal s : sucursales)
		{
			if (s != null && s.obtenerNumeroSucursal() != noSucursal)
				count++;
		}

		Sucursal[] temp = new Sucursal[count];
		int idx = 0;

		for (Sucursal s : sucursales)
		{
			if (s != null && s.obtenerNumeroSucursal() != noSucursal)
			{
				temp[idx++] = s;
			}
		}

		sucursales = temp;
	}

	public Sucursal buscarSucursal(int noSucursal)
	{
		if (sucursales == null)
			return null;

		for (Sucursal s : sucursales)
		{
			if (s != null && s.obtenerNumeroSucursal() == noSucursal)
				return s;
		}

		return null;
	}

	// REPRESENTACION

	@Override
	public String toString()
	{
		String header =
				"\n==============================\n" +
						"      " + nombre + "\n" +
						"==============================\n" +
						"Ubicaciones: " + obtenerUbicaciones() + "\n" +
						"No. de Sucursales: " + sucursales.length + "\n" +
						"--------------------------------\n";

		if (sucursales.length == 0)
			return header + "No hay sucursales registradas.\n";

		String sucursalesStr = "";
		int idx = 1;

		for (Sucursal sucursal : sucursales)
		{
			if (sucursal != null)
			{
				sucursalesStr += "Sucursal #" + idx + "\n";
				sucursalesStr += "  Horario    : " + sucursal.obtenerHorarioOperacion() + "\n";
				sucursalesStr += "  Ubicación  : " + sucursal.obtenerUbicacionSucursal() + "\n";
				sucursalesStr += "  Servicios  : " + sucursal.obtenerServiciosEspeciales() + "\n";
				sucursalesStr += "  Cuota      : $" + String.format("%.2f", sucursal.obtenerCuotaMensual()) + "\n";
				sucursalesStr += "--------------------------------\n";
				idx++;
			}
		}

		return header + sucursalesStr;
	}

	public String resumenSucursales()
	{
		String header =
				"\n==============================\n" +
						"      " + nombre + "\n" +
						"==============================\n" +
						"Ubicaciones: " + obtenerUbicaciones() + "\n" +
						"No. de Sucursales: " + sucursales.length + "\n" +
						"-----------------------------------------------------\n";

		if (sucursales.length == 0)
			return header + "No hay sucursales registradas.\n";

		String sucursalesStr = "";

		for (Sucursal sucursal : sucursales)
		{
			if (sucursal != null)
			{
				sucursalesStr += "Sucursal No: " + sucursal.obtenerNumeroSucursal();
				sucursalesStr += " - " + sucursal.obtenerUbicacionSucursal() + "\n";
				sucursalesStr += "  Horario    : " + sucursal.obtenerHorarioOperacion();
				sucursalesStr += " || Clientes: " + sucursal.obtenerTotalClientes() + "\n";
				sucursalesStr += "----------------------------------------------------\n";
			}
		}

		return header + sucursalesStr;
	}
}