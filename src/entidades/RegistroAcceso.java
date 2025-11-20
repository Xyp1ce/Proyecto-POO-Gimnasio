package entidades;

import java.io.Serializable;
import control.*;

public class RegistroAcceso implements Serializable{
	private static final long serialVersionUID = 120L;
	Gimnasio gimnasio = new Gimnasio();
	
	// ATRIBUTOS
	private long IDcliente;
	private String fechaHoraEntrada;
	private String fechaHoraSalida;

	// CONSTRUCTOR
	public RegistroAcceso(long IDcliente, String fechaHoraEntrada) {
		this.IDcliente = IDcliente;
		this.fechaHoraEntrada = fechaHoraEntrada;
		this.fechaHoraSalida = null;
	}

	// GETTERS Y SETTERS 
	public long getIDcliente() {
		return IDcliente;
	}

	public String getFechaHoraEntrada() {
		return fechaHoraEntrada;
	}

	public String getFechaHoraSalida() {
		return fechaHoraSalida;
	}

	public void setFechaHoraSalida(String fechaHoraSalida) {
		this.fechaHoraSalida = fechaHoraSalida;
	}
	

}
