package entidades;

public class Limpieza extends Empleado
{
	// ESPECIALIZAR EMPLEADO PARA ACTIVIDADES DE LIMPIEZA
	private static final long serialVersionUID = 1L;
	// ATRIBUTO PARTICIPANTE
	private String areaAsignada;

	// CONSTRUCTORES
	public Limpieza()
	{
		super();
		// ASIGNAR UN AREA VACIA HASTA SER CONFIGURADA
		areaAsignada = "";
	}

	public Limpieza(String area)
	{
		super();
		// PERMITIR CREAR INSTANCIAS SOLO CON EL AREA
		areaAsignada = area;
	}

	public Limpieza(String tipoEmpleado, String nombre, long telefono, String direccion, String area)
	{
		super(tipoEmpleado, nombre, telefono, direccion);
		areaAsignada = area;
	}

	// GETTERS Y SETTERS
	public String obtenerAreaAsignada()
	{
		return areaAsignada;
	}

	public void definirAreaAsignada(String areaAsignada)
	{
		this.areaAsignada = areaAsignada;
	}

	// METODOS PARTICULARES
	public void limpiarArea() {}

	public void reportarDano() {}

	// METODOS POLIMORFICOS
	@Override
	public void registrarEntrada()
	{
		// INDICAR QUE EL PERSONAL ESTA ACTIVO
		tareasProgramadas = "LIMPIEZA EN SERVICIO";
	}

	@Override
	public void registrarSalida()
	{
		// MOSTRAR QUE LAS LABORES HAN CONCLUIDO
		tareasProgramadas = "LIMPIEZA FUERA DE SERVICIO";
	}

	@Override
	public String generarDescripcion()
	{
		// INCLUIR EL AREA PARA IDENTIFICAR SUS RESPONSABILIDADES
		return "Limpieza{" +
				"identificador=" + obtenerIdentificador() +
				", nombre='" + obtenerNombre() + '\'' +
				", telefono=" + obtenerTelefono() +
				", direccion='" + obtenerDireccion() + '\'' +
				", areaAsignada='" + areaAsignada + '\'' +
				'}';
	}
}