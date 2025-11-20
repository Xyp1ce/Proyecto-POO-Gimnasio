package entidades;

import java.io.Serializable;

public abstract class Persona implements Serializable{
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	
	//GETTERS Y SETTERS
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
}
