package entidades;

import java.io.Serializable;

public abstract class Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	// ATRIBUTOS COMPARTIDOS
	protected long identificador;
	protected String nombre;
	protected String documento;
	protected long telefono;

	// CONSTRUCTORES
	protected Persona() {}

	protected Persona(String nombre, String documento, long telefono) {
		this.nombre = nombre;
		this.documento = documento;
		this.telefono = telefono;
	}

	// GETTERS Y SETTERS EN ESPAÑOL
	public long obtenerIdentificador() {
		return identificador;
	}

	protected void definirIdentificador(long identificador) {
		this.identificador = identificador;
	}

	public String obtenerNombre() {
		return nombre;
	}

	public void definirNombre(String nombre) {
		this.nombre = nombre;
	}

	public String obtenerDocumento() {
		return documento;
	}

	public void definirDocumento(String documento) {
		this.documento = documento;
	}

	public long obtenerTelefono() {
		return telefono;
	}

	public void definirTelefono(long telefono) {
		this.telefono = telefono;
	}

	// METODO OBLIGATORIO PARA DESCRIPCION
	public abstract String generarDescripcion();

	@Override
	public String toString() {
		return generarDescripcion();
	}
}
