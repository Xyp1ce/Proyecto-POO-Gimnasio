package control;

import entidades.Empleado;
import entidades.Cliente;

public class Sucursal
{
	// ATRIBUTOS PRINCIPALES DE LA SUCURSAL
	private int numeroSucursal;
	private String nombreSucursal;
	private String horarioOperacion;
	private String ubicacionSucursal;
	private String serviciosEspeciales;
	private float cuotaMensual;
	private Empleado[] empleados;
	private Cliente[] clientes;
	private int totalClientes;
	private int totalEmpleados;

	// CONSTRUCTORES
	public Sucursal()
    {
		inicializarColecciones();
	}

	public Sucursal(int numeroSucursal, String nombre, String horario, String ubicacion, String servicios, float cuota)
    {
		this.numeroSucursal = numeroSucursal;
		nombreSucursal = nombre;
		horarioOperacion = horario;
		ubicacionSucursal = ubicacion;
		serviciosEspeciales = servicios;
		cuotaMensual = cuota;
		inicializarColecciones();
	}

	private void inicializarColecciones()
	{
		empleados = new Empleado[100];
		clientes = new Cliente[100];
		totalClientes = 0;
		totalEmpleados = 0;
	}

	// GETTERS Y SETTERS
	public int obtenerNumeroSucursal()
	{
		return numeroSucursal;
	}

	public void definirNumeroSucursal(int numeroSucursal)
	{
		this.numeroSucursal = numeroSucursal;
	}

	public String obtenerNombreSucursal()
	{
		return nombreSucursal;
	}

	public void definirNombreSucursal(String nombreSucursal)
	{
		this.nombreSucursal = nombreSucursal;
	}

	public String obtenerHorarioOperacion()
	{
		return horarioOperacion;
	}

	public void definirHorarioOperacion(String horarioOperacion)
	{
		this.horarioOperacion = horarioOperacion;
	}

	public float obtenerCuotaMensual()
	{
		return cuotaMensual;
	}

	public void definirCuotaMensual(float cuotaMensual)
	{
		this.cuotaMensual = cuotaMensual;
	}

	public String obtenerServiciosEspeciales()
	{
		return serviciosEspeciales;
	}

	public void definirServiciosEspeciales(String serviciosEspeciales)
	{
		this.serviciosEspeciales = serviciosEspeciales;
	}

	public String obtenerUbicacionSucursal()
	{
		return ubicacionSucursal;
	}

	public void definirUbicacionSucursal(String ubicacionSucursal)
	{
		this.ubicacionSucursal = ubicacionSucursal;
	}

	public Empleado[] obtenerEmpleados()
	{
		return empleados;
	}

	public Cliente[] obtenerClientes()
	{
		return clientes;
	}

	public int obtenerTotalClientes()
	{
		return totalClientes;
	}

	public int obtenerTotalEmpleados()
	{
		return totalEmpleados;
	}

	// METODOS PARA CLIENTES
	public boolean agregarCliente(Cliente cliente)
	{
		if (cliente == null)
			return false;
		if (totalClientes >= clientes.length)
			return false;
		clientes[totalClientes] = cliente;
		totalClientes++;
		return true;
	}

	public boolean eliminarCliente(long identificador)
	{
		for (int i = 0; i < clientes.length; i++)
		{
			Cliente cliente = clientes[i];
			if (cliente != null && cliente.obtenerIdentificador() == identificador)
			{
				clientes[i] = null;
				reacomodarClientes();
				totalClientes--;
				return true;
			}
		}
		return false;
	}

	private void reacomodarClientes()
	{
		Cliente[] temporal = new Cliente[clientes.length];
		int indice = 0;
		for (Cliente cliente : clientes)
		{
			if (cliente != null)
			{
				temporal[indice] = cliente;
				indice++;
			}
		}
		clientes = temporal;
	}

	public Cliente buscarCliente(long identificador)
	{
		for (Cliente cliente : clientes)
			if (cliente != null && cliente.obtenerIdentificador() == identificador)
				return cliente;
		return null;
	}

	// METODOS PARA EMPLEADOS
	public boolean registrarEmpleado(Empleado empleado)
	{
		if (empleado == null)
			return false;
		if (totalEmpleados >= empleados.length)
			return false;
		empleados[totalEmpleados] = empleado;
		totalEmpleados++;
		return true;
	}

	public boolean eliminarEmpleado(long identificador)
	{
		for (int i = 0; i < empleados.length; i++)
		{
			Empleado empleado = empleados[i];
			if (empleado != null && empleado.obtenerIdentificador() == identificador)
			{
				empleados[i] = null;
				reacomodarEmpleados();
				totalEmpleados--;
				return true;
			}
		}
		return false;
	}

	private void reacomodarEmpleados()
	{
		Empleado[] temporal = new Empleado[empleados.length];
		int indice = 0;
		for (Empleado empleado : empleados)
		{
			if (empleado != null)
			{
				temporal[indice] = empleado;
				indice++;
			}
		}
		empleados = temporal;
	}

	public Empleado buscarEmpleado(long identificador)
	{
		for (Empleado empleado : empleados)
			if (empleado != null && empleado.obtenerIdentificador() == identificador)
				return empleado;
		return null;
	}

	// DESCRIPCION DE LA SUCURSAL
	@Override
	public String toString()
	{
		return "Sucursal{" +
				"numero=" + numeroSucursal +
				", nombre='" + nombreSucursal + '\'' +
				", horario='" + horarioOperacion + '\'' +
				", ubicacion='" + ubicacionSucursal + '\'' +
				", servicios='" + serviciosEspeciales + '\'' +
				", cuota=" + cuotaMensual +
				", clientes=" + totalClientes +
				", empleados=" + totalEmpleados +
				'}';
	}
}