package control;

import java.io.Serializable;
import entidades.*;
import interfaz.*;

public class Gimnasio implements Serializable
{
	// CENTRALIZAR TODA LA LOGICA GLOBAL DEL SISTEMA
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private String nombre = "Gimnasio POO";
	// MANTENER EL INVENTARIO DE SUCURSALES CARGADAS
	private static Sucursal[] sucursales;
	// ADMINISTRAR EL HILO DE RESPALDO AUTOMATICO
	private static Autoguardado autoGuardado;

	// MAIN
	public static void main(String[] args)
	{
		// PREPARAR LOS COMPONENTES DE PERSISTENCIA
		Persistencia.inicializar();

		// CREAR EL NUCLEO DEL SISTEMA CON SUCURSALES BASE
		Gimnasio gimnasio = new Gimnasio();
		System.out.println(gimnasio);

		// INTENTAR CARGAR DATOS DESDE LOS ARCHIVOS EXISTENTES
		System.out.println("Intentando cargar datos previos...");
		boolean datoscargados = Persistencia.cargarTodo();

		if (datoscargados)
		{
			// REUTILIZAR LAS SUCURSALES PERSISTIDAS SI EXISTEN
			System.out.println("Datos cargados exitosamente.");
			sucursales = DatosSistema.obtenerSucursales();

			if (sucursales.length == 0)
			{
				// GENERAR SUCURSALES DE REFERENCIA CUANDO NO HAY DATOS
				System.out.println("No hay sucursales guardadas. Generando sucursales de ejemplo...");
				gimnasio.generarSucursales();
			}
		}
		else
		{
			// ARRANCAR CON DATOS LIMPIOS SI NO EXISTEN ARCHIVOS
			System.out.println("No se encontraron datos previos. Iniciando con datos nuevos.");
		}

		// INICIAR RESPALDO AUTOMATICO Y MOSTRAR LA INTERFAZ PRINCIPAL
		iniciarAutoGuardado();
		MenuPrincipal.menu();

		// GUARDAR TODO CUANDO EL USUARIO SALE DEL MENU
		detenerAutoGuardado();
		System.out.println("Guardando datos...");
		DatosSistema.definirSucursales(sucursales);
		Persistencia.guardarTodo();
		System.out.println("Datos guardados. Hasta luego!");
	}

	// CONSTRUCTORES
	public Gimnasio()
	{
		// GARANTIZAR QUE LAS SUCURSALES EXISTAN EN MEMORIA
		if (sucursales == null)
		{
			// CREAR EL ARREGLO BASE Y POBLARLO CON DATOS DE EJEMPLO
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
		// ARMAR UNA CADENA CON TODAS LAS UBICACIONES REGISTRADAS
		String ubicaciones = "";
		boolean primera = true;
		for (Sucursal sucursal : sucursales)
		{
			// OMITIR POSICIONES VACIAS EN EL ARREGLO
			if (sucursal != null)
			{
				// AGREGAR COMAS SOLO DESPUES DEL PRIMER ELEMENTO
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
		// PROPORCIONAR ACCESO GLOBAL A LAS SUCURSALES
		return sucursales;
	}

	private static void iniciarAutoGuardado()
	{
		// CREAR EL HILO SOLO UNA VEZ PARA EVITAR DUPLICADOS
		if (autoGuardado == null)
		{
			// ARRANCAR EL HILO Y COMENZAR CICLOS DE GUARDADO
			autoGuardado = new Autoguardado();
			autoGuardado.start();
		}
	}

	public static void detenerAutoGuardado()
	{
		// DETENER EL HILO UNICAMENTE SI ESTA ACTIVO
		if (autoGuardado != null)
		{
			// SOLICITAR EL CIERRE Y LIBERAR LA REFERENCIA
			autoGuardado.detener();
			autoGuardado = null;
		}
	}

	// METODOS DE VALIDACION

	public static boolean validarIdEmpleado(long ID)
	{
		// ABORTAR SI NO HAY SUCURSALES REGISTRADAS
		if (sucursales == null)
			return false;

		for (Sucursal sucursal : sucursales)
		{
			// CONTINUAR CUANDO LA POSICION ESTA VACIA
			if (sucursal == null)
				continue;

			// CONSULTAR EL ARREGLO DE EMPLEADOS DE LA SUCURSAL
			Empleado[] empleados = sucursal.obtenerEmpleados();
			if (empleados == null)
				continue;

			for (Empleado empleado : empleados)
			{
				// DETECTAR SI ALGUN EMPLEADO USA EL IDENTIFICADOR BUSCADO
				if (empleado != null && empleado.obtenerIdentificador() == ID)
					return true;
			}
		}
		return false;
	}

	public static boolean validarIdCliente(long ID)
	{
		// ABORTAR SI NO HAY SUCURSALES REGISTRADAS
		if (sucursales == null)
			return false;

		for (Sucursal sucursal : sucursales)
		{
			// CONTINUAR CUANDO LA POSICION ESTA VACIA
			if (sucursal == null)
				continue;

			// CONSULTAR EL ARREGLO DE CLIENTES DE LA SUCURSAL
			Cliente[] clientes = sucursal.obtenerClientes();
			if (clientes == null)
				continue;

			for (Cliente cliente : clientes)
			{
				// DETECTAR SI ALGUN CLIENTE USA EL IDENTIFICADOR BUSCADO
				if (cliente != null && cliente.obtenerIdentificador() == ID)
					return true;
			}
		}
		return false;
	}

	// METODOS DE GESTION

	public void generarSucursales()
	{
		// DEFINIR LISTAS DE ATRIBUTOS PARA CREAR SUCURSALES DE EJEMPLO
		String[] nombres = {"Peninsula", "Plaza Rio", "Santa Fe", "Dubai"};
		String[] horarios = {"6:00-22:00", "5:00-23:00", "24 horas", "7:00-21:00"};
		String[] ubicaciones = {"Centro", "Norte", "Sur", "Este"};
		String[] servicios = {"Pesas, Cardio", "Yoga, Pilates", "Natación, Spa", "Crossfit, Boxeo"};
		float[] cuotas = {300.0f, 450.0f, 600.0f, 750.0f};

		for (int i = 0; i < 4; i++)
		{
			// CREAR UNA SUCURSAL COMPLETA CON LOS DATOS DEL INDICE
			Sucursal nueva = new Sucursal(i, nombres[i], horarios[i], ubicaciones[i], servicios[i], cuotas[i]);
			// INCORPORAR LA SUCURSAL A LA LISTA GLOBAL
			agregarSucursal(nueva);
		}
	}

	public void agregarSucursal(Sucursal sucursal)
	{
		// CREAR UN ARREGLO NUEVO CON ESPACIO ADICIONAL
		Sucursal[] temp = new Sucursal[sucursales.length + 1];

		for (int i = 0; i < sucursales.length; i++)
		{
			// COPIAR LAS REFERENCIAS EXISTENTES
			temp[i] = sucursales[i];
		}

		// ESCRIBIR LA SUCURSAL NUEVA EN LA ULTIMA POSICION
		temp[sucursales.length] = sucursal;
		sucursales = temp;
	}

	public void eliminarSucursal(int noSucursal)
	{
		// CONTAR CUANTAS SUCURSALES SE QUEDARAN EN EL LISTADO
		int count = 0;
		for (Sucursal s : sucursales)
		{
			if (s != null && s.obtenerNumeroSucursal() != noSucursal)
				count++;
		}

		// CREAR UN NUEVO ARREGLO SIN LA SUCURSAL ELIMINADA
		Sucursal[] temp = new Sucursal[count];
		int idx = 0;

		for (Sucursal s : sucursales)
		{
			if (s != null && s.obtenerNumeroSucursal() != noSucursal)
			{
				// COPIAR SOLO LAS SUCURSALES QUE PERMANECEN
				temp[idx++] = s;
			}
		}

		sucursales = temp;
	}

	public Sucursal buscarSucursal(int noSucursal)
	{
		// RETORNAR NULO SI NO HAY SUCURSALES REGISTRADAS
		if (sucursales == null)
			return null;

		for (Sucursal s : sucursales)
		{
			// DEVOLVER LA SUCURSAL CUANDO COINCIDE EL NUMERO
			if (s != null && s.obtenerNumeroSucursal() == noSucursal)
				return s;
		}

		return null;
	}

	// REPRESENTACION

	@Override
	public String toString()
	{
		// CREAR UN ENCABEZADO CON DATOS GENERALES DEL GIMNASIO
		String header =
				"\n==============================\n" +
						"      " + nombre + "\n" +
						"==============================\n" +
						"Ubicaciones: " + obtenerUbicaciones() + "\n" +
						"No. de Sucursales: " + sucursales.length + "\n" +
						"--------------------------------\n";

		if (sucursales.length == 0)
			return header + "No hay sucursales registradas.\n";

		// RECORRER LAS SUCURSALES PARA ARMAR LA DESCRIPCION DETALLADA
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
		// GENERAR UN ENCABEZADO CON ESTADISTICAS RESUMIDAS
		String header =
				"\n==============================\n" +
						"      " + nombre + "\n" +
						"==============================\n" +
						"Ubicaciones: " + obtenerUbicaciones() + "\n" +
						"No. de Sucursales: " + sucursales.length + "\n" +
						"-----------------------------------------------------\n";

		if (sucursales.length == 0)
			return header + "No hay sucursales registradas.\n";

		// LISTAR RESUMENES LINEA A LINEA PARA CADA SUCURSAL
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