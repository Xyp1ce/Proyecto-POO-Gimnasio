package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Membresia implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idMembresia;
	private String tipo; // "Mensual", "Trimestral", "Anual"
	private float precio;
	private LocalDate fechaInicio;
	private LocalDate fechaVencimiento;
	private boolean activa;

	// CONSTRUCTORES
	public Membresia()
	{
		this.idMembresia = generarIdMembresia();
		this.tipo = "Mensual";
		this.precio = 300.0f;
		this.fechaInicio = LocalDate.now();
		this.fechaVencimiento = fechaInicio.plusMonths(1);
		this.activa = true;
	}

	public Membresia(String tipo, float precio)
	{
		this.idMembresia = generarIdMembresia();
		this.tipo = tipo;
		this.precio = precio;
		this.fechaInicio = LocalDate.now();
		this.fechaVencimiento = calcularFechaVencimiento(tipo);
		this.activa = true;
	}

	public Membresia(String tipo, float precio, LocalDate fechaInicio)
	{
		this.idMembresia = generarIdMembresia();
		this.tipo = tipo;
		this.precio = precio;
		this.fechaInicio = fechaInicio;
		this.fechaVencimiento = calcularFechaVencimiento(tipo);
		this.activa = true;
	}

	// GETTERS Y SETTERS
	public long obtenerIdMembresia()
	{
		return idMembresia;
	}

	public void definirIdMembresia(long idMembresia)
	{
		this.idMembresia = idMembresia;
	}

	public String obtenerTipo()
	{
		return tipo;
	}

	public void definirTipo(String tipo)
	{
		this.tipo = tipo;
		this.fechaVencimiento = calcularFechaVencimiento(tipo);
	}

	public float obtenerPrecio()
	{
		return precio;
	}

	public void definirPrecio(float precio)
	{
		this.precio = precio;
	}

	public LocalDate obtenerFechaInicio()
	{
		return fechaInicio;
	}

	public void definirFechaInicio(LocalDate fechaInicio)
	{
		this.fechaInicio = fechaInicio;
		this.fechaVencimiento = calcularFechaVencimiento(tipo);
	}

	public LocalDate obtenerFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public boolean estaActiva()
	{
		return activa;
	}

	public void definirActiva(boolean activa)
	{
		this.activa = activa;
	}

	// METODOS DE NEGOCIO

	public boolean verificarVigencia()
	{
		LocalDate hoy = LocalDate.now();
		boolean vigente = !hoy.isAfter(fechaVencimiento) && activa;

		if (!vigente && activa)
		{
			activa = false; // Desactivar automáticamente si venció
		}

		return vigente;
	}

	public long obtenerDiasRestantes()
	{
		if (!verificarVigencia())
			return 0;

		LocalDate hoy = LocalDate.now();
		return ChronoUnit.DAYS.between(hoy, fechaVencimiento);
	}

	public void renovar()
	{
		if (verificarVigencia())
		{
			// Si todavía está vigente, extender desde la fecha de vencimiento actual
			fechaVencimiento = calcularNuevaFechaDesde(fechaVencimiento, tipo);
		}
		else
		{
			// Si ya venció, renovar desde hoy
			fechaInicio = LocalDate.now();
			fechaVencimiento = calcularFechaVencimiento(tipo);
		}
		activa = true;
	}

	public void suspender()
	{
		activa = false;
	}

	public boolean reactivar()
	{
		LocalDate hoy = LocalDate.now();
		if (!hoy.isAfter(fechaVencimiento))
		{
			activa = true;
			return true;
		}
		return false;
	}

	// METODOS PRIVADOS DE SOPORTE

	private long generarIdMembresia()
	{
		return (long) (Math.random() * 900000L) + 100000L;
	}

	private LocalDate calcularFechaVencimiento(String tipoMembresia)
	{
		return calcularNuevaFechaDesde(fechaInicio, tipoMembresia);
	}

	private LocalDate calcularNuevaFechaDesde(LocalDate fechaBase, String tipoMembresia)
	{
		if (fechaBase == null)
			fechaBase = LocalDate.now();

		switch (tipoMembresia.toLowerCase())
		{
			case "mensual":
				return fechaBase.plusMonths(1);
			case "trimestral":
				return fechaBase.plusMonths(3);
			case "semestral":
				return fechaBase.plusMonths(6);
			case "anual":
				return fechaBase.plusYears(1);
			default:
				return fechaBase.plusMonths(1); // Por defecto mensual
		}
	}

	// REPRESENTACION EN TEXTO

	@Override
	public String toString()
	{
		String estado = verificarVigencia() ? "VIGENTE" : "VENCIDA";
		return "Membresia{" +
				"id=" + idMembresia +
				", tipo='" + tipo + '\'' +
				", precio=$" + String.format("%.2f", precio) +
				", inicio=" + fechaInicio +
				", vencimiento=" + fechaVencimiento +
				", diasRestantes=" + obtenerDiasRestantes() +
				", estado=" + estado +
				'}';
	}

	public String resumen()
	{
		String estado = verificarVigencia() ? "✓ Vigente" : "✗ Vencida";
		return String.format("%s | $%.2f | %s (%d días)",
				tipo, precio, estado, obtenerDiasRestantes());
	}
}