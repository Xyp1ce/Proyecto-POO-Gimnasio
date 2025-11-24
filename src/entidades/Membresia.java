package entidades;

import java.io.Serializable;

public class Membresia implements Serializable, ConCosto, Reportable
{
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idMembresia;
	private String tipo; // "Mensual", "Trimestral", "Anual"
	private float precio;
	private String fechaInicio; // Formato: "dd/MM/yyyy"
	private String fechaVencimiento; // Formato: "dd/MM/yyyy"
	private boolean activa;

	// CONSTRUCTORES
	public Membresia()
	{
		this.idMembresia = generarIdMembresia();
		this.tipo = "Mensual";
		this.precio = 300.0f;
		this.fechaInicio = obtenerFechaActual();
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
		this.activa = true;
	}

	public Membresia(String tipo, float precio)
	{
		this.idMembresia = generarIdMembresia();
		this.tipo = tipo;
		this.precio = precio;
		this.fechaInicio = obtenerFechaActual();
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
		this.activa = true;
	}

	public Membresia(String tipo, float precio, String fechaInicio)
	{
		this.idMembresia = generarIdMembresia();
		this.tipo = tipo;
		this.precio = precio;
		this.fechaInicio = fechaInicio;
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
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
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
	}

	public float obtenerPrecio()
	{
		return precio;
	}

	public void definirPrecio(float precio)
	{
		this.precio = precio;
	}

	public String obtenerFechaInicio()
	{
		return fechaInicio;
	}

	public void definirFechaInicio(String fechaInicio)
	{
		this.fechaInicio = fechaInicio;
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
	}

	public String obtenerFechaVencimiento()
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
		String hoy = obtenerFechaActual();
		boolean vigente = !esFechaPosteriora(hoy, fechaVencimiento) && activa;

		if (!vigente && activa)
		{
			activa = false; // Desactivar automáticamente si venció
		}

		return vigente;
	}

	public int obtenerDiasRestantes()
	{
		if (!verificarVigencia())
			return 0;

		String hoy = obtenerFechaActual();
		return calcularDiasEntre(hoy, fechaVencimiento);
	}

	public void renovar()
	{
		if (verificarVigencia())
		{
			// Si todavía está vigente, extender desde la fecha de vencimiento actual
			fechaVencimiento = calcularFechaVencimiento(tipo, fechaVencimiento);
		}
		else
		{
			// Si ya venció, renovar desde hoy
			fechaInicio = obtenerFechaActual();
			fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
		}
		activa = true;
	}

	public void suspender()
	{
		activa = false;
	}

	public boolean reactivar()
	{
		String hoy = obtenerFechaActual();
		if (!esFechaPosteriora(hoy, fechaVencimiento))
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

	private String obtenerFechaActual()
	{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1; // Enero es 0
		int anio = cal.get(java.util.Calendar.YEAR);
		return String.format("%02d/%02d/%04d", dia, mes, anio);
	}

	private String calcularFechaVencimiento(String tipoMembresia, String fechaBase)
	{
		if (fechaBase == null || fechaBase.isEmpty())
			fechaBase = obtenerFechaActual();

		// Parsear la fecha base
		String[] partes = fechaBase.split("/");
		if (partes.length != 3)
			return obtenerFechaActual();

		int dia = Integer.parseInt(partes[0]);
		int mes = Integer.parseInt(partes[1]);
		int year = Integer.parseInt(partes[2]);

		// Calcular nueva fecha según tipo
		switch (tipoMembresia.toLowerCase())
		{
			case "mensual":
				mes += 1;
				break;
			case "trimestral":
				mes += 3;
				break;
			case "semestral":
				mes += 6;
				break;
			case "anual":
				year += 1;
				break;
			default:
				mes += 1; // Por defecto mensual
		}

		// Ajustar si el mes se pasa de 12
		while (mes > 12)
		{
			mes -= 12;
			year += 1;
		}

		// Ajustar días si el mes no tiene suficientes
		int diasEnMes = obtenerDiasEnMes(mes, year);
		if (dia > diasEnMes)
			dia = diasEnMes;

		return String.format("%02d/%02d/%04d", dia, mes, year);
	}

	private boolean esFechaPosteriora(String fecha1, String fecha2)
	{
		String[] partes1 = fecha1.split("/");
		String[] partes2 = fecha2.split("/");

		if (partes1.length != 3 || partes2.length != 3)
			return false;

		int dia1 = Integer.parseInt(partes1[0]);
		int mes1 = Integer.parseInt(partes1[1]);
		int year1 = Integer.parseInt(partes1[2]);

		int dia2 = Integer.parseInt(partes2[0]);
		int mes2 = Integer.parseInt(partes2[1]);
		int year2 = Integer.parseInt(partes2[2]);

		if (year1 > year2) return true;
		if (year1 < year2) return false;

		if (mes1 > mes2) return true;
		if (mes1 < mes2) return false;

		return dia1 > dia2;
	}

	private int calcularDiasEntre(String fecha1, String fecha2)
	{
		String[] partes1 = fecha1.split("/");
		String[] partes2 = fecha2.split("/");

		if (partes1.length != 3 || partes2.length != 3)
			return 0;

		int dia1 = Integer.parseInt(partes1[0]);
		int mes1 = Integer.parseInt(partes1[1]);
		int year1 = Integer.parseInt(partes1[2]);

		int dia2 = Integer.parseInt(partes2[0]);
		int mes2 = Integer.parseInt(partes2[1]);
		int year2 = Integer.parseInt(partes2[2]);

		// Cálculo aproximado: diferencia en años * 365 + diferencia en meses * 30 + diferencia en días
		int diasYears = (year2 - year1) * 365;
		int diasMeses = (mes2 - mes1) * 30;
		int diasDias = (dia2 - dia1);

		return diasYears + diasMeses + diasDias;
	}

	private int obtenerDiasEnMes(int mes, int year)
	{
		switch (mes)
		{
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				return 31;
			case 2:
				// Año bisiesto
				if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
					return 29;
				return 28;
			default:
				return 30;
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

	// IMPLEMENTACION DE INTERFACES

	@Override
	public float obtenerCosto()
	{
		return precio;
	}

	@Override
	public String generarReporte()
	{
		return "=== REPORTE DE MEMBRESÍA ===\n" +
				"ID: " + idMembresia + "\n" +
				"Tipo: " + tipo + "\n" +
				"Precio: $" + String.format("%.2f", precio) + "\n" +
				"Fecha inicio: " + fechaInicio + "\n" +
				"Fecha vencimiento: " + fechaVencimiento + "\n" +
				"Estado: " + (verificarVigencia() ? "VIGENTE" : "VENCIDA") + "\n" +
				"Días restantes: " + obtenerDiasRestantes() + "\n" +
				"============================\n";
	}
}