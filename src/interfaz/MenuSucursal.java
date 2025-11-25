package interfaz;
import javax.swing.JOptionPane;
import control.*;
import entidades.*;

public class MenuSucursal
{
	// GUARDA REFERENCIA CUANDO SE REQUIERE CONTEXTO DE INSTANCIA
	Sucursal sucursal;
	// CONSERVA EL ID DEL CLIENTE SELECCIONADO ENTRE ACCIONES
	static long idCliente;

	public static void menu(Sucursal opcSucursal)
	{
		// SUCURSAL LLAMADA DESDE MENU PRINCIPAL
		String opciones = "";
		do
		{
			opciones = JOptionPane.showInputDialog(null, "Bienvenido a la sucursal " +
					opcSucursal.toString() +
					"\nPor favor Seleccione una de las siguientes opciones: "+
					"\n[1] Agregar Cliente"+
					"\n[2] Mostrar Lista de Clientes"+
					"\n[3] Seleccionar Cliente"+
					"\n[4] Remover Cliente"+
					"\n[5] Ver Lista de Empleados"+
					"\n[6] Agregar Empleado"+
					"\n[7] Remover Empleado"+
					"\n[8] Seleccionar Empleado"+
					"\n[9] Gestión de Membresías y Pagos" + // NUEVA OPCION
					"\n[10] Salir"+
					"\nOpcion:\t");

			if (opciones == null)
			{
				// REGRESAR SI EL USUARIO CANCELA EL DIALOGO
				JOptionPane.showMessageDialog(null, "Regresando al menu principal...");
				return;
			}

			switch(opciones)
			{
				case "1": // AGREGAR CLIENTE
				{
					Cliente nuevo = agregarCliente();
					if (nuevo != null)
					{
						if (opcSucursal.agregarCliente(nuevo))
							JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
						else
							JOptionPane.showMessageDialog(null, "No fue posible agregar al cliente");
					}
					break;
				}
				case "2": // VER CLIENTES
				{
					JOptionPane.showMessageDialog(null, verClientes(opcSucursal));
					break;
				}
				case "3": // SELECCIONAR CLIENTE
				{
					seleccionarCliente(opcSucursal);
					break;
				}
				case "4": // REMOVER CLIENTE
				{
					removerCliente(opcSucursal);
					break;
				}
				case "5": // VER EMPLEADOS
				{
					JOptionPane.showMessageDialog(null, verEmpleados(opcSucursal));
					break;
				}
				case "6": // AGREGAR EMPLEADO
				{
					Empleado emp = agregarEmpleado();
					if (emp != null)
					{
						if (opcSucursal.registrarEmpleado(emp))
							JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente");
						else
							JOptionPane.showMessageDialog(null, "No fue posible registrar al empleado");
					}
					break;
				}
				case "7": // REMOVER EMPLEADO
				{
					removerEmpleado(opcSucursal);
					break;
				}
				case "8": // SELECCIONAR EMPLEADO Y LLAMAR A SU MENU
				{
					seleccionarEmpleado(opcSucursal);
					break;
				}
				case "9": // GESTION DE MEMBRESIAS Y PAGOS (NUEVA OPCION)
				{
					MenuMembresias.menu(opcSucursal);
					break;
				}
				case "10":
				{
					JOptionPane.showMessageDialog(null, "Regresando al menu principal...");
					return;
				}
				default:
					JOptionPane.showMessageDialog(null, "Opcion invalida");
			}
		} while (!"10".equals(opciones));
	}

	public static Cliente agregarCliente()
	{
		// SOLICITA DATOS DEL CLIENTE Y VERIFICA NUMEROS TELEFONICOS
		String nombre = JOptionPane.showInputDialog("Ingrese el nombre del cliente:");
		if (nombre == null)
			return null;
		String documento = JOptionPane.showInputDialog("Ingrese el documento del cliente:");
		if (documento == null)
			return null;
		String tipo = JOptionPane.showInputDialog("Ingrese el tipo de cliente:\n[1] Regular\n[2] Premium");
		if (tipo == null)
			return null;
		String tipoCliente = "Regular";
		if ("2".equals(tipo.trim()))
			tipoCliente = "Premium";

		while (true)
		{
			String tel = JOptionPane.showInputDialog("Ingrese el telefono del cliente (solo digitos):");
			if (tel == null)
				return null; // CANCELAR
			tel = tel.trim();
			if (tel.matches("\\d+"))
			{ // VERIFICA QUE SOLO CONTIENE DIGITOS
				long telefono = Long.parseLong(tel);
				return new Cliente(nombre, documento, tipoCliente, telefono);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Telefono invalido. Introduce solo digitos o pulsa Cancelar.");
			}
		}
	}

	public static String verClientes(Sucursal sucursal)
	{
		// GENERA LISTA FORMATEADA DE CLIENTES EXISTENTES
		StringBuilder strClientes = new StringBuilder();
		Cliente[] clientes = sucursal.obtenerClientes();
		if (clientes == null)
			return "No hay clientes registrados.";
		int count = 0;
		for (Cliente c : clientes)
		{
			if (c != null)
			{
				strClientes.append(c).append("\n");
				count++;
			}
		}
		if (count == 0)
			return "No hay clientes registrados.";
		return strClientes.toString();
	}

	public static Empleado agregarEmpleado()
	{
		// CAPTURA DATOS DEL EMPLEADO Y CREA INSTANCIA SEGUN EL TIPO
		String opcionTipo = JOptionPane.showInputDialog(null, "Seleccione el tipo de empleado:\n[1] Entrenador\n[2] Limpieza\n[3] Servicio Al Cliente");
		if (opcionTipo == null)
			return null;
		String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del empleado:");
		if (nombre == null)
			return null;
		String documento = JOptionPane.showInputDialog(null, "Ingrese el documento del empleado:");
		if (documento == null)
			return null;
		long telefono = 0;
		while (true)
		{
			String tel = JOptionPane.showInputDialog(null, "Ingrese el telefono del empleado:");

			if (tel == null)
				return null;

			tel = tel.trim();

			if (tel.matches("\\d+"))
			{
				telefono = Long.parseLong(tel);
				break;
			}
			JOptionPane.showMessageDialog(null, "TELEFONO INVALIDO, SOLO DIGITOS");
		}

		String direccion = JOptionPane.showInputDialog(null, "Ingrese la direccion del empleado:");
		if (direccion == null)
			return null;

		switch (opcionTipo.trim()) {
			case "1":
			{
				String especialidad = JOptionPane.showInputDialog(null, "Ingrese una especialidad inicial:");
				if (especialidad == null)
					especialidad = "";
				String certificacion = JOptionPane.showInputDialog(null, "Ingrese una certificacion inicial:");
				if (certificacion == null)
					certificacion = "";
				String rutina = JOptionPane.showInputDialog(null, "Ingrese una rutina inicial:");
				if (rutina == null)
					rutina = "";
				Entrenador entrenador = new Entrenador("Entrenador", nombre, telefono, direccion, especialidad, certificacion, rutina);
				entrenador.definirDocumento(documento);
				return entrenador;
			}
			case "2":
			{
				String area = JOptionPane.showInputDialog(null, "Ingrese el area asignada:");
				if (area == null)
					area = "General";
				Limpieza limpieza = new Limpieza("Limpieza", nombre, telefono, direccion, area);
				limpieza.definirDocumento(documento);
				return limpieza;
			}
			case "3":
			{
				String idioma = JOptionPane.showInputDialog(null, "Ingrese el idioma principal:");
				if (idioma == null)
					idioma = "Español";
				String total = JOptionPane.showInputDialog(null, "Ingrese clientes atendidos previamente:");
				int atendidos = 0;
				if (total != null && total.trim().matches("\\d+"))
					atendidos = Integer.parseInt(total.trim());
				ServicioAlCliente servicio = new ServicioAlCliente("Servicio", nombre, telefono, direccion, idioma, atendidos);
				servicio.definirDocumento(documento);
				return servicio;
			}
		}
		JOptionPane.showMessageDialog(null, "Opcion de empleado invalida");
		return null;
	}

	public static String verEmpleados(Sucursal sucursal)
	{
		StringBuilder strEmpleados = new StringBuilder();
		Empleado[] empleados = sucursal.obtenerEmpleados();
		if (empleados == null)
			return "No hay empleados registrados.";
		int count = 0;
		for (Empleado e : empleados)
		{
			if (e != null)
			{
				strEmpleados.append(e).append("\n");
				count++;
			}
		}
		if (count == 0)
			return "No hay empleados registrados.";
		return strEmpleados.toString();
	}

	public static void seleccionarEmpleado(Sucursal sucursal)
	{
		// BUSCA EMPLEADO POR ID Y ABRE MENU ESPECIFICO
		try
		{
			String in = JOptionPane.showInputDialog(null, verEmpleados(sucursal) + "\nIngrese el ID del empleado:");
			if (in == null)
				return;
			long idEmpleado = Long.parseLong(in.trim());
			Empleado e = null;
			for (Empleado empleado : sucursal.obtenerEmpleados())
			{
				if (empleado != null && empleado.obtenerIdentificador() == idEmpleado)
				{
					e = empleado;
					break;
				}
			}
			if (e != null)
			{
				MenuEmpleados.menu(e, sucursal);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No se encontró un empleado con ese ID.");
			}
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}

	public static void removerEmpleado(Sucursal sucursal)
	{
		// SOLICITA ID Y ELIMINA EMPLEADO SI EXISTE
		try
		{
			String in = JOptionPane.showInputDialog(null, verEmpleados(sucursal) + "\nIngrese el ID del empleado a eliminar:");
			if (in == null)
				return;
			long idEmpleado = Long.parseLong(in.trim());
			if (sucursal.eliminarEmpleado(idEmpleado))
				JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente.");
			else
				JOptionPane.showMessageDialog(null, "No se encontro el empleado.");
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}

	public static void seleccionarCliente(Sucursal sucursal)
	{
		// PERMITE NAVEGAR A LAS OPCIONES DE UN CLIENTE EN PARTICULAR
		try
		{
			String in = JOptionPane.showInputDialog(null, verClientes(sucursal) + "\nIngrese el ID del cliente:");
			if (in == null)
				return;
			idCliente = Long.parseLong(in.trim());
			Cliente c = sucursal.buscarCliente(idCliente);
			if (c != null)
			{
				MenuCliente.menu(c, sucursal);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No se encontró un cliente con ese ID.");
			}
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}

	public static void removerCliente(Sucursal sucursal)
	{
		// ELIMINA CLIENTE DESPUES DE CONFIRMAR IDENTIFICADOR
		try
		{
			String in = JOptionPane.showInputDialog(null, verClientes(sucursal) + "\nIngrese el ID del cliente a eliminar:");
			if (in == null) return;
			idCliente = Long.parseLong(in.trim());
			if (sucursal.eliminarCliente(idCliente))
				JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
			else
				JOptionPane.showMessageDialog(null, "No se encontro el cliente.");
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(null, "ID inválido.");
		}
	}
}