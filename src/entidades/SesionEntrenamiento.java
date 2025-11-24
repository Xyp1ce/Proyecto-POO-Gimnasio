package entidades;

import java.io.Serializable;

public class SesionEntrenamiento implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idSesion;
	private long idCliente;
	private long idEntrenador;
	private String fecha; // Formato: "dd/MM/yyyy"
	private int duracionMinutos;
	private String descripcion;

	// CONSTRUCTORES
	public SesionEntrenamiento()
	{
		this.idSesion = generarIdSesion();
		this.fecha = obtenerFechaActual();
		this.duracionMinutos = 60;
		this.descripcion = "";
	}

	public SesionEntrenamiento(long idCliente, long idEntrenador, int duracionMinutos)
	{
		this.idSesion = generarIdSesion();
		this.idCliente = idCliente;
		this.idEntrenador = idEntrenador;
		this.fecha = obtenerFechaActual();
		this.duracionMinutos = duracionMinutos;
		this.descripcion = "";
	}

	public SesionEntrenamiento(long idCliente, long idEntrenador, int duracionMinutos, String descripcion)
	{
		this.idSesion = generarIdSesion();
		this.idCliente = idCliente;
		this.idEntrenador = idEntrenador;
		this.fecha = obtenerFechaActual();
		this.duracionMinutos = duracionMinutos;
		this.descripcion = descripcion;
	}

	// GETTERS Y SETTERS
	public long obtenerIdSesion()
	{
		return idSesion;
	}

	public void definirIdSesion(long idSesion)
	{
		this.idSesion = idSesion;
	}

	public long obtenerIdCliente()
	{
		return idCliente;
	}

	public void definirIdCliente(long idCliente)
	{
		this.idCliente = idCliente;
	}

	public long obtenerIdEntrenador()
	{
		return idEntrenador;
	}

	public void definirIdEntrenador(long idEntrenador)
	{
		this.idEntrenador = idEntrenador;
	}

	public String obtenerFecha()
	{
		return fecha;
	}

	public void definirFecha(String fecha)
	{
		this.fecha = fecha;
	}

	public int obtenerDuracionMinutos()
	{
		return duracionMinutos;
	}

	public void definirDuracionMinutos(int duracionMinutos)
	{
		this.duracionMinutos = duracionMinutos;
	}

	public String obtenerDescripcion()
	{
		return descripcion;
	}

	public void definirDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	// METODOS PRIVADOS

	private long generarIdSesion()
	{
		return (long) (Math.random() * 9000000L) + 1000000L;
	}

	private String obtenerFechaActual()
	{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1;
		int anio = cal.get(java.util.Calendar.YEAR);
		return String.format("%02d/%02d/%04d", dia, mes, anio);
	}

	// REPRESENTACION

	@Override
	public String toString()
	{
		return "SesionEntrenamiento{" +
				"id=" + idSesion +
				", cliente=" + idCliente +
				", entrenador=" + idEntrenador +
				", fecha='" + fecha + '\'' +
				", duracion=" + duracionMinutos + " min" +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}