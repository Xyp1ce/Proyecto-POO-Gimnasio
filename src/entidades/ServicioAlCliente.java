package entidades;

public class ServicioAlCliente extends Empleado
{
	// MODELAR EMPLEADOS ENFOCADOS EN ATENCION AL CLIENTE
	private static final long serialVersionUID = 1L;
	// ATRIBUTOS PARTICULARES DEL SERVICIO
	private String idiomaServicio;
	private int clientesAtendidos;
	private float comision;

	// CONSTRUCTORES
	public ServicioAlCliente()
	{
		super();
		// CONFIGURAR VALORES BASE SIN CARGA DE DATOS
		idiomaServicio = "";
		clientesAtendidos = 0;
		comision = 0;
	}

	public ServicioAlCliente(String idioma, int clientesAtendidos)
	{
		super();
		// PERMITIR CREAR INSTANCIAS RAPIDAS CON LOS DATOS PRINCIPALES
		this.idiomaServicio = idioma;
		this.clientesAtendidos = clientesAtendidos;
		comision = 0;
	}

	public ServicioAlCliente(String tipoEmpleado, String nombre, long telefono, String direccion, String idioma, int clientesAtendidos)
	{
		super(tipoEmpleado, nombre, telefono, direccion);
		this.idiomaServicio = idioma;
		this.clientesAtendidos = clientesAtendidos;
		comision = 0;
	}

	// GETTERS Y SETTERS
	public String obtenerIdiomaServicio()
	{
		return idiomaServicio;
	}

	public void definirIdiomaServicio(String idiomaServicio)
	{
		this.idiomaServicio = idiomaServicio;
	}

	public int obtenerClientesAtendidos()
	{
		return clientesAtendidos;
	}

	public void definirClientesAtendidos(int clientesAtendidos)
	{
		this.clientesAtendidos = clientesAtendidos;
	}

	public float obtenerComision()
	{
		return comision;
	}

	public void definirComision(float comision)
	{
		this.comision = comision;
	}

	// METODOS PARTICULARES
	public void atenderCliente()
	{
		// INCREMENTAR EL CONTADOR PARA FINES DE REPORTE
		clientesAtendidos++;
	}

	public void registrarEncuestaSatisfaccion() {}

	public void registrarFelicitacion() {}

	// METODOS POLIMORFICOS
	@Override
	public void registrarEntrada()
	{
		// INDICAR QUE EL PERSONAL ESTA DISPONIBLE EN PISOS O LINEA
		tareasProgramadas = "SERVICIO AL CLIENTE EN SERVICIO";
	}

	@Override
	public void registrarSalida()
	{
		// MOSTRAR QUE YA NO ESTA ATENDIENDO
		tareasProgramadas = "SERVICIO AL CLIENTE FUERA DE SERVICIO";
	}

	@Override
	protected String describirCamposEspecificos()
	{
		String camposBase = super.describirCamposEspecificos();
		String descripcion = (camposBase == null || camposBase.isEmpty()) ? "" : camposBase + ", ";
		descripcion += "idiomaServicio='" + textoSeguro(idiomaServicio) + '\'';
		descripcion += ", clientesAtendidos=" + clientesAtendidos;
		descripcion += ", comision=" + comision;
		return descripcion;
	}
}