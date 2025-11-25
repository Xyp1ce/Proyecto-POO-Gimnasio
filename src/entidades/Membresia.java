package entidades;

import java.io.Serializable;

public class Membresia implements Serializable, ConCosto, Reportable
{
	// REPRESENTAR LOS PLANES DE MEMBRESIA Y SU ESTADO DE VIGENCIA
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	private long idMembresia;
	private String tipo; // DEFINIR CATEGORIA COMO MENSUAL TRIMESTRAL O ANUAL
	private float precio;
	private String fechaInicio; // FORMATO DD/MM/YYYY
	private String fechaVencimiento; // FORMATO DD/MM/YYYY
	private boolean activa;

	// CONSTRUCTORES
	public Membresia()
	{
		// CREA MEMBRESIA MENSUAL POR DEFECTO PARA NUEVOS CLIENTES
		this.idMembresia = generarIdMembresia();
		this.tipo = "Mensual";
		this.precio = 300.0f;
		this.fechaInicio = obtenerFechaActual();
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
		this.activa = true;
	}

	public Membresia(String tipo, float precio)
	{
		// PERMITE ESPECIFICAR TIPO Y PRECIO MANTENIENDO FECHA ACTUAL COMO BASE
		this.idMembresia = generarIdMembresia();
		this.tipo = tipo;
		this.precio = precio;
		this.fechaInicio = obtenerFechaActual();
		this.fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
		this.activa = true;
	}

	public Membresia(String tipo, float precio, String fechaInicio)
	{
		// RECIBE FECHA PERSONALIZADA PARA SINCRONIZAR CON UN HISTORICO EXISTENTE
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
		// ACTUALIZAR EL TIPO REQUIERE RECALCULAR EL VENCIMIENTO
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
		// CAMBIAR LA FECHA BASE IMPACTA DIRECTAMENTE EL NUEVO VENCIMIENTO
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
		// COMPARAR LA FECHA ACTUAL CON EL VENCIMIENTO Y EL ESTADO ACTIVO
		String hoy = obtenerFechaActual();
		boolean vigente = !esFechaPosteriora(hoy, fechaVencimiento) && activa;

		if (!vigente && activa)
		{
			activa = false; // DESACTIVAR AUTOMATICAMENTE SI VENCIO
		}

		return vigente;
	}

	public int obtenerDiasRestantes()
	{
		// DEVOLVER DIAS RESTANTES SOLO SI LA MEMBRESIA CONTINUA VIGENTE
		if (!verificarVigencia())
			return 0;

		String hoy = obtenerFechaActual();
		return calcularDiasEntre(hoy, fechaVencimiento);
	}

	public void renovar()
	{
		// RENOVAR SEGUN SI SIGUE ACTIVA O SI DEBE REINICIARSE DESDE HOY
		if (verificarVigencia())
		{
			// SI TODAVIA ESTA VIGENTE, EXTENDER DESDE LA FECHA DE VENCIMIENTO ACTUAL
			fechaVencimiento = calcularFechaVencimiento(tipo, fechaVencimiento);
		}
		else
		{
			// SI YA VENCIO, RENOVAR DESDE HOY
			fechaInicio = obtenerFechaActual();
			fechaVencimiento = calcularFechaVencimiento(tipo, fechaInicio);
		}
		activa = true;
	}

	public void suspender()
	{
		// DESACTIVAR MEMBRESIA SIN ALTERAR FECHAS
		activa = false;
	}

	public boolean reactivar()
	{
		// SOLO REACTIVAR SI LA FECHA VIGENTE AUN NO EXPIRA
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
		// CREAR UN ID ALEATORIO DE SEIS DIGITOS
		return (long) (Math.random() * 900000L) + 100000L;
	}

	private String obtenerFechaActual()
	{
		// FORMATEAR LA FECHA DEL SISTEMA EN CADENA ESTANDAR
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int dia = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int mes = cal.get(java.util.Calendar.MONTH) + 1; // ENERO ES 0
		int anio = cal.get(java.util.Calendar.YEAR);
		return String.format("%02d/%02d/%04d", dia, mes, anio);
	}

	private String calcularFechaVencimiento(String tipoMembresia, String fechaBase)
	{
		// CALCULA NUEVA FECHA DE VENCIMIENTO SEGUN EL PLAN CONFIGURADO
		if (fechaBase == null || fechaBase.isEmpty())
			fechaBase = obtenerFechaActual();

		// PARSEAR LA FECHA BASE
		String[] partes = fechaBase.split("/");
		if (partes.length != 3)
			return obtenerFechaActual();

		int dia = Integer.parseInt(partes[0]);
		int mes = Integer.parseInt(partes[1]);
		int year = Integer.parseInt(partes[2]);

		// CALCULAR NUEVA FECHA SEGUN TIPO
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
				mes += 1; // POR DEFECTO MENSUAL
		}

		// AJUSTAR SI EL MES SE PASA DE 12
		while (mes > 12)
		{
			mes -= 12;
			year += 1;
		}

		// AJUSTAR DIAS SI EL MES NO TIENE SUFICIENTES
		int diasEnMes = obtenerDiasEnMes(mes, year);
		if (dia > diasEnMes)
			dia = diasEnMes;

		return String.format("%02d/%02d/%04d", dia, mes, year);
	}

	private boolean esFechaPosteriora(String fecha1, String fecha2)
	{
		// COMPARA DOS FECHAS EN FORMATO DD/MM/YYYY PARA DETERMINAR ORDEN
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
		// ESTIMA EL NUMERO DE DIAS ENTRE DOS FECHAS SIN DEPENDER DE API AVANZADA
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

		// CALCULO APROXIMADO DIFERENCIA EN ANIOS 365 MAS MESES 30 MAS DIAS
		int diasYears = (year2 - year1) * 365;
		int diasMeses = (mes2 - mes1) * 30;
		int diasDias = (dia2 - dia1);

		return diasYears + diasMeses + diasDias;
	}

	private int obtenerDiasEnMes(int mes, int year)
	{
		// RETORNA LA CANTIDAD DE DIAS DE CADA MES CONSIDERANDO BISIESTOS
		switch (mes)
		{
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				return 31;
			case 2:
				// MANEJAR ANIO BISIESTO
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
		// GENERA CADENA DETALLADA PARA BITACORAS Y DEPURACION
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
		// ENTREGAR RESUMEN BREVE USADO EN MENUS E INFORMES RAPIDOS
		String estado = verificarVigencia() ? "✓ Vigente" : "✗ Vencida";
		return String.format("%s | $%.2f | %s (%d días)",
				tipo, precio, estado, obtenerDiasRestantes());
	}

	// IMPLEMENTACION DE INTERFACES

	@Override
	public float obtenerCosto()
	{
		// EXPONE EL PRECIO PARA QUE LOS REPORTES SUMEN COSTOS CONSISTENTES
		return precio;
	}

	@Override
	public String generarReporte()
	{
		// CONSTRUYE DESGLOSE COMPLETO APTO PARA EXPORTAR A ARCHIVOS
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