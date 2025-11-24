package entidades;

import control.Gimnasio;

public class Cliente extends Persona
{
	private static final long serialVersionUID = 1L;
	// ATRIBUTOS PARTICULARES
	private String tipoCliente;
	private int historialVisitas;

	// CONSTRUCTORES
	public Cliente()
	{
		super();
		asignarIdentificadorCliente();
		historialVisitas = 0;
	}

	public Cliente(String nombre, String documento, String tipoCliente, long telefono)
	{
		super(nombre, documento, telefono);
		this.tipoCliente = tipoCliente;
		historialVisitas = 0;
		asignarIdentificadorCliente();
	}

	// GETTERS Y SETTERS
	public String obtenerTipoCliente()
	{
		return tipoCliente;
	}

	public void definirTipoCliente(String tipoCliente)
	{
		this.tipoCliente = tipoCliente;
	}

	public int obtenerHistorialVisitas()
	{
		return historialVisitas;
	}

	public void definirHistorialVisitas(int historialVisitas)
	{
		this.historialVisitas = historialVisitas;
	}

	// METODOS PARTICULARES
	public void registrarVisita()
	{
		historialVisitas++;
	}

	private void asignarIdentificadorCliente()
	{
		long nuevoIdentificador;
		do
			nuevoIdentificador = (long) (Math.random() * 90000000L) + 10000000L;
		while (Gimnasio.validarIdCliente(nuevoIdentificador));
		
		definirIdentificador(nuevoIdentificador);
	}

	@Override
	public String generarDescripcion()
	{
		return "Cliente{" +
				"identificador=" + obtenerIdentificador() +
				", nombre='" + obtenerNombre() + '\'' +
				", tipoCliente='" + tipoCliente + '\'' +
				", historialVisitas='" + historialVisitas + '\'' +
				", telefono='" + obtenerTelefono() + '\'' +
				'}';
	}
}
