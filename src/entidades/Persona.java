package entidades;

import java.io.Serializable;

public abstract class Persona implements Serializable, Identificable
{
	// SERVIR COMO BASE PARA CLIENTES Y EMPLEADOS
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS COMPARTIDOS
	protected long identificador;
	protected String nombre;
	protected String documento;
	protected long telefono;

	// CONSTRUCTORES
	protected Persona() {}

	protected Persona(String nombre, String documento, long telefono)
	{
		// GUARDAR LOS DATOS FUNDAMENTALES DESDE LA CREACION
		this.nombre = nombre;
		this.documento = documento;
		this.telefono = telefono;
	}

	// GETTERS Y SETTERS
	public long obtenerIdentificador()
	{
		return identificador;
	}

	public void definirIdentificador(long identificador)
	{
		this.identificador = identificador;
	}

	public String obtenerNombre()
	{
		return nombre;
	}

	public void definirNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String obtenerDocumento()
	{
		return documento;
	}

	public void definirDocumento(String documento)
	{
		this.documento = documento;
	}

	public long obtenerTelefono()
	{
		return telefono;
	}

	public void definirTelefono(long telefono)
	{
		this.telefono = telefono;
	}

	// IMPLEMENTACION DE IDENTIFICABLE
	@Override
	public long obtenerId()
	{
		// CUMPLIR EL CONTRATO DE IDENTIFICABLE
		return identificador;
	}

	// METODOS PARA DESCRIPCION EN TEXTO
	protected String describirCamposEspecificos()
	{
		// PERMITIR A LAS SUBCLASES APORTAR SUS CAMPOS PARTICULARES
		return "";
	}

	@Override
	public String toString()
	{
		String extras = describirCamposEspecificos();
		return getClass().getSimpleName() + '{' +
				"identificador=" + identificador +
				", nombre='" + textoSeguro(nombre) + '\'' +
				", documento='" + textoSeguro(documento) + '\'' +
				", telefono=" + telefono +
				(extras != null && !extras.trim().isEmpty() ? ", " + extras : "") +
				'}';
	}

	protected String textoSeguro(String valor)
	{
		return valor == null ? "" : valor;
	}
}