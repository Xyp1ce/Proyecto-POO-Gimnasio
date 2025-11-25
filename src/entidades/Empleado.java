package entidades;

import control.Gimnasio;

public abstract class Empleado extends Persona
{
	// ESPECIALIZAR PERSONA PARA MANEJAR EMPLEADOS DEL GIMNASIO
	private static final long serialVersionUID = 1L;
	// ATRIBUTOS DEL EMPLEADO
	protected String tipoEmpleado;
	protected String tareasProgramadas;
	protected String direccion;
	protected float salario;

	// CONSTRUCTORES
	public Empleado()
	{
		super();
		// ASIGNAR IDENTIFICADOR UNICO DESDE EL INICIO
		asignarIdentificadorEmpleado();
	}

	public Empleado(String tipoEmpleado, String nombre, String documento, long telefono, String direccion)
	{
		super(nombre, documento, telefono);
		// CONFIGURAR LA INFORMACION CORE DEL EMPLEADO
		this.tipoEmpleado = tipoEmpleado;
		this.direccion = direccion;
		tareasProgramadas = "";
		salario = 0;
		asignarIdentificadorEmpleado();
	}

	public Empleado(String tipoEmpleado, String nombre, long telefono, String direccion)
	{
		this(tipoEmpleado, nombre, "", telefono, direccion);
	}

	// GETTERS Y SETTERS
	public String obtenerTipoEmpleado() 
	{
		return tipoEmpleado;
	}

	public void definirTipoEmpleado(String tipoEmpleado)
	{
		this.tipoEmpleado = tipoEmpleado;
	}

	public String obtenerTareasProgramadas()
	{
		return tareasProgramadas;
	}

	public void definirTareasProgramadas(String tareasProgramadas)
	{
		this.tareasProgramadas = tareasProgramadas;
	}

	public String obtenerDireccion()
	{
		return direccion;
	}

	public void definirDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public float obtenerSalario()
	{
		return salario;
	}

	public void definirSalario(float salario)
	{
		this.salario = salario;
	}

	// METODOS COMPARTIDOS
	private void asignarIdentificadorEmpleado()
	{
		// GENERAR UN IDENTIFICADOR ALEATORIO QUE NO SE REPITA EN EL GIMNASIO
		long nuevoIdentificador;
		do
			nuevoIdentificador = (long) (Math.random() * 90000000L) + 10000000L;
		while (Gimnasio.validarIdEmpleado(nuevoIdentificador));
	
		definirIdentificador(nuevoIdentificador);
	}
	
	// METODOS ABSTRACTOS
	public abstract void registrarEntrada();

	public abstract void registrarSalida();

	@Override
	protected String describirCamposEspecificos()
	{
		return "tipoEmpleado='" + textoSeguro(tipoEmpleado) + '\'' +
				", tareasProgramadas='" + textoSeguro(tareasProgramadas) + '\'' +
				", direccion='" + textoSeguro(direccion) + '\'' +
				", salario=" + salario;
	}
}